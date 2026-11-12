#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
mkdir -p lib
curl -fL --retry 2 'https://cse22x1.engineering.osu.edu/common/components.jar' -o lib/components.jar
curl -fL --retry 2 'https://repo.maven.apache.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar' -o lib/junit-4.13.2.jar
curl -fL --retry 2 'https://repo.maven.apache.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar' -o lib/hamcrest-core-1.3.jar
python3 - <<'CHECK'
from pathlib import Path
import hashlib
assert hashlib.sha256(Path('lib/components.jar').read_bytes()).hexdigest() == 'a87680cb4d653ce646e128f0404eecae94a88224f04df4d3351c4943b0682778', 'components.jar: checksum mismatch'
assert hashlib.sha256(Path('lib/junit-4.13.2.jar').read_bytes()).hexdigest() == '8e495b634469d64fb8acfa3495a065cbacc8a0fff55ce1e31007be4c16dc57d3', 'junit-4.13.2.jar: checksum mismatch'
assert hashlib.sha256(Path('lib/hamcrest-core-1.3.jar').read_bytes()).hexdigest() == '66fdef91e9739348df7a096aa384a5685f4e875584cce89386a7a47251c4d8e9', 'hamcrest-core-1.3.jar: checksum mismatch'
print('Dependency checksums passed.')
CHECK
