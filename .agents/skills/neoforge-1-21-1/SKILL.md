---
name: neoforge-1-21-1
description: "Develop, build, debug, and review Minecraft Java Edition 1.21.1 mods using NeoForge and Java 21. Use for project setup, content registration, resources and data generation, networking, persistence, and client/server compatibility for this version. Do not apply to Fabric, Bukkit/Paper plugins, or other Minecraft versions unless adapting explicitly."
---

# Minecraft 1.21.1 / NeoForge

## Version and evidence

Target Minecraft **1.21.1**, NeoForge, and a **64-bit JDK 21**. Preserve these choices unless the user changes them. Read the project's AGENTS.md and build files first. Determine the pinned NeoForge patch, build plugin, mappings, mod ID, and Java package from the project rather than guessing.

Use versioned official documentation at https://docs.neoforged.net/docs/1.21.1/ and the project's resolved Minecraft/NeoForge sources to confirm method signatures and JSON schemas. Documentation groups this release under “1.21 - 1.21.1”. Do not silently substitute documentation for a later 1.21.x release or the latest release. Treat snippets from Forge, Fabric, and older tutorials as migration material, not compatible code.

For uncertain APIs, inspect the resolved dependency sources or version-matched official examples before implementation. Compile against the project's actual dependencies. Record any unresolved uncertainty instead of inventing signatures.

## Project setup and builds

Only initialize a mod project when requested; preparing this skill alone does not authorize scaffolding a mod. For a new project, prefer the official version-specific ModDevGradle MDK:
https://github.com/NeoForgeMDKs/MDK-1.21.1-ModDevGradle

Inspect and pin the selected template revision and dependency versions when scaffolding. Choose a released NeoForge version compatible with 1.21.1 from the official MDK/Maven; do not hardcode an unverified “latest” version in this skill. Preserve NeoGradle in existing projects; do not mix its DSL or plugins with ModDevGradle.

- Check `java -version`, `javac -version`, and the Gradle JVM/toolchain. On macOS, `/usr/libexec/java_home -V` can locate installed JDKs.
- Use the project's Gradle wrapper (`./gradlew` on macOS/Linux, `gradlew.bat` on Windows). Preserve wrapper files and template repository configuration.
- Keep mod ID, namespaces, package, entrypoint, and mod metadata consistent. Inspect the MDK's resource/template expansion for `META-INF/neoforge.mods.toml`; edit the authoritative template rather than a generated copy.
- Run `./gradlew tasks --all` when task names are uncertain. Common tasks include `build`, `runClient`, `runServer`, and `runData`; verify availability in the project.
- Diagnose the first relevant Gradle error and logs before changing configuration. Distinguish source errors from JDK, TLS, dependency download, and proxy errors. Do not disable TLS verification to work around certificates.
- Read plugin versions from the project. Avoid unconditional `clean` or `--refresh-dependencies`; use them for a diagnosed cache/generated-output problem.

## Registration and lifecycle

Use NeoForge imports (`net.neoforged...`) and the 1.21.1 entrypoint constructor style shown by the MDK. Use `DeferredRegister` and appropriate deferred holders for static registry content; attach each register to the mod event bus. Avoid dereferencing entries before registration completes.

Distinguish the mod event bus for lifecycle/registration events from `NeoForge.EVENT_BUS` for gameplay events. Check the event's documented bus and distribution. Do not use event class names or annotation parameters remembered from another version without checking.

Use `ResourceLocation.fromNamespaceAndPath(namespace, path)` or version-supported parsing helpers. Keep registry IDs stable for world compatibility. Dynamic registries such as worldgen need their documented data/bootstrap workflow rather than ordinary static registry registration.

## Gameplay and resources

Implement the smallest complete requested gameplay slice. Include the resources that make it usable: blockstates/models/textures, item models, creative tab inclusion, localization, recipes, tags, and loot tables as applicable. Preserve existing language conventions; for a Chinese-facing mod, add `zh_cn` and `en_us` translations when introducing translatable content.

Use `assets/<mod_id>/` for client resources and `data/<namespace>/` for server data. Verify the 1.21.1 singular data-directory names and JSON formats against vanilla resources or datagen; do not copy pre-1.21 plural paths or later item-model formats. Use the project's data providers when present, include generated resources in source sets, and inspect `runData` output for missing IDs or references. Avoid maintaining manual duplicates of generated files.

Use data components for structured per-item stack data where appropriate in 1.21.1. Choose NeoForge attachments, block entity persistence, or SavedData according to ownership and lifetime. Check serialization, loading, and synchronization separately; persistence alone does not imply network synchronization. Do not copy legacy NBT/capability examples without verifying the current API.

## Sides and networking

Keep authoritative world/inventory/gameplay mutations on the logical server. Separate physical client loading from logical side checks. Dedicated servers must not load `net.minecraft.client` classes through common entrypoints, static fields, signatures, or event subscribers.

For custom networking, consult the 1.21.1 payload API (`CustomPacketPayload`, stream codecs, and `RegisterPayloadHandlersEvent`). Verify payload direction and handler execution thread. Schedule world access on the correct game thread when needed. Validate client-provided targets, player permissions, distance, menu state, and input bounds relevant to the operation. Do not copy legacy SimpleChannel registration code.

Prefer ordinary registries, events, data, and extension APIs before introducing mixins or access transformers. Add those only when the requested behavior needs access unavailable through supported hooks.

## Verification and delivery

Match validation to the change:
- Java/build changes: run the relevant compile task and `./gradlew build` when dependencies are available.
- Data/resource changes: run configured datagen where relevant; inspect generated files and resource IDs.
- Gameplay or UI changes: exercise the affected behavior in `runClient` when an interactive run is possible.
- Common initialization or side-sensitive changes: verify dedicated server startup and relevant multiplayer behavior when possible. A successful compile or client launch does not prove server compatibility.

Do not accept the Minecraft EULA on the user's behalf. If a dedicated-server test stops there, report that concrete limitation. Do not weaken an existing server's authentication configuration to enable tests.

Report what changed, checks actually completed, artifact location under `build/libs` if built, and any unverified runtime behavior. Never claim a successful game launch based only on compilation.

## Official references by task

Read only the pages needed for the current feature, staying under the 1.21.1 version prefix:
- Setup and runs: https://docs.neoforged.net/docs/1.21.1/gettingstarted/
- Registries: https://docs.neoforged.net/docs/1.21.1/concepts/registries/
- Events: https://docs.neoforged.net/docs/1.21.1/concepts/events/
- Sides: https://docs.neoforged.net/docs/1.21.1/concepts/sides/
- Resources and datagen navigation: https://docs.neoforged.net/docs/1.21.1/resources/
- Network payloads: https://docs.neoforged.net/docs/1.21.1/networking/payload/
- Blocks, items, storage, GUI, worldgen: navigate from the versioned documentation sidebar, then verify APIs against resolved sources.
