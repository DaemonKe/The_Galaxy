#!/usr/bin/env python3
"""Check staged files or reachable Git history without printing matched secrets."""
import argparse
import re
import subprocess
import sys
from pathlib import PurePosixPath

EMAIL = re.compile(r"[A-Za-z0-9.!#$%&'*+/=?^_`{|}~-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}")
PATTERNS = {
    'personal home path': re.compile(r"/(?:Users|home)/[A-Za-z0-9_.-]+/|[A-Za-z]:[\\/]Users[\\/][^\\/\s]+[\\/]"),
    'private key': re.compile(r"-----BEGIN (?:OPENSSH |RSA |EC |DSA )?PRIVATE KEY-----"),
    'access token': re.compile(r"gh[pousr]_[A-Za-z0-9]{20,}|github_pat_[A-Za-z0-9_]{20,}|AKIA[A-Z0-9]{16}|sk-(?:proj-)?[A-Za-z0-9_-]{32,}"),
    'credential in URL': re.compile(r"https?://[^\s/:]+:[^\s/@]+@"),
}
LOCAL_PARTS = {'.tools', '.gradle', '.learnings', '.codex', '.aws', '.ssh', 'run', 'runs', 'logs'}


def git(*args):
    return subprocess.check_output(['git', *args])


def text_findings(text):
    findings = [label for label, pattern in PATTERNS.items() if pattern.search(text)]
    if any(not address.lower().endswith('@users.noreply.github.com') for address in EMAIL.findall(text)):
        findings.append('non-noreply email')
    return findings


def path_findings(name):
    p = PurePosixPath(name)
    if any(part in LOCAL_PARTS for part in p.parts):
        return ['local-only file']
    if p.name == '.env' or (p.name.startswith('.env.') and p.name != '.env.example'):
        return ['environment file']
    if p.suffix.lower() in {'.pem', '.key', '.p12', '.pfx', '.keystore', '.jks'}:
        return ['credential file']
    return []


def check_blob(name, data):
    findings = path_findings(name)
    if b'\0' not in data:
        findings.extend(text_findings(data.decode('utf-8', errors='replace')))
    return findings


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--history', metavar='REF', help='check commits and all reachable file versions')
    args = parser.parse_args()
    failures = []
    if args.history:
        commits = git('log', '--format=%H%x00%ae%x00%ce%x00%B%x00', args.history).decode()
        fields = commits.split('\0')
        for offset in range(0, len(fields) - 3, 4):
            sha, author, committer, message = fields[offset:offset + 4]
            if text_findings(author + '\n' + committer + '\n' + message):
                failures.append(('commit ' + sha.strip()[:12], 'private metadata or message'))
        blobs = {}
        for row in git('rev-list', '--objects', args.history).decode().splitlines():
            sha, _, name = row.partition(' ')
            if name and git('cat-file', '-t', sha).strip() == b'blob':
                blobs[sha] = name
        entries = ((name, git('cat-file', 'blob', sha)) for sha, name in blobs.items())
    else:
        for kind in ('GIT_AUTHOR_IDENT', 'GIT_COMMITTER_IDENT'):
            if text_findings(git('var', kind).decode()):
                failures.append(('Git identity', 'use the setup script to configure a noreply email'))
        names = git('diff', '--cached', '--name-only', '--diff-filter=ACMR', '-z').decode().split('\0')
        entries = ((name, git('show', ':' + name)) for name in names if name)
    for name, data in entries:
        for label in check_blob(name, data):
            failures.append((name, label))
    if failures:
        for name, label in failures:
            print(f'Privacy check blocked: {name}: {label}', file=sys.stderr)
        return 1
    print('Privacy checks passed (patterns and identity; manual review still required).')
    return 0


if __name__ == '__main__':
    sys.exit(main())
