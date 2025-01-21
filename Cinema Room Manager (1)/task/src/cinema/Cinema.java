package cinema;
import java.util.Scanner;

public class Cinema {

    private final int rows;
    private final int cols;
    private final int totalSeats;
    private final String[][] activeTheater;

    // Statistic values
    private final int fullHouseIncome;
    private int ticketsPurchased = 0;
    private int currentIncome = 0;

    public static Cinema cinemaBuilder(Scanner sc) {
        // Build an empty theater
        System.out.println("Enter the number of rows:");
        int inRows = sc.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int inCols = sc.nextInt();

        return new Cinema(inRows, inCols);
    }

    public Cinema(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.totalSeats = rows * cols;
        this.activeTheater = new String[rows + 1][cols + 1];

        // Create empty theater
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= cols; j++) {
                if (i == 0) {
                    // Populate header row
                    this.activeTheater[i][j] = ((j == 0) ? " " : String.valueOf(j));
                } else {
                    // Populate all other rows
                    this.activeTheater[i][j] = ((j == 0) ? String.valueOf(i) : "S");
                }
            }
        }

        // Calculate full house income (Max income if all tickets sold)
        int fullHouseIncome = 0;
        for (int i = 1; i <= rows; i++) {
            fullHouseIncome += (getTicketPrice(i) * cols);
        }
        this.fullHouseIncome = fullHouseIncome;
    }

    public void showOptionsMenu() {
        String[] menuOptions = {"1. Show the seats", "2. Buy a ticket", "3. Statistics", "0. Exit"};
        for (String option : menuOptions) {
            System.out.println(option);
        }
    }

    public int getTicketPrice(int seatRow) {
        return (this.totalSeats <= 60 || (seatRow <= (this.rows - 1) / 2)) ? 10 : 8;
    }

    public void buyTicket(Scanner sc) {
         while (true) {
            // Get current ticket sale information
            System.out.println("Enter a row number:");
            int seatRow = sc.nextInt();
            System.out.println("Enter a seat number in that row:");
            int seatCol = sc.nextInt();

            if ((0 > seatRow || seatRow > this.rows) || (0 > seatCol || seatCol > this.cols)) {
                // Selected seat does not exist
                System.out.println("Wrong input!");
            } else if ("B".equals(this.activeTheater[seatRow][seatCol])) {
                // Check if seat is sold
                 System.out.println("That ticket has already been purchased!");
            } else {
                // Show price of ticket, mark seat sold
                int ticketPrice = getTicketPrice(seatRow);
                System.out.printf("Ticket price: $%d%n", ticketPrice);
                this.activeTheater[seatRow][seatCol] = "B";
                this.currentIncome += ticketPrice;
                this.ticketsPurchased += 1;
                break;
            }
        }
    }

    public void showStatistics() {
        System.out.printf("Number of purchased tickets: %d%n", this.ticketsPurchased);
        System.out.printf("Percentage: %.2f%%%n", ((double) this.ticketsPurchased / this.totalSeats) * 100);
        System.out.printf("Current income: $%d%n", this.currentIncome);
        System.out.printf("Total income: $%d%n", this.fullHouseIncome);
    }

    @Override
    public String toString() {
        StringBuilder cinemaString = new StringBuilder("Cinema:").append(System.lineSeparator());
        for (String[] row : this.activeTheater) {
            for(String seat : row) {
                cinemaString.append(seat).append(" ");
            }
            cinemaString.append(System.lineSeparator());
        }
        return cinemaString.toString().trim();
    }

    public static void cinemaApp() {
        Scanner sc = new Scanner(System.in);

        Cinema cinema = Cinema.cinemaBuilder(sc);

        // Display the options menu
        int userSelection;
        do {
            cinema.showOptionsMenu();

            userSelection = sc.nextInt();

            switch (userSelection) {
                case 1:
                    // Print theater
                    System.out.println(cinema.toString());
                    break;
                case 2:
                    // Buy a ticket
                    cinema.buyTicket(sc);
                    break;
                case 3:
                    // Show stats
                    cinema.showStatistics();
                    break;
                case 0:
                    // Exit loop
                    break;
                default:
                    System.out.println("Please select a valid option.");
                    cinema.showOptionsMenu();
            }
        } while (userSelection != 0);


    }

    public static void main(String[] args) {
        // Activate the Cinema Application
        cinemaApp();
    }
}