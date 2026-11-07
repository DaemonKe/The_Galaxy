# The Galaxy development

- Minecraft 1.21.1, NeoForge 21.1.256, Java 21, ModDevGradle 2.0.148, official Mojang mappings. Preserve version pins unless requested otherwise.
- Mod ID: `the_galaxy`. Java base package: `dev.thegalaxy`. Read `README.md` for current implemented scope and `docs/ROADMAP.md` for planned features.
- Use the `neoforge-1-21-1` skill when available. Verify APIs against 1.21.1 documentation and resolved sources, especially when adapting Forge/Fabric or later Minecraft examples.
- Use the Gradle wrapper. `scripts/dev.sh` selects an optional ignored project-local Java 21 installation and cache; otherwise use JDK 21 via JAVA_HOME.
- Registries live in `registry/`. Data generators live in `data/`. Keep entrypoints small and avoid loading client-only Minecraft classes from common code.
- Generated assets/data under `src/generated/resources/` are tracked. Update providers, run `runData`, then build after content/data changes. Do not create manual copies at the same resource path.
- Keep both `zh_cn` and `en_us` names/tooltips for new content. Vanilla art references are temporary and documented.
- Preserve stable registry IDs and namespaces. Add persistence or networking only when a feature requires it.
- Report actual validation performed. Build/datagen success does not establish client rendering, dedicated-server gameplay, or multiplayer correctness.

## Two-computer collaboration

- Read `docs/COLLABORATION.md` and `docs/HANDOFF.md` when starting or handing off work. The canonical skill is `.agents/skills/neoforge-1-21-1/`; read its SKILL.md directly if automatic discovery is unavailable.
- Use an independent Git clone per computer. Keep Git metadata, Java, caches and runtime worlds outside shared-folder synchronization. Configure each clone with `scripts/setup-workspace.py`.
- Start with `git status` and `git fetch origin`. Use one `feat/`, `fix/` or `docs/` branch per task. Never change branches, pull, reset or overwrite uncommitted work to resolve another computer's changes.
- For a handoff, commit and push the task branch, and update its `docs/HANDOFF.md` entry with changed files, exact checks and remaining work. An unpushed change cannot be assumed available on the other computer.
- Parallel tasks need separate branches and ownership of touched files; coordinate shared registry/data-generator changes. Merge through a PR after review; update main only by fast-forward. Do not force-push shared history during routine work.
- Public commits must use the GitHub username and GitHub noreply email. Keep real names, personal email, home paths, credentials and private logs out of tracked files, commit messages, PRs and screenshots. Preserve the privacy hooks; review their findings rather than bypassing them.
