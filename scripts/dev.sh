#!/usr/bin/env bash
set -euo pipefail

project_dir="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd)"

# Prefer the isolated project JDK when present; otherwise honor the user's Java setup.
if [[ -x "$project_dir/.tools/java21/Contents/Home/bin/java" ]]; then
    export JAVA_HOME="$project_dir/.tools/java21/Contents/Home"
elif [[ -x "$project_dir/.tools/java21/bin/java" ]]; then
    export JAVA_HOME="$project_dir/.tools/java21"
fi

if [[ -n "${JAVA_HOME:-}" ]]; then
    java_bin="$JAVA_HOME/bin/java"
else
    java_bin=java
fi

java_version="$("$java_bin" -version 2>&1)"
if [[ ! "$java_version" =~ version\ \"21[.\"] ]]; then
    printf '%s\n' 'The Galaxy requires JDK 21. Set JAVA_HOME to a JDK 21 installation.' >&2
    exit 1
fi

# Keep local build dependencies out of global Gradle configuration by default.
export GRADLE_USER_HOME="${GRADLE_USER_HOME:-$project_dir/.tools/gradle-home}"
cd -- "$project_dir"
exec ./gradlew "${@:-build}"
