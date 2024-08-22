package mySweetmanagementsystem;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TESTMAIN {
	
	
	  private static Map<String, String> messages = new HashMap<>();
	    private static Map<String, String> emails = new HashMap<>();

	    public void sendMessage(String sender, String recipient, String message) {
	        messages.put(recipient, "From " + sender + ": " + message);
	        System.out.println("Message sent.");
	    }

	    public String receiveMessage(String recipient) {
	        return messages.getOrDefault(recipient, "No new messages.");
	    }

	    public void sendEmail(String sender, String recipient, String subject, String body) {
	        emails.put(recipient, "From: " + sender + "\nSubject: " + subject + "\nBody: " + body);
	        System.out.println("Email sent.");
	    }

	    public String receiveEmail(String recipient) {
	        return emails.getOrDefault(recipient, "No new emails.");
	    }
    
    private static Map<String, String> userMap = new HashMap<>();
    private static Map<String, String> ownerMap = new HashMap<>();
    private static Map<String, String> supplierMap = new HashMap<>();
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TestUtility testUtility = new TestUtility();
        boolean running = true;
        
        while (running) {
            System.out.println("Welcome to Sweet Management System");
            System.out.println("1. Test Utilities");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
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
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close(); // Close the scanner when done
    }
    
    private static void testUtilities(Scanner scanner, TestUtility testUtility) {
        boolean testMenuRunning = true;

        while (testMenuRunning) {
            System.out.println("\n** Test Utilities **");
            System.out.println("1. Send Message");
            System.out.println("2. Receive Message");
            System.out.println("3. Send Email");
            System.out.println("4. Receive Email");
            System.out.println("5. Update Order Status");
            System.out.println("6. Get Order Status");
            System.out.println("7. Update User Details");
            System.out.println("8. Update Supplier Details");
            System.out.println("9. View and Update Orders");
            System.out.println("10. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int testChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (testChoice) {
                case 1:
                    System.out.print("Enter sender name: ");
                    String sender = scanner.nextLine();
                    System.out.print("Enter recipient name: ");
                    String recipient = scanner.nextLine();
                    System.out.print("Enter message: ");
                    String message = scanner.nextLine();
                    testUtility.sendMessage(sender, recipient, message);
                    break;

                case 2:
                    System.out.print("Enter recipient name: ");
                    String recipientForMessage = scanner.nextLine();
                    String receivedMessage = testUtility.receiveMessage(recipientForMessage);
                    System.out.println(receivedMessage);
                    break;

                case 3:
                    System.out.print("Enter sender: ");
                    String emailSender = scanner.nextLine();
                    System.out.print("Enter recipient email: ");
                    String emailRecipient = scanner.nextLine();
                    System.out.print("Enter subject: ");
                    String subject = scanner.nextLine();
                    System.out.print("Enter body: ");
                    String body = scanner.nextLine();
                    testUtility.sendEmail(emailSender, emailRecipient, subject, body);
                    break;

                case 4:
                    System.out.print("Enter recipient email: ");
                    String emailRecipientForCheck = scanner.nextLine();
                    String receivedEmail = testUtility.receiveEmail(emailRecipientForCheck);
                    System.out.println("Received Email: " + receivedEmail);
                    break;

                case 5:
                    System.out.print("Enter order ID: ");
                    String orderId = scanner.nextLine();
                    System.out.print("Enter new status: ");
                    String status = scanner.nextLine();
                    // Implement the updateOrderStatus method in TestUtility
                    //testUtility.updateOrderStatus(orderId, status);
                    break;

                case 6:
                    System.out.print("Enter order ID: ");
                    String orderIdForStatus = scanner.nextLine();
                    // Implement the getOrderStatus method in TestUtility
                  //  String orderStatus = testUtility.getOrderStatus(orderIdForStatus);
                   // System.out.println("Order Status: " + orderStatus);
                    break;

                case 7:
                    System.out.print("Enter your name (owner) to authorize update: ");
                    String ownerName = scanner.nextLine();
                    System.out.print("Enter current username to update: ");
                    String oldUsernameToUpdate = scanner.nextLine();
                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Enter new address: ");
                    String newAddress = scanner.nextLine();
                    System.out.print("Enter new contact number: ");
                    String newContactNumber = scanner.nextLine();
                    testUtility.updateUserDetails(ownerName, oldUsernameToUpdate, newUsername, newAddress, newContactNumber);
                    break;

                case 8:
                    System.out.print("Enter your current username: ");
                    String currentUsername = scanner.nextLine();
                    System.out.print("Enter new username: ");
                    String newUsername1 = scanner.nextLine();
                    System.out.print("Enter new address: ");
                    String newAddress1 = scanner.nextLine();
                    System.out.print("Enter new phone number: ");
                    String newPhone = scanner.nextLine();
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.nextLine();
                    testUtility.updateSupplierDetails(currentUsername, newUsername1, newAddress1, newPhone, newEmail);
                    String confirmationMessage = testUtility.getLastUpdateMessage();
                    System.out.println(confirmationMessage);
                    break;

                case 9:
                    System.out.print("Enter user type (user/owner): ");
                    String userType = scanner.nextLine();
                    System.out.print("Enter your username: ");
                    String username = scanner.nextLine();
                    testUtility.viewAndUpdateOrder(userType, username);
                    break;

                case 10:
                    testMenuRunning = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
