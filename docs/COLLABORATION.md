# 双电脑与 Codex 协作指南

目标是让每台电脑都能独立构建、继续同一个任务，也能在不同任务分支上并行开发。

## 一、每台电脑使用独立工作副本

**通过 GitHub 同步代码，通过 Git 分支和交接记录同步进度。**

将两台电脑的实际开发目录放在自动网盘同步之外。不要让网盘同时同步正在使用的 `.git`、JDK、Gradle 缓存、构建输出或 Minecraft 世界；这些文件应各自在本机维护。已有的同步目录可以保留作本地备份。

本次协作初始化更新了 Git 基线历史。已有旧副本时，首次使用本指南应在一个新的本地目录重新克隆：

```bash
git clone https://github.com/DaemonKe/The_Galaxy.git The_Galaxy-clean
cd The_Galaxy-clean
```

保留旧副本中未提交的工作，逐项检查并复制需要的源码改动到新分支。不要把旧副本的 `.git` 覆盖回来，也不要合并、推送旧分支或旧提交。不要为了同步执行 `reset --hard`、`clean -fd` 或删除未备份的文件。

完成第一次迁移后，日常使用正常的 fetch / pull / push 流程即可。

## 二、安装一次本机工具

两台电脑都需要：

| 工具 | 用途 |
| --- | --- |
| Codex | 打开本机项目并执行开发任务 |
| Git | 分支与版本同步 |
| GitHub CLI（`gh`） | 登录、设置公开提交身份、创建 PR |
| 64 位 JDK 21 | Minecraft 1.21.1 的编译和运行环境 |
| Python 3.9 或更新版本 | 标准库实现的配置和隐私检查脚本，无需额外 Python 包 |

Java 必须与本机系统和 CPU 架构匹配。无需单独安装 Gradle，项目自带 Wrapper。Java、Codex 和 GitHub 分别在每台电脑完成本地安装或登录；无需同步整套用户配置和登录凭据。

在终端确认：

```bash
git --version
gh --version
java -version
javac -version
gh auth login
gh auth status
```

将 `JAVA_HOME` 和 IDE 的 Gradle JVM 指向 JDK 21。代理地址、证书或网络设置在各机器本地配置，不写进仓库。无法下载依赖时，先检查网络与错误日志，不要关闭 TLS 校验。

## 三、初始化本仓库的协作与隐私设置

在新克隆的项目根目录执行：

macOS / Linux：

```bash
python3 scripts/setup-workspace.py
./gradlew build
```

Windows PowerShell：

```powershell
py -3 scripts/setup-workspace.py
.\gradlew.bat build
```

Windows 若只有 `python` 命令，可用 `python scripts/setup-workspace.py`。Git hooks 需要 Git for Windows 自带的 shell；请正常安装 Git for Windows。

配置脚本会读取 `gh` 当前登录用户的公开用户名和编号，然后只设置本仓库的：

- `user.name`：GitHub 公开用户名。
- `user.email`：GitHub 的 noreply 地址，不使用个人邮箱。
- `core.hooksPath`：启用版本控制中的 `.githooks/`。
- `pull.ff=only`：分支产生分叉时停止，避免无意制造合并。
- `fetch.prune=true`：获取远端更新时清理过期的远端跟踪分支。

脚本不会更改全局 Git 配置，不复制登录凭据；遇到已有自定义 hooksPath 会停止，请先确认兼容性。

检查是否完成：

```bash
git config --local user.name
git config --local user.email
git config --local core.hooksPath
```

邮箱应以 `@users.noreply.github.com` 结尾，hooksPath 应为 `.githooks`。还可以在 GitHub 邮箱设置中启用邮箱隐私和阻止暴露个人邮箱的推送。

### 检查脚本的作用和边界

- 提交前检查暂存文件、当前作者与提交者邮箱。
- 推送前检查目标分支可达的提交身份、提交消息与历史文件内容。
- 检查常见令牌、私钥、个人主目录路径、非 noreply 邮箱和本地凭据文件；错误提示只显示文件和类别，不打印匹配值。
- 这些模式检查不能识别所有隐私信息，不会识别图片内文字或解包任意二进制文件。仍需人工检查真实姓名、截图、日志和 PR 内容。
- Hooks 是本地保护，克隆后要运行配置脚本；GitHub 网页编辑等路径不会执行本地 hooks。不要通过 `--no-verify` 绕过问题。

