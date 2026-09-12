import java.util.Scanner;

public class MovieTicketBooking {

    static boolean[][] seats = new boolean[5][5];

    static Scanner sc = new Scanner(System.in);

    static double ticketPrice = 500;

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n===== MOVIE TICKET BOOKING SYSTEM =====");
            System.out.println("1. Show Available Seats");
            System.out.println("2. Book Ticket");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. Book Ticket with Discount Coupon");
            System.out.println("5. Weekend Pricing");
            System.out.println("6. Movie Rating");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    showSeats();
                    break;

                case 2:
                    bookTicket();
                    break;

                case 3:
                    cancelTicket();
                    break;

                case 4:
                    bookWithCoupon();
                    break;

                case 5:
                    weekendPricing();
                    break;

                case 6:
                    movieRating();
                    break;

                case 7:
                    System.out.println("Thank you for using Movie Ticket Booking System!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 7);
    }


    // Method to display seats
    static void showSeats() {

        System.out.println("\n======== SEAT STATUS ========");

        for (int i = 0; i < seats.length; i++) {

            for (int j = 0; j < seats[i].length; j++) {

                if (seats[i][j] == false) {
                    System.out.print("[" + (i + 1) + "-" + (j + 1) + "] Available ");
                } 
                else {
                    System.out.print("[" + (i + 1) + "-" + (j + 1) + "] Booked ");
                }
            }

            System.out.println();
        }
    }


    // Method to book a ticket
    static void bookTicket() {

        showSeats();

        System.out.print("\nEnter row number (1-5): ");
        int row = sc.nextInt();

        System.out.print("Enter seat number (1-5): ");
        int seat = sc.nextInt();

        if (row < 1 || row > 5 || seat < 1 || seat > 5) {

            System.out.println("Invalid seat number!");

        } 
        else if (seats[row - 1][seat - 1] == true) {

            System.out.println("Sorry! This seat is already booked.");

        } 
        else {

            seats[row - 1][seat - 1] = true;

            System.out.println("Ticket booked successfully!");
            System.out.println("Seat: Row " + row + ", Seat " + seat);
            System.out.println("Ticket Price: ₹" + ticketPrice);
        }
    }


    // Method to cancel a ticket
    static void cancelTicket() {

        System.out.print("\nEnter row number: ");
        int row = sc.nextInt();

        System.out.print("Enter seat number: ");
        int seat = sc.nextInt();

        if (row < 1 || row > 5 || seat < 1 || seat > 5) {

            System.out.println("Invalid seat number!");

        } 
        else if (seats[row - 1][seat - 1] == false) {

            System.out.println("This seat is not booked.");

        } 
        else {

            seats[row - 1][seat - 1] = false;

            System.out.println("Ticket cancelled successfully!");
        }
    }


    // Method for discount coupon
    static void bookWithCoupon() {

        showSeats();

        System.out.print("\nEnter row number: ");
        int row = sc.nextInt();

        System.out.print("Enter seat number: ");
        int seat = sc.nextInt();

        if (row < 1 || row > 5 || seat < 1 || seat > 5) {

            System.out.println("Invalid seat number!");
            return;
        }

        if (seats[row - 1][seat - 1] == true) {

            System.out.println("Seat already booked!");
            return;
        }

        System.out.print("Enter coupon code: ");
        String coupon = sc.next();

        double price = ticketPrice;

        if (coupon.equals("MOVIE50")) {

            price = price - (price * 0.50);

            System.out.println("50% discount applied!");

        } 
        else if (coupon.equals("MOVIE30")) {

            price = price - (price * 0.30);

            System.out.println("30% discount applied!");

        } 
        else {

            System.out.println("Invalid coupon!");
        }

        seats[row - 1][seat - 1] = true;

        System.out.println("Ticket booked successfully!");
        System.out.println("Final Price: ₹" + price);
    }


    // Method for weekend pricing
    static void weekendPricing() {

        double weekendPrice = ticketPrice + 50;

        System.out.println("\n===== WEEKEND PRICING =====");
        System.out.println("Normal Ticket Price: ₹" + ticketPrice);
        System.out.println("Weekend Ticket Price: ₹" + weekendPrice);
    }


    // Method for movie rating
    static void movieRating() {

        System.out.print("\nEnter movie rating (1-5): ");
        int rating = sc.nextInt();

        if (rating >= 1 && rating <= 5) {

            System.out.println("You rated the movie: " + rating + "/5");

            if (rating == 5) {
                System.out.println("Excellent movie!");
            } 
            else if (rating >= 3) {
                System.out.println("Good movie!");
            } 
            else {
                System.out.println("You didn't like the movie much.");
            }

        } 
        else {

            System.out.println("Invalid rating! Please enter 1 to 5.");
        }
    }
}