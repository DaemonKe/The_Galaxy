#!/usr/bin/env python3
"""Configure repository-local Git privacy and shared hooks. Requires gh login."""
import json
import os
from pathlib import Path
import subprocess
import sys


def run(*args):
    return subprocess.check_output(args, text=True).strip()


def main():
    root = Path(__file__).resolve().parent.parent
    os.chdir(root)
    if Path(run('git', 'rev-parse', '--show-toplevel')).resolve() != root:
        raise SystemExit('Run this from an independent clone of The Galaxy.')
    profile = json.loads(run('gh', 'api', 'user'))
    login = profile['login']
    email = f"{profile['id']}+{login}@users.noreply.github.com"
    current_hooks = subprocess.run(['git', 'config', '--get', 'core.hooksPath'], capture_output=True, text=True)
    if current_hooks.stdout.strip() not in ('', '.githooks'):
        raise SystemExit('Existing custom hooksPath found. Review it before enabling shared hooks.')
    for key, value in [('user.name', login), ('user.email', email), ('core.hooksPath', '.githooks'),
                       ('pull.ff', 'only'), ('fetch.prune', 'true')]:
        subprocess.run(['git', 'config', '--local', key, value], check=True)
    print('Repository-local noreply identity and shared hooks configured.')
    print('No global configuration or credentials were copied.')
    print('Next: java -version; javac -version; then run the Gradle wrapper build.')


if __name__ == '__main__':
    try:
        main()
    except (subprocess.CalledProcessError, FileNotFoundError):
        sys.exit('Setup failed. Check Git, GitHub CLI, and gh auth login. No credential output is shown.')
