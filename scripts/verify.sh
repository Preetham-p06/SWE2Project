#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
mkdir -p bin
javac --release 21 -Xlint:all -Werror -cp 'lib/*' -d bin @doc/java-sources.txt
java -ea -cp 'bin:lib/*' org.junit.runner.JUnitCore SeatMap1Test SeatMap1BoundaryTest FlightCheckInTest
python3 scripts/check-clients.py
javadoc -quiet -Werror -d bin/javadoc -classpath 'lib/*' \
  -tag 'mathmodel:a:Model:' -tag 'initially:a:Initial value:' \
  -tag 'requires:a:Requires:' -tag 'ensures:a:Ensures:' \
  -tag 'updates:a:Updates:' -tag 'convention:a:Convention:' \
  -tag 'correspondence:a:Correspondence:' src/*.java
