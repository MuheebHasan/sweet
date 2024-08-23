package my_sweet_management_system;

import java.io.*;
import java.util.*;
import java.util.logging.*;

public class MonitoringAndReportingww {

    private static final Logger LOGGER = Logger.getLogger(MonitoringAndReportingww.class.getName());

    private Map<String, List<String>> usersByCity;
    private Map<String, Integer> salesByProduct;
    
    // Define constants for file names
    private static final String USER_DATA_FILE = "user.txt";
    private static final String SALES_DATA_FILE = "order.txt";
    private static final String REPORT_FILE = "report.txt";
    private static final String FEEDBACK_FILE = "feedback.txt";
    private static final String COUNTER_FILE = "feedback_counter.txt";
    private static final String LOGSIGN_FILE = "logsign.txt";

    private String loggedInUserEmail; // To store email after login
    private String loggedInUsername;  // To store username after login

    public MonitoringAndReportingww() {
        this.usersByCity = new HashMap<>();
        this.salesByProduct = new HashMap<>();
        loadUserData(USER_DATA_FILE);
        loadSalesData(SALES_DATA_FILE);
    }

    private void loadUserData(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 5) continue; // Validate data length
                String address = parts[3]; // Extract city
                String user = parts[2]; // Extract user name

                usersByCity.putIfAbsent(address, new ArrayList<>());
                usersByCity.get(address).add(user);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading user data file: {0}", e.getMessage());
        }
    }

    private void loadSalesData(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 6) continue; // Validate data length
                String productName = parts[2]; // Extract product name
                int quantity = Integer.parseInt(parts[4]); // Extract quantity

                // Ensure quantity is accumulated correctly
                int currentQuantity = salesByProduct.getOrDefault(productName, 0);
                int newQuantity = currentQuantity + quantity;
                salesByProduct.put(productName, newQuantity);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading sales data file: {0}", e.getMessage());
        }
    }

    public String getCityWithMostUsers() {
        return usersByCity.entrySet().stream()
                .max(Comparator.comparingInt(e -> e.getValue().size()))
                .map(Map.Entry::getKey)
                .orElse("No Data");
    }

    public int getUserCountInCity(String city) {
        return usersByCity.getOrDefault(city, new ArrayList<>()).size();
    }

    public String getMostOrderedProduct() {
        return salesByProduct.entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse("No Sales Data");
    }

    public void displayStatistics() {
        String cityWithMostUsers = getCityWithMostUsers();
        int userCount = getUserCountInCity(cityWithMostUsers);
        String mostOrderedProduct = getMostOrderedProduct();

        LOGGER.info(String.format("City with most users: %s with %d users", cityWithMostUsers, userCount));
        LOGGER.info(String.format("Most ordered product: %s", mostOrderedProduct));

        // Write report to file
        StringBuilder report = new StringBuilder();
        report.append(String.format("City with most users: %s with %d users%n", cityWithMostUsers, userCount));
        report.append(String.format("Most ordered product: %s%n", mostOrderedProduct));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REPORT_FILE))) {
            writer.write(report.toString());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error writing to report file: {0}", e.getMessage());
        }
    }

    // New method to display each product and its total sales
    public void displayProductSales() {
        LOGGER.info("Product sales data:");
        salesByProduct.forEach((product, quantity) -> 
            LOGGER.info(String.format("Product: %s, Quantity Sold: %d", product, quantity))
        );

        // Write product sales data to file
        StringBuilder productSalesReport = new StringBuilder();
        productSalesReport.append("Product Sales Data:\n");
        salesByProduct.forEach((product, quantity) -> 
            productSalesReport.append(String.format("Product: %s, Quantity Sold: %d%n", product, quantity))
        );

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REPORT_FILE))) {
            writer.write(productSalesReport.toString());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error writing to report file: {0}", e.getMessage());
        }
    }

    // Method to get role by email
    public String getRoleByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOGSIGN_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[2].equals(email)) {
                    return parts[3]; // Return role
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading logsign file: {0}", e.getMessage());
        }
        return "Unknown"; // If role not found
    }

    // Method to get logged-in username
    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    // Method to get the current feedback ID counter
    private int getFeedbackIdCounter() {
        try (BufferedReader reader = new BufferedReader(new FileReader(COUNTER_FILE))) {
            String line = reader.readLine();
            if (line != null) {
                return Integer.parseInt(line.trim());
            }
        } catch (IOException | NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Error reading feedback counter file. Defaulting to 1: {0}", e.getMessage());
        }
        return 1; // Default to 1 if there is an error
    }

    // Method to update the feedback ID counter
    private void updateFeedbackIdCounter(int newCounter) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COUNTER_FILE))) {
            writer.write(String.valueOf(newCounter));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error writing feedback counter file: {0}", e.getMessage());
        }
    }

    // Method to add feedback
    public void addFeedback(Scanner scanner) {
        LOGGER.info("Please enter your feedback:");
        String feedback = scanner.nextLine();
        
        // Get the current feedback ID counter and increment it
        int feedbackId = getFeedbackIdCounter();
        updateFeedbackIdCounter(feedbackId + 1);
        
        // Get the username of the logged-in user
        String username = getLoggedInUsername();
        
        // Write feedback to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FEEDBACK_FILE, true))) {
            writer.write(String.format("%d;%s;%s", feedbackId, username, feedback));
            writer.newLine();
            LOGGER.info("Thank you for your feedback! We appreciate your input.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error writing to feedback file: {0}", e.getMessage());
        }
    }

    public static void displayFeedback() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FEEDBACK_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LOGGER.info(line);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading feedback file: {0}", e.getMessage());
        }
    }

    public boolean login(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOGSIGN_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[2].equals(email) && parts[1].equals(password)) {
                    loggedInUserEmail = email;  // Set email
                    loggedInUsername = parts[0]; // Set username
                    LOGGER.info(String.format("Logged in user email set to: %s", loggedInUserEmail));
                    return true;
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading logsign file: {0}", e.getMessage());
        }
        return false;
    }

    public void updateLogsignFile(String newEmail, String newPassword) {
        if (loggedInUserEmail == null) {
            LOGGER.warning("Error: No user is currently logged in.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(LOGSIGN_FILE));
             BufferedWriter writer = new BufferedWriter(new FileWriter("logsign_temp.txt"))) {

            String line;
            boolean updated = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[2].equals(loggedInUserEmail)) {
                    if (newEmail != null && !newEmail.isEmpty()) {
                        parts[2] = newEmail;
                    }
                    if (newPassword != null && !newPassword.isEmpty()) {
                        parts[1] = newPassword;
                    }
                    updated = true;
                }
                writer.write(String.join(",", parts));
                writer.newLine();
            }

            if (updated) {
                LOGGER.info("Account details updated.");
            } else {
                LOGGER.warning("No matching record found for the logged-in user.");
            }

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error updating logsign file: {0}", e.getMessage());
        }

        new File(LOGSIGN_FILE).delete();
        new File("logsign_temp.txt").renameTo(new File(LOGSIGN_FILE));
    }
}
