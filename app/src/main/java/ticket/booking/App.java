package ticket.booking;

import ticket.booking.entities.User;
import ticket.booking.services.UserBookingService;
import ticket.booking.util.UserServiceUtil;

import java.io.IOException;
import java.util.*;

public class App {

    public static void main(String[] args) {
        System.out.println("Welcome to the Train Booking System");
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        UserBookingService userBookingService;

        try {
            userBookingService = new UserBookingService();
        } catch (IOException ex) {
            ex.printStackTrace(); // Print the stack trace for debugging
            System.out.println("Error initializing the booking system. Please check the logs.");
            return;
        }

        while (option != 7) {
            displayMenu();

            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");
                continue;
            }

            switch (option) {
                case 1 : handleSignUp(scanner, userBookingService);
                case 2 : userBookingService = handleLogin(scanner);
                case 3 : userBookingService.fetchBooking();
                case 4 : handleSearchTrains();
                case 5 : System.out.println("Booking seat functionality not implemented yet.");
                case 6 : handleCancelBooking(scanner, userBookingService);
                case 7 : System.out.println("Thank you for using the Train Booking System. Goodbye!");
                default : System.out.println("Invalid option. Please select a number between 1 and 7.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nChoose an Option:");
        System.out.println("1. Sign Up");
        System.out.println("2. Login");
        System.out.println("3. Fetch Booking");
        System.out.println("4. Search Trains");
        System.out.println("5. Book a Seat");
        System.out.println("6. Cancel a Booking");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void handleSignUp(Scanner scanner, UserBookingService userBookingService) {
        System.out.print("Enter your name to sign up: ");
        String name = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User newUser = new User(name, password, UserServiceUtil.hashPassword(password), new ArrayList<>(), UUID.randomUUID().toString());

        if (userBookingService.signUp(newUser)) {
            System.out.println("Sign-up successful!");
        } else {
            System.out.println("Error during sign-up. Please try again.");
        }
    }

    private static UserBookingService handleLogin(Scanner scanner) {
        System.out.print("Enter your name to login: ");
        String name = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User loginUser = new User(name, password, UserServiceUtil.hashPassword(password), new ArrayList<>(), UUID.randomUUID().toString());

        try {
            UserBookingService userBookingService = new UserBookingService(loginUser);
            System.out.println("Login successful!");
            return userBookingService;
        } catch (IOException e) {
            System.out.println("Login failed. Please try again.");
            return null;
        }
    }

    private static void handleCancelBooking(Scanner scanner, UserBookingService userBookingService) {
        System.out.print("Enter the Ticket ID to cancel: ");
        String ticketId = scanner.nextLine();

        if (userBookingService.cancelBooking(ticketId)) {
            System.out.println("Booking canceled successfully.");
        } else {
            System.out.println("Unable to cancel booking. Please check the Ticket ID.");
        }
    }

    private static void handleSearchTrains() {
        System.out.println("Search Trains functionality not implemented yet.");
    }
}
