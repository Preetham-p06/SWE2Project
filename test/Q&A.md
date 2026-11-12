# Testing questions

## Why is SeatMapTest abstract?

It defines tests for the SeatMap contracts without choosing a concrete
implementation. SeatMap1Test supplies the factory method that creates a
SeatMap1. Another implementation could reuse the same contract tests by
providing its own factory.

## What are the test values based on?

Small layouts make it possible to check every seat after an update. The
suite includes a single seat, rectangular layouts, and reservations at the
first, middle, and last coordinates. It also checks all reservation patterns
for layouts with one through three rows and seats per row: 682 states in all.

Maximum-dimension tests check selected coordinates on sparse maps. They do
not call methods that scan every seat, since that would take too long.

## How are the expected results calculated?

A separate boolean grid records whether each seat should be reserved. Tests
compare every coordinate, both dimensions, the available count, and fullness
against that grid. They do not rely on SeatMap.equals to decide whether a
kernel update produced the correct state.

## Why have separate secondary-method tests?

SeatMapSecondaryTest checks that queries leave the map unchanged, that
reserving and releasing restores the original hash and text, and that
releasing one seat changes a full map back to an available map. Its @Before
method creates a fresh fixture for each test.

## How is console output tested?

scripts/check-clients.py starts each demo as a separate process, supplies
input, captures standard output and standard error, and checks the expected
messages. It also rejects a failed exit status or a timeout. JUnit can test
captured output too; this project uses a separate script for complete console
sessions and JUnit for component and service methods.

## Why must assertions be enabled?

The implementation uses assertions to check preconditions during development.
The verification script supplies -ea. Boundary tests deliberately violate
some preconditions to check those diagnostics. These tests do not promise
that invalid calls have a defined public result when assertions are disabled.

## Does a passing suite prove every possible behavior?

No. The tests cover documented cases and exhaustively check small reservation
states. They do not exhaust every legal aircraft size, every possible input
string, or every alternative SeatMap implementation. No statement-coverage
percentage is claimed.
