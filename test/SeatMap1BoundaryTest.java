import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.seatmap.SeatMap;
import components.seatmap.SeatMap1;

/** Sparse capacity boundaries and implementation assertion diagnostics. */
public final class SeatMap1BoundaryTest {
    /** Checks a maximum-size dimension without a full-map traversal. */
    @Test
    public void maximumRow() {
        SeatMap map = new SeatMap1(Integer.MAX_VALUE, 1);
        map.reserve(Integer.MAX_VALUE, 1);
        assertFalse(map.isAvailable(Integer.MAX_VALUE, 1));
        assertTrue(map.isAvailable(1, 1));
        map.release(Integer.MAX_VALUE, 1);
        assertTrue(map.isAvailable(Integer.MAX_VALUE, 1));
    }
    /** Checks a maximum-size dimension without a full-map traversal. */
    @Test
    public void maximumColumn() {
        SeatMap map = new SeatMap1(1, Integer.MAX_VALUE);
        map.reserve(1, Integer.MAX_VALUE);
        assertFalse(map.isAvailable(1, Integer.MAX_VALUE));
        assertTrue(map.isAvailable(1, 1));
        map.release(1, Integer.MAX_VALUE);
        assertTrue(map.isAvailable(1, Integer.MAX_VALUE));
    }

    /** Checks an implementation diagnostic with assertions enabled. */
    @Test(expected = AssertionError.class)
    public void zeroRows() {
        new SeatMap1(0, 1);
    }

    /** Checks an implementation diagnostic with assertions enabled. */
    @Test(expected = AssertionError.class)
    public void negativeSeats() {
        new SeatMap1(1, -1);
    }

    /** Checks an implementation diagnostic with assertions enabled. */
    @Test(expected = AssertionError.class)
    public void overflowCapacity() {
        new SeatMap1(Integer.MAX_VALUE, 2);
    }

    /** Checks an implementation diagnostic with assertions enabled. */
    @Test(expected = AssertionError.class)
    public void badRow() {
        new SeatMap1().isAvailable(0, 1);
    }

    /** Checks an implementation diagnostic with assertions enabled. */
    @Test(expected = AssertionError.class)
    public void badSeat() {
        new SeatMap1().reserve(1, 2);
    }

    /** Checks an implementation diagnostic with assertions enabled. */
    @Test(expected = AssertionError.class)
    public void duplicateReservation() {
        SeatMap map = new SeatMap1();
        map.reserve(1, 1);
        map.reserve(1, 1);
    }

    /** Checks an implementation diagnostic with assertions enabled. */
    @Test(expected = AssertionError.class)
    public void releaseAvailable() {
        new SeatMap1().release(1, 1);
    }

    /** Checks an implementation diagnostic with assertions enabled. */
    @Test(expected = AssertionError.class)
    public void selfTransfer() {
        SeatMap map = new SeatMap1();
        map.transferFrom(map);
    }

    /** Checks an implementation diagnostic with assertions enabled. */
    @Test(expected = AssertionError.class)
    public void nullTransfer() {
        new SeatMap1().transferFrom(null);
    }
}
