package my_sweet_management_system;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class TESTMAIN {
    private static final Logger logger = Logger.getLogger(TESTMAIN.class.getName());

    private static Map<String, String> messages = new HashMap<>();
    private static Map<String, String> emails = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);


    public void sendMessage(String sender, String recipient, String message) {
        messages.put(recipient, "From " + sender + ": " + message);
        logger.info("Message sent.");
    }

    public String receiveMessage(String recipient) {
        return messages.getOrDefault(recipient, "No new messages.");
    }

    public void sendEmail(String sender, String recipient, String subject, String body) {
        emails.put(recipient, "From: " + sender + "\nSubject: " + subject + "\nBody: " + body);
        logger.info("Email sent.");
    }

    public String receiveEmail(String recipient) {
        return emails.getOrDefault(recipient, "No new emails.");
    }

    public static void main(String[] args) {
        TestUtility testUtility = new TestUtility();
        boolean running = true;

        while (running) {
            logger.info("Welcome to Sweet Management System");
            logger.info("1. Test Utilities");
            logger.info("2. Exit");
            logger.info("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    testUtilities(scanner, testUtility);
                    break;
                case 2:
                    running = false; // Exit the program
                    break;
                default:
                    logger.info("Invalid choice. Please try again.");
            }
        }

        scanner.close(); // Close the scanner when done
    }

    private static void testUtilities(Scanner scanner, TestUtility testUtility) {
        boolean testMenuRunning = true;

        while (testMenuRunning) {
            logger.info("\n** Test Utilities **");
            logger.info("1. Send Message");
            logger.info("2. Receive Message");
            logger.info("3. Update User Details");
            logger.info("4. View and Update Orders");
            logger.info("5. Back to Main Menu");
            logger.info("Enter your choice: ");
            int testChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (testChoice) {
                case 1:
                    logger.info("Enter sender name: ");
                    String sender = scanner.nextLine();
                    logger.info("Enter recipient name: ");
                    String recipient = scanner.nextLine();
                    logger.info("Enter message: ");
                    String message = scanner.nextLine();
                    testUtility.sendMessage(sender, recipient, message);
                    break;

                case 2:
                    logger.info("Enter recipient name: ");
                    String recipientForMessage = scanner.nextLine();
                    String receivedMessage = testUtility.receiveMessage(recipientForMessage);
                    logger.info(receivedMessage);
                    break;

                case 3:
                    logger.info("Enter your name (owner) to authorize update: ");
                    String ownerName = scanner.nextLine();
                    logger.info("Enter current username to update: ");
                    String oldUsernameToUpdate = scanner.nextLine();
                    logger.info("Enter new username: ");
                    String newUsername = scanner.nextLine();
                    logger.info("Enter new address: ");
                    String newAddress = scanner.nextLine();
                    logger.info("Enter new contact number: ");
                    String newContactNumber = scanner.nextLine();
                    testUtility.updateUserDetails(ownerName, oldUsernameToUpdate, newUsername, newAddress, newContactNumber);
                    break;

        
                case 4:
                    logger.info("Enter user type (user/owner): ");
                    String userType = scanner.nextLine();
                    logger.info("Enter your username: ");
                    String username = scanner.nextLine();
                    testUtility.viewAndUpdateOrder(userType, username);
                    break;

                case 5:
                    testMenuRunning = false;
                    break;

                default:
                    logger.info("Invalid choice. Please try again.");
            }
        }
    }
}
