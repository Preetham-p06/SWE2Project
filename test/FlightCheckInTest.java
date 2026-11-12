import static org.junit.Assert.assertEquals;

import org.junit.Test;

/** Checks allocation policy independently of the console demo. */
public final class FlightCheckInTest {

    /** Assignments are unique, ordered, and bounded by capacity. */
    @Test
    public void fillsAircraft() {
        FlightCheckIn desk = new FlightCheckIn(2, 2);
        final int capacity = 4;
        for (int seat = 1; seat <= capacity; seat++) {
            assertEquals(seat, desk.assignSeat());
            assertEquals(capacity - seat, desk.remainingSeats());
        }
        assertEquals(0, desk.assignSeat());
        assertEquals(0, desk.assignSeat());
        assertEquals(0, desk.remainingSeats());
    }

    /** Different desks own separate layouts. */
    @Test
    public void independentDesks() {
        FlightCheckIn first = new FlightCheckIn(1, 1);
        FlightCheckIn second = new FlightCheckIn(1, 1);
        assertEquals(1, first.assignSeat());
        assertEquals(1, second.remainingSeats());
        assertEquals(1, second.assignSeat());
    }
}
