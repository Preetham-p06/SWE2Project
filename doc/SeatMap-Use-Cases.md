# Use cases

## Direct component use: manual booking

BookingDemo creates a 3x4 SeatMap. A customer or agent chooses coordinates,
reserves or releases them, and views the layout. For example:

```text
reserve 1 2
reserve 3 4
release 1 2
show
quit
```

The resulting layout is `3 x 4 [OOOO / OOOO / OOOX]`, with 11 available seats.
The client checks coordinates and status before invoking kernel updates.
Input errors do not become kernel precondition violations.

## Component as representation: automatic check-in

FlightCheckIn owns a SeatMap that it does not expose to clients. assignSeat
chooses the first free position in row-major order and returns a one-based
seat number; zero signals full capacity. remainingSeats reports availability.
CheckInDemo processes seven passengers on a six-seat aircraft. The first six
receive distinct seats, and the seventh is told the flight is full.

This example separates an allocation policy from the seat-storage component.
The first example lets a user select and reverse individual reservations;
the second asks a higher-level service to allocate seats automatically.
Neither example is a production airline system or persists passenger records.
