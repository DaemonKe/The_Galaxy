# The Galaxy · 星河探索

![Minecraft 1.21.1](https://img.shields.io/badge/Minecraft-1.21.1-62B47A)
![NeoForge 21.1.256](https://img.shields.io/badge/NeoForge-21.1.256-F38B2A)
![Java 21](https://img.shields.io/badge/Java-21-437291)
![Status Alpha](https://img.shields.io/badge/Status-Alpha-yellow)

**从地面仰望星空，到亲自踏上其他星球。**

The Galaxy 是一个正在开发的 Minecraft Java 版天文与航天 Mod，使用 **NeoForge** 构建。项目计划围绕天文观测、卫星探测与载人航天，逐步形成完整的太空探索体验。

```text
天文观测 → 卫星探测 → 火箭发射 → 星际探索
```

> 当前版本为 `0.1.0-alpha` 基础模板。已实现内容注册、望远镜基础行为和航天组件；卫星发射、火箭飞行、星球维度尚未实现。

## 当前内容

所有内容位于独立的创造模式分类「**星河探索**」，注册命名空间为 `the_galaxy`。

| 内容 | 注册 ID | 当前功能 |
| --- | --- | --- |
| 天文望远镜 | `telescope` | 按住使用键放大观察，复用原版望远镜行为 |
| 卫星核心 | `satellite_core` | 可合成的基础组件，预留卫星制造用途 |
| 火箭引擎 | `rocket_engine` | 可合成的基础组件，预留火箭制造用途 |
| 观测站控制器 | `observatory_controller` | 可合成、放置和采集的普通方块 |
| 发射台 | `launch_pad` | 可合成、放置和采集的完整方块 |

工程还包含：

- 简体中文（`zh_cn`）和英文（`en_us`）名称与组件说明。
- 五种工作台配方及对应的配方解锁进度。
- 方块状态、方块模型、物品模型和方块掉落表。
- 挖掘工具标签：两个方块均需要石镐或更好的镐才能正常采集。
- 数据生成器与客户端、专用服务器开发运行配置。

目前模型引用 Minecraft 原版贴图，便于验证功能。观测站与发射台暂未包含 GUI、机器逻辑、多方块结构或发射功能；望远镜尚不识别天体或记录研究成果。

## 开发计划

| 阶段 | 目标 |
| --- | --- |
| 地面天文观测 | 天体识别、观测条件、观测站、研究成果与进度保存 |
| 卫星探测 | 卫星组装、任务选择、发射与探测结果回收 |
| 火箭与发射设施 | 发射台结构、火箭实体、燃料、登乘、倒计时与目的地选择 |
| 星球探索 | 星球维度、地形、安全着陆、返航与环境生存机制 |

具体规划与阶段验收标准见 [开发路线](docs/ROADMAP.md)。上述功能会分阶段实现，当前模板没有可供玩家游玩的星际旅行流程。

## 环境要求

| 组件 | 版本 |
| --- | --- |
| Minecraft Java Edition | **1.21.1** |
| NeoForge | **21.1.256** |
| JDK | **21，64 位** |
| Gradle Wrapper | **9.2.1** |
| ModDevGradle | **2.0.148** |
| 映射 | Mojang 官方映射 |

无需全局安装 Gradle。请确认 `java -version` 和 `javac -version` 均指向 Java 21，并在 IDE 中将 Gradle JVM 设置为 JDK 21。

## 快速开始

克隆仓库：

```bash
git clone https://github.com/DaemonKe/The_Galaxy.git
cd The_Galaxy
```

### macOS / Linux

```bash
./gradlew build       # 编译并打包
./gradlew runClient   # 启动开发客户端
./gradlew runServer   # 启动开发专用服务器
./gradlew runData     # 重新生成模型、翻译、配方、标签和掉落表
```

### Windows

```powershell
.\gradlew.bat build
.\gradlew.bat runClient
.\gradlew.bat runServer
.\gradlew.bat runData
```

首次运行需要联网下载构建依赖和游戏资源。`runClient` / `runData` 可能触发较大的游戏声音资源下载；普通 `build` 不需要这些声音资源。

### 便捷开发脚本

macOS / Linux 也可以使用：

```bash
./scripts/dev.sh build
./scripts/dev.sh runClient
./scripts/dev.sh runData
```

脚本优先使用可选的项目本地 JDK（`.tools/java21/`），否则使用 `JAVA_HOME` 或系统 Java，并检查版本是否为 21。Gradle 缓存默认保存在 `.tools/gradle-home/`。

`.tools/` 不随仓库上传。新克隆的工作区需要自行准备 JDK 21；脚本不会自动安装 Java 或更改系统配置。

## 构建产物与安装

构建完成后，Mod 主文件位于：

```text
build/libs/the_galaxy-0.1.0-alpha.jar
```

将主 JAR 放入 Minecraft **1.21.1** + NeoForge **21.1.256** 实例的 `mods/` 目录。`-sources.jar` 是开发用源码包，不是游戏安装包。当前需要从源码构建，尚未发布 GitHub Release。

## 工程结构

```text
The_Galaxy/
├── src/main/java/dev/thegalaxy/
│   ├── TheGalaxy.java           # Mod 入口与监听器注册
│   ├── registry/                # 方块、物品、创造模式分类
│   ├── item/                    # 自定义物品行为
│   └── data/                    # 资源和数据生成器
├── src/main/templates/META-INF/ # Mod 元数据源模板
├── src/main/resources/          # 手写贴图、音效等资源
├── src/generated/resources/     # 数据生成产物，纳入版本控制
├── docs/ROADMAP.md              # 后续开发路线
├── scripts/dev.sh              # 本地开发入口
├── gradle.properties            # Minecraft、NeoForge 与 Mod 信息
└── build.gradle                 # 构建与运行配置
```

版本、名称和包组信息集中在 [gradle.properties](gradle.properties)。若修改 Mod ID，需同步入口常量、命名空间和翻译键；已有世界使用后，应尽量保持注册 ID 稳定。

新增内容时，修改注册代码和对应数据生成器，然后执行：

```bash
./gradlew runData
./gradlew build
```

`src/generated/resources/` 是运行时需要的资源，必须一并提交。不要直接修改生成的 JSON，或在 `src/main/resources/` 下维护同路径副本。普通 `build` 使用已提交的生成资源，不会自动启动数据生成。

## 验证状态

以下检查于 **2026-10-08** 在 Java 21 / macOS ARM64 环境完成：

| 检查 | 结果 |
| --- | --- |
| Java 编译与 Mod 打包 | 通过 |
| 数据生成 | 通过，生成 25 个 JSON 文件 |
| JAR 内容检查 | 通过，资源、展开后的元数据与 Java 21 字节码均已确认 |
| 专用服务器启动 | 通过，Mod 加载、配方与进度读取、测试世界启动正常 |
| 客户端画面、物品交互与玩家联机 | 待验证 |

本次数据生成和服务端检查使用 `-x downloadAssets`，引用本地已下载的游戏资源索引，跳过停止进展的声音下载。此配置仅保存在本地 `build/`，不随仓库分发。常规运行仍会尝试下载完整资源。测试服务器完成启动后已关闭并保存世界。

手动检查建议：启动客户端，确认创造模式分类内五项内容的模型和翻译；测试望远镜、五种配方和方块生存模式掉落；再进行专用服务器多人交互测试。

## 参考与授权

- 玩法方向参考经典 [Galacticraft](https://github.com/micdoodle8/Galacticraft)，本项目独立开发，当前没有运行依赖或移植其代码、模型与贴图。
- 基础工程参考 [NeoForge 1.21.1 ModDevGradle MDK](https://github.com/NeoForgeMDKs/MDK-1.21.1-ModDevGradle)，参考提交为 `ad911709f06d3bdd5b1a500023a9156999216351`。
- 上游模板的 MIT 许可保留在 [TEMPLATE_LICENSE.txt](TEMPLATE_LICENSE.txt)。项目业务代码目前为 **All Rights Reserved**，正式许可证由项目所有者后续决定。
- 开发 API 以 [NeoForge 1.21.1 官方文档](https://docs.neoforged.net/docs/1.21.1/) 和工程解析的依赖源码为准。
