import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.seatmap.SeatMap;

/** Contract tests shared by implementations of SeatMap. */
// Literal values identify test fixtures and expected results.
// SUPPRESS CHECKSTYLE MagicNumber FOR NEXT 500 LINES
public abstract class SeatMapTest {

    /**
     * Creates a map for the implementation under test.
     * @param rows number of rows
     * @param seats number of seats per row
     * @return a fresh all-available map
     */
    protected abstract SeatMap create(int rows, int seats);

    /**
     * Compares observable state to an independent grid.
     * @param map map under test
     * @param expected true means reserved
     */
    private static void assertMap(SeatMap map, boolean[][] expected) {
        assertEquals(expected.length, map.rows());
        assertEquals(expected[0].length, map.seatsPerRow());
        int available = 0;
        for (int r = 0; r < expected.length; r++) {
            for (int s = 0; s < expected[r].length; s++) {
                assertEquals(!expected[r][s], map.isAvailable(r + 1, s + 1));
                if (!expected[r][s]) {
                    available++;
                }
            }
        }
        assertEquals(available, map.countAvailable());
        assertEquals(available == 0, map.isFull());
    }

    /** New maps have no reservations. */
    @Test
    public final void emptyRectangle() {
        SeatMap map = this.create(2, 3);
        assertMap(map, new boolean[2][3]);
    }

    /** One seat transitions between empty and full. */
    @Test
    public final void singleSeat() {
        SeatMap map = this.create(1, 1);
        map.reserve(1, 1);
        assertMap(map, new boolean[][] {{true}});
        map.release(1, 1);
        assertMap(map, new boolean[1][1]);
    }

    /** Reservation preserves every other coordinate. */
    @Test
    public final void firstMiddleLast() {
        SeatMap map = this.create(3, 3);
        map.reserve(1, 1);
        map.reserve(2, 2);
        map.reserve(3, 3);
        assertMap(map, new boolean[][] {{true, false, false},
                {false, true, false}, {false, false, true}});
    }

    /** Clear restores the default value. */
    @Test
    public final void clearOccupied() {
        SeatMap map = this.create(2, 3);
        map.reserve(2, 3);
        map.clear();
        assertMap(map, new boolean[1][1]);
    }

    /** Clear is repeatable on empty maps. */
    @Test
    public final void clearEmptyTwice() {
        SeatMap map = this.create(3, 2);
        map.clear();
        map.clear();
        assertMap(map, new boolean[1][1]);
    }

    /** New instances are fresh and do not modify the receiver. */
    @Test
    public final void newInstance() {
        SeatMap map = this.create(2, 2);
        map.reserve(2, 2);
        SeatMap other = map.newInstance();
        assertNotSame(map, other);
        assertEquals(map.getClass(), other.getClass());
        assertMap(other, new boolean[1][1]);
        other.reserve(1, 1);
        assertMap(map, new boolean[][] {{false, false}, {false, true}});
    }

    /** Transfer replaces dimensions and reservations. */
    @Test
    public final void nonemptyTransfer() {
        SeatMap source = this.create(2, 3);
        source.reserve(2, 3);
        SeatMap target = this.create(4, 1);
        target.reserve(1, 1);
        target.transferFrom(source);
        assertMap(target, new boolean[][] {{false, false, false},
                {false, false, true}});
        assertMap(source, new boolean[1][1]);
        source.reserve(1, 1);
        assertTrue(target.isAvailable(1, 1));
        target.clear();
        assertFalse(source.isAvailable(1, 1));
    }

    /** Empty source replaces an occupied receiver. */
    @Test
    public final void emptyTransfer() {
        SeatMap source = this.create(2, 1);
        SeatMap target = this.create(1, 1);
        target.reserve(1, 1);
        target.transferFrom(source);
        assertMap(target, new boolean[2][1]);
        assertMap(source, new boolean[1][1]);
    }

    /** Full sources retain fullness at the receiver. */
    @Test
    public final void fullTransfer() {
        SeatMap source = this.create(1, 1);
        source.reserve(1, 1);
        SeatMap target = this.create(2, 2);
        target.transferFrom(source);
        assertMap(target, new boolean[][] {{true}});
        assertMap(source, new boolean[1][1]);
    }

    /** Equality is symmetric and transitive for equal values. */
    @Test
    public final void equalityAndHash() {
        SeatMap a = this.create(2, 2);
        SeatMap b = this.create(2, 2);
        SeatMap c = this.create(2, 2);
        a.reserve(1, 2);
        b.reserve(1, 2);
        c.reserve(1, 2);
        assertTrue(a.equals(a));
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
        assertTrue(b.equals(c));
        assertTrue(a.equals(c));
        assertEquals(a.hashCode(), b.hashCode());
        assertEquals(a.hashCode(), a.hashCode());
    }

    /** Equality rejects unrelated types, dimensions, and statuses. */
    @Test
    public final void unequalValues() {
        SeatMap a = this.create(2, 3);
        assertFalse(a.equals(null));
        assertFalse(a.equals("map"));
        assertFalse(a.equals(this.create(3, 2)));
        SeatMap b = this.create(2, 3);
        b.reserve(2, 3);
        assertFalse(a.equals(b));
        assertFalse(b.equals(a));
    }

    /** Text shows rows in order without changing state. */
    @Test
    public final void gridText() {
        SeatMap map = this.create(2, 3);
        assertEquals("2 x 3 [OOO / OOO]", map.toString());
        map.reserve(1, 2);
        map.reserve(2, 3);
        assertEquals("2 x 3 [OXO / OOX]", map.toString());
        assertMap(map, new boolean[][] {{false, true, false},
                {false, false, true}});
    }

    /** Tests all 682 possible states of layouts from 1-by-1 through 3-by-3. */
    @Test
    public final void exhaustiveSmallMaps() {
        int states = 0;
        for (int rows = 1; rows <= 3; rows++) {
            for (int seats = 1; seats <= 3; seats++) {
                int capacity = rows * seats;
                for (int bits = 0; bits < (1 << capacity); bits++) {
                    SeatMap map = this.create(rows, seats);
                    boolean[][] expected = new boolean[rows][seats];
                    for (int n = 0; n < capacity; n++) {
                        if ((bits & (1 << n)) != 0) {
                            map.reserve(n / seats + 1, n % seats + 1);
                            expected[n / seats][n % seats] = true;
                            assertMap(map, expected);
                        }
                    }
                    assertMap(map, expected);
                    for (int n = capacity - 1; n >= 0; n--) {
                        if (expected[n / seats][n % seats]) {
                            map.release(n / seats + 1, n % seats + 1);
                            expected[n / seats][n % seats] = false;
                            assertMap(map, expected);
                        }
                    }
                    states++;
                }
            }
        }
        assertEquals(682, states);
    }
}
