package my_sweet_management_system;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class TESTMAIN {
    private static final Logger logger = Logger.getLogger(TESTMAIN.class.getName());

    private static Map<String, String> messages = new HashMap<>();
    private static Map<String, String> emails = new HashMap<>();
 

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
        Scanner scanner = new Scanner(System.in);
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
            logger.info("3. Send Email");
            logger.info("4. Receive Email");
            logger.info("5. Update Order Status");
            logger.info("6. Get Order Status");
            logger.info("7. Update User Details");
            logger.info("8. Update Supplier Details");
            logger.info("9. View and Update Orders");
            logger.info("10. Back to Main Menu");
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
                    logger.info("Enter sender: ");
                    String emailSender = scanner.nextLine();
                    logger.info("Enter recipient email: ");
                    String emailRecipient = scanner.nextLine();
                    logger.info("Enter subject: ");
                    String subject = scanner.nextLine();
                    logger.info("Enter body: ");
                    String body = scanner.nextLine();
                    testUtility.sendEmail(emailSender, emailRecipient, subject, body);
                    break;

                case 4:
                    logger.info("Enter recipient email: ");
                    String emailRecipientForCheck = scanner.nextLine();
                    String receivedEmail = testUtility.receiveEmail(emailRecipientForCheck);
                    logger.info("Received Email: " + receivedEmail);
                    break;

                case 5:
                    logger.info("Enter order ID: ");
                    String orderId = scanner.nextLine();
                    logger.info("Enter new status: ");
                    String status = scanner.nextLine();
                    // Implement the updateOrderStatus method in TestUtility
                    //testUtility.updateOrderStatus(orderId, status);
                    break;

                case 6:
                    logger.info("Enter order ID: ");
                    String orderIdForStatus = scanner.nextLine();
                    // Implement the getOrderStatus method in TestUtility
                    //String orderStatus = testUtility.getOrderStatus(orderIdForStatus);
                    //logger.info("Order Status: " + orderStatus);
                    break;

                case 7:
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

                case 8:
                    logger.info("Enter your current username: ");
                    String currentUsername = scanner.nextLine();
                    logger.info("Enter new username: ");
                    String newUsername1 = scanner.nextLine();
                    logger.info("Enter new address: ");
                    String newAddress1 = scanner.nextLine();
                    logger.info("Enter new phone number: ");
                    String newPhone = scanner.nextLine();
                    logger.info("Enter new email: ");
                    String newEmail = scanner.nextLine();
                    testUtility.updateSupplierDetails(currentUsername, newUsername1, newAddress1, newPhone, newEmail);
                    String confirmationMessage = testUtility.getLastUpdateMessage();
                    logger.info(confirmationMessage);
                    break;

                case 9:
                    logger.info("Enter user type (user/owner): ");
                    String userType = scanner.nextLine();
                    logger.info("Enter your username: ");
                    String username = scanner.nextLine();
                    testUtility.viewAndUpdateOrder(userType, username);
                    break;

                case 10:
                    testMenuRunning = false;
                    break;

                default:
                    logger.info("Invalid choice. Please try again.");
            }
        }
    }
}
