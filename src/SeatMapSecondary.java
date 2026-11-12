/** Secondary operations implemented using only kernel methods. */
public abstract class SeatMapSecondary implements SeatMap {

    /** Creates the stateless secondary layer. */
    protected SeatMapSecondary() {
    }

    /*
     * Secondary methods
     */

    @Override
    public final int countAvailable() {
        int count = 0;
        for (int r = 0; r < this.rows(); r++) {
            for (int s = 0; s < this.seatsPerRow(); s++) {
                if (this.isAvailable(r + 1, s + 1)) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public final boolean isFull() {
        return this.countAvailable() == 0;
    }

    /*
     * Common methods
     */

    @Override
    public final boolean equals(Object obj) {
        boolean same = this == obj;
        if (!same && obj instanceof SeatMap) {
            SeatMap other = (SeatMap) obj;
            same = this.rows() == other.rows()
                    && this.seatsPerRow() == other.seatsPerRow();
            for (int r = 0; r < this.rows() && same; r++) {
                for (int s = 0; s < this.seatsPerRow() && same; s++) {
                    same = this.isAvailable(r + 1, s + 1)
                            == other.isAvailable(r + 1, s + 1);
                }
            }
        }
        return same;
    }

    @Override
    public final int hashCode() {
        final int factor = 31;
        int hash = factor * this.rows() + this.seatsPerRow();
        for (int r = 0; r < this.rows(); r++) {
            for (int s = 0; s < this.seatsPerRow(); s++) {
                hash *= factor;
                if (!this.isAvailable(r + 1, s + 1)) {
                    hash++;
                }
            }
        }
        return hash;
    }

    @Override
    public final String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.rows()).append(" x ").append(this.seatsPerRow());
        result.append(" [");
        for (int r = 0; r < this.rows(); r++) {
            if (r > 0) {
                result.append(" / ");
            }
            for (int s = 0; s < this.seatsPerRow(); s++) {
                if (this.isAvailable(r + 1, s + 1)) {
                    result.append('O');
                } else {
                    result.append('X');
                }
            }
        }
        result.append(']');
        return result.toString();
    }
}
