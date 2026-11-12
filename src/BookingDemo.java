import java.util.Scanner;

import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

import components.seatmap.SeatMap;
import components.seatmap.SeatMap1;

/** Direct use of SeatMap for choosing and canceling individual seats. */
public final class BookingDemo {

    /** No instances of this utility class. */
    private BookingDemo() {
    }

    /**
     * Runs a booking session. Input is one command per line.
     *
     * @param args
     *            unused command-line arguments
     */
    public static void main(String[] args) {
        final int rows = 3;
        final int seats = 4;
        final int commandSize = 3;
        SeatMap map = new SeatMap1(rows, seats);
        Scanner input = new Scanner(System.in);
        SimpleWriter out = new SimpleWriter1L();
        out.println("Commands: reserve ROW SEAT, release ROW SEAT, show, quit");
        boolean done = false;
        while (!done && input.hasNextLine()) {
            String command = input.nextLine().trim();
            String[] parts = command.split("\\s+");
            if (command.equals("quit")) {
                done = true;
            } else if (command.equals("show")) {
                out.println(map.toString());
                out.println("Available: " + map.countAvailable());
            } else if (parts[0].equals("reserve") || parts[0].equals("release")) {
                if (parts.length != commandSize) {
                    out.println("Enter exactly two coordinates.");
                } else {
                    try {
                        int row = Integer.parseInt(parts[1]);
                        int seat = Integer.parseInt(parts[2]);
                        if (row < 1 || row > map.rows() || seat < 1
                                || seat > map.seatsPerRow()) {
                            out.println("Invalid seat.");
                        } else if (parts[0].equals("reserve")) {
                            if (map.isAvailable(row, seat)) {
                                map.reserve(row, seat);
                                out.println("Reserved.");
                            } else {
                                out.println("Seat is already reserved.");
                            }
                        } else if (!map.isAvailable(row, seat)) {
                            map.release(row, seat);
                            out.println("Released.");
                        } else {
                            out.println("Seat is already available.");
                        }
                    } catch (NumberFormatException exception) {
                        out.println("Coordinates must be integers.");
                    }
                }
            } else {
                out.println("Unknown command.");
            }
        }
        input.close();
        out.close();
    }
}
