# SeatMap

SeatMap tracks reserved seats on an aircraft. Each coordinate is a one-based
row and seat number. The project follows the OSU component structure:

| File | Responsibility |
| --- | --- |
| SeatMapKernel | Primitive operations and Standard inheritance |
| SeatMap | Additional queries |
| SeatMapSecondary | Derived queries and common methods |
| SeatMap1 | OSU Set representation, constructors, kernel and Standard methods |

The component lives in `src/components/seatmap`, with demo programs in `src`
and tests in `test`. The code uses explicit loops, assertion preconditions,
and JavaDoc contracts. Passenger information, payment, and a graphical
interface are outside the component's scope.

## Setup and testing

Install a Java 21 JDK, Python 3, Bash, and curl. Then run from this folder:

```bash
bash scripts/setup.sh
bash scripts/verify.sh
```

Setup downloads the official components.jar, JUnit 4.13.2, and Hamcrest 1.3,
and checks their SHA-256 digests. Jars and compiled files are ignored by Git.
Verification compiles all sources with warnings treated as errors, runs 30
JUnit tests and four console sessions, and generates `bin/javadoc/index.html`.

To run Checkstyle 10.21.4 with the supplied course configuration:

```bash
CHECKSTYLE_JAR=/absolute/path/checkstyle-10.21.4-all.jar bash scripts/check-style.sh
```

The script rejects warnings as well as errors. Test fixture literals have a
documented MagicNumber exception using the course configuration's filter.

## Examples

```bash
java -ea -cp "bin:lib/*" BookingDemo
java -ea -cp "bin:lib/*" CheckInDemo
```

On Windows replace `bin:lib/*` with `bin;lib/*`.

BookingDemo accepts `reserve ROW SEAT`, `release ROW SEAT`, `show`, and `quit`.
`O` means available and `X` means reserved. CheckInDemo shows a separate
FlightCheckIn object assigning six seats and rejecting a seventh passenger.

## Contracts

Both dimensions are positive and their product fits in an int. Reserving
requires an available valid seat; releasing requires a reserved valid seat.
The caller checks these conditions. Assertions provide development diagnostics.
`clear()` restores a 1-by-1 empty map. `newInstance()` returns a fresh default
map. `transferFrom()` moves all state from a distinct object of the same
dynamic type, then resets that source. Dimensions need not match.

The representation is sparse. Queries such as counting, equality, hashing,
and display scan every seat and should not be used on billion-seat layouts.

## Documentation

- [Design](doc/SeatMap-Design.md)
- [Test plan](doc/SeatMap-Test-Plan.md)
- [Testing questions](test/Q&A.md)
- [Use cases](doc/SeatMap-Use-Cases.md)
- [Demo justification](src/DemoJustification.md)
- [Future ideas](doc/FUTUREIDEAS.md)
- [Reflection](doc/SeatMap-Reflection.md)
- [Rubric checklist](doc/SeatMap-Checklist.md)
- [Validation results](doc/SeatMap-Validation.txt)

The original template instructions remain under doc. Current DIRECTIONS.md
supersedes the old per-part submission workflow. Keep the submission PR open
and unmerged so main retains the original template.
