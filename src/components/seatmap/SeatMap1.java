package components.seatmap;

import components.set.Set;
import components.set.Set1L;

/**
 * Seat map implemented with an OSU set of reserved seat numbers.
 *
 * @convention <pre>
 * rowCount &gt; 0 and seatCount &gt; 0 and
 * rowCount * seatCount &lt;= Integer.MAX_VALUE and
 * reserved /= null and
 * for all n in reserved (0 &lt;= n &lt; rowCount * seatCount)
 * </pre>
 * @correspondence <pre>
 * this.rows = rowCount and this.seatsPerRow = seatCount and
 * this.reserved = {(r, s): integer * integer |
 *   1 &lt;= r &lt;= rowCount and 1 &lt;= s &lt;= seatCount and
 *   (r - 1) * seatCount + s - 1 is in reserved}
 * </pre>
 */
public final class SeatMap1 extends SeatMapSecondary {

    /*
     * Private members
     */

    /** Number of rows. */
    private int rowCount;

    /** Number of seats per row. */
    private int seatCount;

    /** Exclusively owned set of reserved row-major seat numbers. */
    private Set<Integer> reserved;

    /**
     * Creates an empty representation of the requested size.
     *
     * @param rows
     *            positive row count
     * @param seats
     *            positive seats per row
     * @requires rows &gt; 0 and seats &gt; 0 and rows * seats &lt;= Integer.MAX_VALUE
     * @ensures this = (rows, seats, {})
     */
    private void createNewRep(int rows, int seats) {
        this.rowCount = rows;
        this.seatCount = seats;
        this.reserved = new Set1L<Integer>();
    }

    /*
     * Constructors
     */

    /** Creates the initial one-row, one-seat map. */
    public SeatMap1() {
        this.createNewRep(1, 1);
    }

    /**
     * Creates an all-available map.
     *
     * @param rows
     *            positive row count
     * @param seatsPerRow
     *            positive number of seats per row
     * @requires rows &gt; 0 and seatsPerRow &gt; 0 and
     *           rows * seatsPerRow &lt;= Integer.MAX_VALUE
     * @ensures this = (rows, seatsPerRow, {})
     */
    public SeatMap1(int rows, int seatsPerRow) {
        assert rows > 0 : "Violation of: rows > 0";
        assert seatsPerRow > 0 : "Violation of: seatsPerRow > 0";
        assert (long) rows * seatsPerRow <= Integer.MAX_VALUE
                : "Violation of: capacity fits in int";
        this.createNewRep(rows, seatsPerRow);
    }

    /**
     * Converts coordinates into the private seat number.
     *
     * @param row
     *            one-based row
     * @param seat
     *            one-based seat
     * @return row-major seat number
     * @requires 1 &lt;= row &lt;= rowCount and 1 &lt;= seat &lt;= seatCount
     * @ensures seatNumber = (row - 1) * seatCount + seat - 1
     */
    private int seatNumber(int row, int seat) {
        assert row >= 1 && row <= this.rowCount : "Violation of: valid row";
        assert seat >= 1 && seat <= this.seatCount : "Violation of: valid seat";
        return (row - 1) * this.seatCount + seat - 1;
    }

    /*
     * Standard methods
     */

    @Override
    public void clear() {
        this.createNewRep(1, 1);
    }

    @Override
    public SeatMap newInstance() {
        return new SeatMap1();
    }

    @Override
    public void transferFrom(SeatMap source) {
        assert source != null : "Violation of: source /= null";
        assert source != this : "Violation of: source /= this";
        assert source.getClass() == this.getClass()
                : "Violation of: matching dynamic types";
        SeatMap1 localSource = (SeatMap1) source;
        this.rowCount = localSource.rowCount;
        this.seatCount = localSource.seatCount;
        this.reserved = localSource.reserved;
        localSource.createNewRep(1, 1);
    }

    /*
     * Kernel methods
     */

    @Override
    public int rows() {
        return this.rowCount;
    }

    @Override
    public int seatsPerRow() {
        return this.seatCount;
    }

    @Override
    public boolean isAvailable(int row, int seat) {
        return !this.reserved.contains(this.seatNumber(row, seat));
    }

    @Override
    public void reserve(int row, int seat) {
        int number = this.seatNumber(row, seat);
        assert !this.reserved.contains(number) : "Violation of: available seat";
        this.reserved.add(number);
    }

    @Override
    public void release(int row, int seat) {
        int number = this.seatNumber(row, seat);
        assert this.reserved.contains(number) : "Violation of: reserved seat";
        this.reserved.remove(number);
    }

}
