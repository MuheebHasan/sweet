package my_sweet_management_system;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestUtility {

    private static final Logger LOGGER = Logger.getLogger(TestUtility.class.getName());

    private static final String OWNER_FILE = "owner.txt";
    private static final String USER_FILE = "user.txt";
    private static final String SUPPLIER_FILE = "supplier.txt";
    private static final String MESSAGE_FILE = "messages.txt";
    private static final String EMAIL_FILE = "emails.txt";
    Scanner scanner = new Scanner(System.in);

    private Set<String> owners;
    private Set<String> users;
    private Set<String> suppliers;
    private Map<String, String> orders;
    private String lastUpdateMessage;

    public TestUtility() {
        owners = loadNamesFromFile(OWNER_FILE);
        users = loadNamesFromFile(USER_FILE);
        suppliers = loadNamesFromFile(SUPPLIER_FILE);
        orders = new HashMap<>();
        lastUpdateMessage = "";
        loadOrdersFromFile("order.txt");
    }

    private void loadOrdersFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    String orderId = parts[0].trim();
                    String details = parts[1].trim();
                    orders.put(orderId, details);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading order file.", e);
        }
    }

    private Set<String> loadNamesFromFile(String fileName) {
        Set<String> names = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                names.add(line.trim());
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading file: " + fileName, e);
        }
        return names;
    }

    public void sendMessage(String sender, String recipient, String message) {
        if (isValidSender(sender) && isValidRecipient(recipient)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(MESSAGE_FILE, true))) {
                writer.write(recipient + ": " + sender + ": " + message + "\n");
                writer.flush();
                LOGGER.info("Message sent successfully!");
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error writing to message file.", e);
            }
        } else {
            LOGGER.warning("Invalid sender or recipient.");
        }
    }

    private boolean isValidSender(String sender) {
        return owners.contains(sender) || users.contains(sender) || suppliers.contains(sender);
    }

    private boolean isValidRecipient(String recipient) {
        return users.contains(recipient) || suppliers.contains(recipient);
    }

    public String receiveMessage(String recipient) {
        StringBuilder messages = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(MESSAGE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ", 3);
                if (parts.length == 3 && parts[0].trim().equals(recipient)) {
                    messages.append(parts[1].trim()).append(": ").append(parts[2].trim()).append("\n");
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading from message file.", e);
        }
        if (messages.length() > 0) {
            return messages.toString();
        } else {
            return "No new messages for " + recipient;
        }
    }

    public void sendEmail(String sender, String recipientEmail, String subject, String body) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EMAIL_FILE, true))) {
            writer.write(recipientEmail + ": " + sender + ": " + subject + ": " + body + "\n");
            writer.flush();
            LOGGER.info("Email sent from " + sender + " to " + recipientEmail + " with subject: " + subject);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error writing to email file.", e);
        }
    }

    public String receiveEmail(String recipientEmail) {
        StringBuilder emails = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(EMAIL_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ", 4);
                if (parts.length == 4 && parts[0].trim().equals(recipientEmail)) {
                    emails.append("From ").append(parts[1].trim()).append(" with subject: ")
                        .append(parts[2].trim()).append(" - ").append(parts[3].trim()).append("\n");
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading from email file.", e);
        }
        if (emails.length() > 0) {
            return emails.toString();
        } else {
            return "No new emails for " + recipientEmail;
        }
    }

    public void updateUserDetails(String owner, String oldUsername, String newUsername, String newAddress, String newPhone) {
        if (!owners.contains(owner)) {
            LOGGER.warning("You are not authorized to update user details.");
            return;
        }

        File userFile = new File(USER_FILE);
        File tempFile = new File("user_temp.txt");
        boolean userFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(userFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && parts[2].trim().equals(oldUsername)) {
                    line = parts[0] + "," + parts[1] + "," + newUsername + "," + newAddress + "," + newPhone;
                    userFound = true;
                }
                writer.write(line + "\n");
            }

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error updating user details.", e);
            return;
        }

        if (userFound) {
            if (!userFile.delete()) {
                LOGGER.warning("Error deleting original file.");
                return;
            }

            if (!tempFile.renameTo(userFile)) {
                LOGGER.warning("Error renaming temporary file to the original file.");
            } else {
                LOGGER.info("User details updated successfully.");
            }
        } else {
            if (!tempFile.delete()) {
                LOGGER.warning("Error deleting temporary file.");
            }
            LOGGER.info("Username not found.");
        }
    }

    public void updateSupplierDetails(String currentUsername, String newUsername, String newAddress, String newPhone, String newEmail) {
        File supplierFile = new File(SUPPLIER_FILE);
        File tempFile = new File("supplier_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(supplierFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean supplierFound = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6 && parts[2].trim().equals(currentUsername)) {
                    line = parts[0] + "," + parts[1] + "," + newUsername + "," + newAddress + "," + newPhone + "," + newEmail;
                    supplierFound = true;
                }
                writer.write(line + "\n");
            }

            if (!supplierFound) {
                lastUpdateMessage = "Supplier username not found.";
            } else {
                lastUpdateMessage = "Supplier details updated successfully.";
            }

        } catch (IOException e) {
            lastUpdateMessage = "Error updating supplier details: " + e.getMessage();
            LOGGER.log(Level.SEVERE, lastUpdateMessage, e);
            return;
        }

        if (!supplierFile.delete()) {
            lastUpdateMessage = "Error deleting original file.";
            LOGGER.warning(lastUpdateMessage);
            return;
        }

        if (!tempFile.renameTo(supplierFile)) {
            lastUpdateMessage = "Error renaming temporary file to the original file.";
            LOGGER.warning(lastUpdateMessage);
        }
    }

    public String getLastUpdateMessage() {
        return lastUpdateMessage;
    }

    public void viewAndUpdateOrder(String userType, String username) {

        if ("owner".equalsIgnoreCase(userType)) {
            LOGGER.info("Viewing and updating orders:");
            boolean hasOrders = false;

            for (Map.Entry<String, String> entry : orders.entrySet()) {
                LOGGER.info("Order ID: " + entry.getKey() + " - " + entry.getValue());
                hasOrders = true;
            }

            if (!hasOrders) {
                LOGGER.info("No orders found.");
                return;
            }

            LOGGER.info("Enter the Order ID to update (e.g., to mark as 'Ready'): ");
            String orderId = scanner.nextLine().trim();

            if (orders.containsKey(orderId)) {
                LOGGER.info("Updating order ID " + orderId);
                String[] parts = orders.get(orderId).split(",", 2);
                String newDescription = parts[0] + ", Process";
                orders.put(orderId, newDescription);
                LOGGER.info("Order updated to 'Ready'.");
                updateOrderInFile(orderId, newDescription);
            } else {
                LOGGER.info("Order ID not found.");
            }

        } else if ("user".equalsIgnoreCase(userType)) {
            if (!users.contains(username)) {
                LOGGER.info("User not found. No updates will be made.");
                return;
            }

            LOGGER.info("Viewing all available orders:");
            boolean hasOrders = false;

            for (Map.Entry<String, String> entry : orders.entrySet()) {
                LOGGER.info("Order ID: " + entry.getKey() + " - " + entry.getValue());
                hasOrders = true;
            }

            if (!hasOrders) {
                LOGGER.info("No orders found.");
                return;
            }

            LOGGER.info("Enter the Order ID to bind to yourself: ");
            String orderId = scanner.nextLine().trim();

            if (orders.containsKey(orderId)) {
                String[] parts = orders.get(orderId).split(",", 2);
                String newDescription = parts[0] + ", Reserved by " + username;
                orders.put(orderId, newDescription);
                LOGGER.info("Order ID " + orderId + " has been bound to you.");
                updateOrderInFile(orderId, newDescription);
            } else {
                LOGGER.info("Order ID not found.");
            }
        } else {
            LOGGER.info("Invalid user type.");
        }
    }

    private void updateOrderInFile(String orderId, String updatedOrderDetails) {
        File orderFile = new File("order.txt");
        File tempFile = new File("order_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(orderFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean updated = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length > 0 && parts[0].trim().equals(orderId)) {
                    writer.write(updatedOrderDetails + "\n");
                    updated = true;
                } else {
                    writer.write(line + "\n");
                }
            }

            if (!updated) {
                LOGGER.info("Order ID " + orderId + " not found in the file.");
            }

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error updating order in file.", e);
            return;
        }

        if (!orderFile.delete()) {
            LOGGER.severe("Error deleting original file.");
            return;
        }

        if (!tempFile.renameTo(orderFile)) {
            LOGGER.severe("Error renaming temporary file to the original file.");
        } else {
            LOGGER.info("Order file updated successfully.");
        }
    }

  
}
