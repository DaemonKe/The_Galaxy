import importlib.util
from pathlib import Path
import unittest
import subprocess
import tempfile
import sys

spec = importlib.util.spec_from_file_location('privacy', Path(__file__).parents[1] / 'scripts/check_privacy.py')
privacy = importlib.util.module_from_spec(spec)
spec.loader.exec_module(privacy)


class PrivacyTests(unittest.TestCase):
    def test_personal_email(self):
        self.assertIn('non-noreply email', privacy.text_findings('person' + '@' + 'example.org'))

    def test_noreply(self):
        self.assertEqual([], privacy.text_findings('123+login' + '@' + 'users.noreply.github.com'))

    def test_home_paths(self):
        for path in ('/Users/' + 'sample/file', 'C:\\Users\\' + 'sample\\file'):
            self.assertIn('personal home path', privacy.text_findings(path))

    def test_token(self):
        self.assertIn('access token', privacy.text_findings('gh' + 'p_' + 'a' * 36))

    def test_credential_url(self):
        self.assertIn('credential in URL', privacy.text_findings('https://' + 'sample:password' + '@' + 'example.org'))

    def test_local_files(self):
        for path in ('.env', '.aws/credentials', '.tools/cache'):
            self.assertTrue(privacy.path_findings(path))

    def test_project_skill_allowed(self):
        self.assertEqual([], privacy.path_findings('.agents/skills/neoforge-1-21-1/SKILL.md'))

    def test_binary_and_text(self):
        self.assertEqual([], privacy.check_blob('gradle/wrapper/gradle-wrapper.jar', b'PK\0binary'))
        self.assertEqual([], privacy.text_findings('Java 21; ./gradlew build'))


    def test_git_index_and_history(self):
        checker = Path(__file__).parents[1].resolve() / 'scripts/check_privacy.py'
        with tempfile.TemporaryDirectory() as folder:
            def git(*args):
                return subprocess.run(['git', *args], cwd=folder, check=True, capture_output=True)
            def check(*args):
                return subprocess.run([sys.executable, str(checker), *args], cwd=folder, capture_output=True, text=True)
            git('init', '-b', 'main')
            git('config', 'user.name', 'sample-user')
            git('config', 'user.email', '123+sample-user' + '@' + 'users.noreply.github.com')
            hooks = Path(folder) / 'empty-hooks'
            hooks.mkdir()
            git('config', 'core.hooksPath', str(hooks))
            document = Path(folder) / 'README.md'
            document.write_text('Safe project documentation')
            git('add', 'README.md')
            self.assertEqual(0, check().returncode)
            git('commit', '-m', 'Safe baseline')
            self.assertEqual(0, check('--history', 'HEAD').returncode)
            personal = 'sample' + '@' + 'example.org'
            document.write_text(personal)
            git('add', 'README.md')
            blocked = check()
            self.assertNotEqual(0, blocked.returncode)
            self.assertNotIn(personal, blocked.stderr)
            document.write_text('Safe new document')
            git('add', 'README.md')
            git('config', 'user.email', personal)
            self.assertNotEqual(0, check().returncode)
            git('commit', '-m', 'Private metadata sample')
            blocked = check('--history', 'HEAD')
            self.assertNotEqual(0, blocked.returncode)
            self.assertNotIn(personal, blocked.stderr)


if __name__ == '__main__':
    unittest.main()
