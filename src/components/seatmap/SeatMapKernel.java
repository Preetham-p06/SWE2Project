package components.seatmap;

import components.standard.Standard;

/**
 * Kernel interface for an aircraft seat map. Coordinates start at one.
 *
 * @mathmodel <pre>
 * type SeatMapKernel is modeled by
 *   (rows: integer, seatsPerRow: integer,
 *    reserved: finite set of (integer, integer))
 * constraint
 *   rows &gt; 0 and seatsPerRow &gt; 0 and
 *   rows * seatsPerRow &lt;= 2147483647 and
 *   for all (r, s) in reserved
 *     (1 &lt;= r &lt;= rows and 1 &lt;= s &lt;= seatsPerRow)
 * </pre>
 * @initially <pre>
 * (): this = (1, 1, {})
 * (int rows, int seatsPerRow):
 *   requires rows &gt; 0 and seatsPerRow &gt; 0 and
 *     rows * seatsPerRow &lt;= 2147483647
 *   ensures this = (rows, seatsPerRow, {})
 * </pre>
 * <p>Every implementation provides both constructors. Standard operations use
 * the no-argument initial value. Thus clear resets to (1, 1, {}), newInstance
 * returns a fresh initial object of the same dynamic type, and transferFrom
 * moves the whole value and resets its source. Transfer requires distinct
 * objects of the same dynamic type. Dimensions need not match. Model
 * arithmetic uses mathematical integers; dimensions are preserved by seat
 * operations, not necessarily by Standard operations.</p>
 */
public interface SeatMapKernel extends Standard<SeatMap> {

    /**
     * Reports the row count.
     *
     * @return the number of rows
     * @ensures rows = this.rows and this = #this
     */
    int rows();

    /**
     * Reports the number of seats in a row.
     *
     * @return the number of seats per row
     * @ensures seatsPerRow = this.seatsPerRow and this = #this
     */
    int seatsPerRow();

    /**
     * Checks whether a seat is available.
     *
     * @param row
     *            one-based row number
     * @param seat
     *            one-based seat number
     * @return true exactly when the seat is available
     * @requires 1 &lt;= row &lt;= this.rows and 1 &lt;= seat &lt;= this.seatsPerRow
     * @ensures isAvailable = ((row, seat) is not in this.reserved)
     *          and this = #this
     */
    boolean isAvailable(int row, int seat);

    /**
     * Reserves an available seat.
     *
     * @param row
     *            one-based row number
     * @param seat
     *            one-based seat number
     * @updates this
     * @requires 1 &lt;= row &lt;= this.rows and 1 &lt;= seat &lt;= this.seatsPerRow
     *           and (row, seat) is not in this.reserved
     * @ensures this = (#this.rows, #this.seatsPerRow,
     *                 #this.reserved union {(row, seat)})
     */
    void reserve(int row, int seat);

    /**
     * Releases a reserved seat.
     *
     * @param row
     *            one-based row number
     * @param seat
     *            one-based seat number
     * @updates this
     * @requires 1 &lt;= row &lt;= this.rows and 1 &lt;= seat &lt;= this.seatsPerRow
     *           and (row, seat) is in this.reserved
     * @ensures this = (#this.rows, #this.seatsPerRow,
     *                 #this.reserved minus {(row, seat)})
     */
    void release(int row, int seat);
}
