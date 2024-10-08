package my_sweet_management_system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserManagementSystem {
    private static final Logger logger = Logger.getLogger(UserManagementSystem.class.getName());

    private Map<String, User> users = new HashMap<>();
    private String currentId;

    public UserManagementSystem() {
        loadData();
    }

    // Load data from the text files
    private void loadData() {
        loadFromFile("user.txt");
        loadFromFile("owner.txt");
        loadFromFile("supplier.txt");
    }

    private void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine(); // Read the header line and discard it
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String id = parts[0];
                    String type = parts[1];
                    String name = parts[2];
                    String address = parts[3];
                    String phone = parts[4];
                    users.put(id, new User(id, type, name, address, phone));
                } else {
                    // Log a warning if the line format is invalid
                    logger.warning("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading data from file: " + filename, e);
        }
    }

    // Add a new user
    public String addUser(String id, String type, String name, String address, String phone) {
        if (users.containsKey(id)) {
            return "ID already exists";
        }

        // Ensure type is one of the predefined options
        if (!type.equalsIgnoreCase("user") && !type.equalsIgnoreCase("owner") && !type.equalsIgnoreCase("supplier")) {
            return "Invalid user type";
        }

        users.put(id, new User(id, type, name, address, phone));
        saveData();

        return "User account created successfully";
    }

    // Set the current user ID
    public void setCurrentId(String id) {
        this.currentId = id;
    }

    // Update user details
    public String updateUser(String field, String value) {
        User user = users.get(currentId);
        if (user == null) {
            return "ID does not exist";
        }
        switch (field) {
            case "Name":
                user.setName(value);
                break;
            case "Address":
                user.setAddress(value);
                break;
            case "Phone":
                user.setPhone(value);
                break;
            default:
                return "Invalid field";
        }
        saveData(); // Save changes to all files
        return "User account updated successfully";
    }

    public String deleteUser() {
        User user = users.remove(currentId);
        if (user != null) {
            saveData(); // Save changes to all files
            return "User account deleted successfully";
        } else {
            logger.log(Level.WARNING, "Current ID not found: " + currentId); // Debug statement
            return "ID does not exist";
        }
    }

    // Save data to all text files
    private void saveData() {
        saveToFile("data.txt");
        saveToFile("user.txt");
        saveToFile("owner.txt");
        saveToFile("supplier.txt");
    }

    private void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("id,type,name,address,phone");
            writer.newLine();
            for (User user : users.values()) {
                // Write only the relevant user type data to the specific file
                if (filename.equals("data.txt") ||
                        (filename.equals("user.txt") && user.getType().equalsIgnoreCase("user")) ||
                        (filename.equals("owner.txt") && user.getType().equalsIgnoreCase("owner")) ||
                        (filename.equals("supplier.txt") && user.getType().equalsIgnoreCase("supplier"))) {
                    writer.write(String.format("%s,%s,%s,%s,%s",
                            user.getId(),
                            user.getType(),
                            user.getName(),
                            user.getAddress(),
                            user.getPhone()));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving data to file: " + filename, e);
        }
    }

    // Inner class for User
    public static class User {
        private String id;
        private String type;
        private String name;
        private String address;
        private String phone;

        public User(String id, String type, String name, String address, String phone) {
            this.id = id;
            this.type = type;
            this.name = name;
            this.address = address;
            this.phone = phone;
        }

        public String getId() { return id; }
        public String getType() { return type; }
        public String getName() { return name; }
        public String getAddress() { return address; }
        public String getPhone() { return phone; }

        public void setName(String name) { this.name = name; }
        public void setAddress(String address) { this.address = address; }
        public void setPhone(String phone) { this.phone = phone; }
    }

    public static void main(String[] args) {
        UserManagementSystem system = new UserManagementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            logger.info("\n** Admin Menu **");
            logger.info("1. Add User");
            logger.info("2. Update User");
            logger.info("3. Delete User");
            logger.info("4. View All Users");
            logger.info("5. Exit");
            logger.info("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    logger.info("Enter User ID: ");
                    String id = scanner.nextLine();
                    logger.info("Enter User Type (User/Owner/Supplier): ");
                    String type = scanner.nextLine().toLowerCase(); // Convert to lower case for consistency
                    logger.info("Enter User Name: ");
                    String name = scanner.nextLine();
                    logger.info("Enter User Address: ");
                    String address = scanner.nextLine();
                    logger.info("Enter User Phone: ");
                    String phone = scanner.nextLine();
                    String addResult = system.addUser(id, type, name, address, phone);
                    logger.info(addResult);
                    break;

                case 2:
                    logger.info("Enter User ID to update: ");
                    String updateId = scanner.nextLine();
                    system.setCurrentId(updateId);
                    logger.info("Enter field to update (Name/Address/Phone): ");
                    String field = scanner.nextLine();
                    logger.info("Enter new value: ");
                    String value = scanner.nextLine();
                    String updateResult = system.updateUser(field, value);
                    logger.info(updateResult);
                    break;

                case 3:
                    logger.info("Enter User ID to delete: ");
                    String deleteId = scanner.nextLine();
                    system.setCurrentId(deleteId);
                    String deleteResult = system.deleteUser();
                    logger.info(deleteResult);
                    break;

                case 4:
                    logger.info("All users:");
                    for (User user : system.users.values()) {
                        logger.info(String.format("ID: %s, Type: %s, Name: %s, Address: %s, Phone: %s",
                                user.getId(), user.getType(), user.getName(), user.getAddress(), user.getPhone()));
                    }
                    break;

                case 5:
                    logger.info("Exiting...");
                    scanner.close();
                    return;

                default:
                    logger.warning("Invalid choice. Please try again.");
            }
        }
    }
}
