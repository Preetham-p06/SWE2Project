# Why these demos?

The project connects aircraft seating with two different programming tasks.

BookingDemo uses SeatMap directly. The user chooses a seat, reserves it,
changes their mind, or checks the remaining layout. This shows the kernel
operations and the client checks needed before calling them.

CheckInDemo works through FlightCheckIn, which owns a private SeatMap. The
caller asks for the next assignment without choosing coordinates or getting
access to the underlying map. FlightCheckIn adds a row-by-row allocation
policy and handles a full aircraft by returning zero.

The distinction is how the component is used, not just the starting seat
values: one client calls the component directly, while the other uses it as
the representation of a separate service. Both are small console programs so
the component behavior stays easy to follow.

See [the use-case guide](../doc/SeatMap-Use-Cases.md) for commands and expected
results. The verification script exercises both programs.
