# Future ideas

The current component only tracks whether seats are available or reserved.
These are possible extensions, not features included in this submission.

## Seat preferences

A check-in service could choose a window or aisle seat when one is available.
The selection policy would belong in FlightCheckIn or another client. SeatMap
could continue to handle the basic reservation operations.

## Several flights

A program could store a separate SeatMap for each flight number. It could then
look up a flight before reserving a seat. This would build on the component
without adding flight schedules or passenger records to its kernel.

## Saved reservations

A separate program could save a layout to a file and restore it later. It
would need to validate dimensions and reserved coordinates before rebuilding
the map. This would let a booking session continue after the program closes.

## Larger layouts

The current secondary methods visit every seat. That is reasonable for a
small aircraft layout, but expensive for a much larger map. A future design
could investigate faster counting while keeping the contracts and layering
consistent. Any change would need tests showing that counts stay correct
after reserving, releasing, clearing, and transferring.
