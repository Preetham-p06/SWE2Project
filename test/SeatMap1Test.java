import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/** Runs the contract tests on SeatMap1. */
public final class SeatMap1Test extends SeatMapTest {
    @Override
    protected SeatMap create(int rows, int seats) {
        return new SeatMap1(rows, seats);
    }

    /** Checks the concrete default constructor. */
    @Test
    public void defaultConstructor() {
        SeatMap map = new SeatMap1();
        assertEquals(1, map.rows());
        assertEquals(1, map.seatsPerRow());
        assertTrue(map.isAvailable(1, 1));
    }
}
