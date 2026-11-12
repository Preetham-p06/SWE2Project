import components.seatmap.SeatMap;
import components.seatmap.SeatMap1;
/**
 * A check-in service that uses SeatMap as its private representation.
 * Clients ask for an assignment without selecting coordinates.
 */
public final class FlightCheckIn {

    /** The layout owned by this check-in service. */
    private final SeatMap layout;

    /**
     * Creates a check-in service with no assigned seats.
     *
     * @param rows
     *            positive row count
     * @param seatsPerRow
     *            positive seats per row
     * @requires rows > 0 and seatsPerRow > 0 and
     *           rows * seatsPerRow &lt;= Integer.MAX_VALUE
     */
    public FlightCheckIn(int rows, int seatsPerRow) {
        this.layout = new SeatMap1(rows, seatsPerRow);
    }

    /**
     * Assigns the next seat in row-major order.
     *
     * @return the one-based seat number, or zero if full
     * @ensures if a seat was available, exactly the first available seat
     *          becomes reserved; otherwise the layout is unchanged
     */
    public int assignSeat() {
        int assigned = 0;
        for (int r = 0; r < this.layout.rows() && assigned == 0; r++) {
            for (int s = 0; s < this.layout.seatsPerRow() && assigned == 0; s++) {
                if (this.layout.isAvailable(r + 1, s + 1)) {
                    this.layout.reserve(r + 1, s + 1);
                    assigned = r * this.layout.seatsPerRow() + s + 1;
                }
            }
        }
        return assigned;
    }

    /**
     * Reports how many assignments remain possible.
     *
     * @return the number of passengers who can still be assigned seats
     */
    public int remainingSeats() {
        return this.layout.countAvailable();
    }
}
