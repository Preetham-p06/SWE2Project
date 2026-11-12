import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;


/** Demonstrates automatic assignment through a service containing SeatMap. */
public final class CheckInDemo {

    /** No instances of this utility class. */
    private CheckInDemo() {
    }

    /**
     * Processes a group larger than the aircraft capacity.
     *
     * @param args
     *            unused command-line arguments
     */
    public static void main(String[] args) {
        final int rows = 2;
        final int seats = 3;
        final int passengers = 7;
        FlightCheckIn desk = new FlightCheckIn(rows, seats);
        SimpleWriter out = new SimpleWriter1L();
        for (int passenger = 1; passenger <= passengers; passenger++) {
            int number = desk.assignSeat();
            if (number == 0) {
                out.println("Passenger " + passenger + ": flight full");
            } else {
                int row = (number - 1) / seats + 1;
                int seat = (number - 1) % seats + 1;
                out.println("Passenger " + passenger + ": row " + row
                        + ", seat " + seat);
            }
        }
        out.println("Remaining: " + desk.remainingSeats());
        out.close();
    }
}
