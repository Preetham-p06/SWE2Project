#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
: "${CHECKSTYLE_JAR:?Set CHECKSTYLE_JAR to checkstyle-10.21.4-all.jar}"
report=$(mktemp)
trap 'rm -f "$report"' EXIT
java -jar "$CHECKSTYLE_JAR" -c .vscode/osu-cse-checkstyle-config.xml src test > "$report"
cat "$report"
if grep -Eq '\[(WARN|ERROR)\]' "$report"; then
  exit 1
fi
