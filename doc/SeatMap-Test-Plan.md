# Test plan

Run `bash scripts/verify.sh` with assertions enabled (the script sets -ea).
There are 30 JUnit tests. SeatMapTest is an abstract reusable suite and
SeatMap1Test supplies its constructor factory.

| Area | Cases |
| --- | --- |
| Construction | Default map, rectangular maps, one-seat maps |
| Kernel changes | First/middle/last seats, all 682 small-map states |
| Preservation | Independent boolean grid checked after every update |
| Enhanced queries | Empty, partial, full, released maps |
| Standard | Empty/occupied/repeated clear, new-instance independence, empty/full/different-dimension transfer |
| Ownership | Source and receiver modified independently after transfer |
| Common methods | Reflexivity, symmetry, transitivity, null, unrelated types, dimensions, reservations, equal hashes, stable text |
| Boundaries | Maximum row and maximum column without full traversal |
| Assertions | Zero/negative dimensions, overflow, invalid coordinates, duplicate reserve, empty release, null/self transfer |
| Composition | Ordered unique assignments, repeated full responses, independent desks |
| Console input | Valid changes, duplicate operations, malformed/overflowed numbers, EOF, full flight |

The 682 states cover all reservation subsets on layouts from 1x1 to 3x3;
they are scenarios in a test, not 682 separately counted JUnit tests.
Assertions on invalid kernel calls test implementation diagnostics, not a
public guarantee for calls outside the preconditions. No numerical code
coverage or cross-implementation equality result is claimed.

SeatMapSecondaryTest uses a fresh fixture for each test and separately checks
query preservation, restored-state hashing, and the full-to-available transition.

See [Testing questions](../test/Q&A.md) for the reasoning behind the fixtures,
independent expected results, assertion checks, and console output capture.