## 四、在另一台 Codex 打开项目

打开新克隆的项目根目录。让 Codex 先读取：

1. `AGENTS.md`：版本、代码约定、协作与隐私要求。
2. `README.md` 和 `docs/ROADMAP.md`：当前实现与目标。
3. `docs/HANDOFF.md`：任务状态、验证证据和下一步。
4. `.agents/skills/neoforge-1-21-1/SKILL.md`：NeoForge 1.21.1 专用技能。

项目级技能在 `.agents/skills/` 随代码同步，符合 [Codex 官方技能发现规则](https://learn.chatgpt.com/docs/build-skills)。如果技能列表没有出现，重新打开 Codex；仍不可见时，让它直接读取该文件。若电脑另有同名个人技能，本项目以仓库内版本为准。

不要依赖某台电脑上的聊天内容作为唯一交接记录。分支上的代码、提交和 `HANDOFF.md` 是下一台电脑能够核对的交接依据。

## 五、日常协作

### 开始一个新任务

确认没有未提交改动后执行；若有改动，先明确归属并保存：

```bash
git status
git fetch origin
git switch main
git pull --ff-only
git switch -c feat/observation
```

`feat/observation` 是示例名称。按任务使用 `feat/`、`fix/` 或 `docs/` 前缀。不要让两台电脑同时在同一个任务分支写提交。

### 将同一任务交给另一台电脑

交出方：

1. 更新该任务在 `docs/HANDOFF.md` 的记录，写清已完成、未完成、修改范围和真实检查结果。
2. 暂存明确的文件，查看差异，提交并推送任务分支。

```bash
git add docs/HANDOFF.md
# 再逐个 git add 本任务修改的源码或资源文件
git diff --cached
git commit -m "Describe the completed checkpoint"
git push -u origin HEAD
```

接手方：

```bash
git status
git fetch origin
git switch --track origin/feat/observation
```

上面 `switch --track` 仅用于本地还没有该分支的情况。分支已存在时，使用 `git switch feat/observation`，再 `git pull --ff-only`。接手后先阅读该分支上的交接记录。

**推送成功后再交接；接手开始后原电脑暂停该任务。** 如双方都产生新提交，停止操作、保留两边提交，检查差异后再处理，不要强制覆盖远端。

### 两台同时开发不同任务

各自从最新 `main` 创建不同分支，约定文件范围。例如一边负责观测物品逻辑，另一边负责独立文档或资源。共同注册类、翻译/数据生成器以及 `HANDOFF.md` 是容易冲突的文件，应先约定由哪一方修改；交接条目按任务追加，保留其他任务记录。

完成后：

```bash
git push -u origin HEAD
gh pr create --base main
```

PR 写明行为变化、验证结果和限制。完成评审并解决冲突后再合并。两台电脑随后 fetch，并在工作区干净时更新本地 `main`。

## 六、构建与首次资源下载

- macOS/Linux：`./gradlew build`；有 Bash 时也可用 `./scripts/dev.sh build`。
- Windows：`.\gradlew.bat build`。
- 新内容改变后先运行 `runData`，再 `build`，提交生成的 JSON。
- 首次游戏资源下载可能较大；`.tools/` 不随 Git 分发，每台电脑独立下载或配置自己的 JDK。
- 仓库没有携带另一台电脑的 `build/` 临时资源索引。不要假设在全新机器上加 `-x downloadAssets` 就能运行数据生成或客户端。

验收：构建成功、Codex 能读取项目技能、隐私 hooks 已启用、测试任务分支可成功推送。客户端能否运行需要另外启动 `runClient` 实际检查。

## 七、给另一台 Codex 的启动指令

> 阅读 AGENTS.md、README.md、docs/COLLABORATION.md 和 docs/HANDOFF.md，并使用仓库内 neoforge-1-21-1 技能。先检查工作区、当前分支、Java 21、GitHub 登录与本仓库的 noreply 身份和 hooks 配置。若这是旧副本迁移，保留改动并按协作指南重新克隆，不合并旧历史。确认接手的任务和文件范围后，在任务分支继续开发。不要覆盖未提交工作；完成后更新对应交接条目，报告实际验证结果。所有公开提交、文档、日志和 PR 都避免个人信息；不要更改现有版本锁定。
