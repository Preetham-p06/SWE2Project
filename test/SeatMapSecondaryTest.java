import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import components.seatmap.SeatMap;
import components.seatmap.SeatMap1;

/** Focused checks for secondary queries and their preservation of state. */
public final class SeatMapSecondaryTest {

    /** Fresh map used for each test. */
    private SeatMap map;

    /** Creates a small rectangular fixture. */
    @Before
    public void setUp() {
        final int seats = 3;
        this.map = new SeatMap1(2, seats);
    }

    /** Querying repeatedly preserves dimensions and every seat. */
    @Test
    public void queriesPreserveState() {
        this.map.reserve(1, 2);
        final int expectedAvailable = 5;
        int originalHash = this.map.hashCode();
        String originalText = this.map.toString();
        assertEquals(expectedAvailable, this.map.countAvailable());
        assertFalse(this.map.isFull());
        assertEquals(originalHash, this.map.hashCode());
        assertEquals(originalText, this.map.toString());
        assertEquals(2, this.map.rows());
        final int seats = 3;
        assertEquals(seats, this.map.seatsPerRow());
        for (int row = 1; row <= 2; row++) {
            for (int seat = 1; seat <= seats; seat++) {
                assertEquals(!(row == 1 && seat == 2),
                        this.map.isAvailable(row, seat));
            }
        }
    }

    /** Releasing a reservation restores the original value hash and text. */
    @Test
    public void restoredStateHasOriginalHash() {
        int emptyHash = this.map.hashCode();
        String emptyText = this.map.toString();
        this.map.reserve(2, 2);
        this.map.release(2, 2);
        assertEquals(emptyHash, this.map.hashCode());
        assertEquals(emptyText, this.map.toString());
    }

    /** Fullness changes when one seat is released from a full map. */
    @Test
    public void fullMapBecomesAvailable() {
        for (int row = 1; row <= this.map.rows(); row++) {
            for (int seat = 1; seat <= this.map.seatsPerRow(); seat++) {
                this.map.reserve(row, seat);
            }
        }
        assertTrue(this.map.isFull());
        assertEquals(0, this.map.countAvailable());
        this.map.release(1, 1);
        assertFalse(this.map.isFull());
        assertEquals(1, this.map.countAvailable());
    }
}
