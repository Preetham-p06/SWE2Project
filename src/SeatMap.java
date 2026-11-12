/** Enhanced interface for SeatMap. Inherits the kernel mathematical model. */
public interface SeatMap extends SeatMapKernel {

    /**
     * Counts seats that have not been reserved.
     *
     * @return the number of available seats
     * @ensures countAvailable = this.rows * this.seatsPerRow
     *                          - |this.reserved| and this = #this
     */
    int countAvailable();

    /**
     * Checks whether every seat has been reserved.
     *
     * @return true exactly when there are no available seats
     * @ensures isFull = (|this.reserved| = this.rows * this.seatsPerRow)
     *          and this = #this
     */
    boolean isFull();
}
