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
