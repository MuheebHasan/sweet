package mySweetmanagementsystem;

import java.io.*;
import java.util.*;

public class MonitoringAndReportingww {

    private Map<String, List<String>> usersByCity;
    private Map<String, Integer> salesByProduct;

    public MonitoringAndReportingww() {
        this.usersByCity = new HashMap<>();
        this.salesByProduct = new HashMap<>();
        loadUserData("user.txt");
        loadSalesData("order.txt");
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
            e.printStackTrace();
        }
    }

    private void loadSalesData(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // تخطي العنوان
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 6) continue; // تحقق من طول البيانات
                String productName = parts[2]; // استخراج اسم المنتج
                int quantity = Integer.parseInt(parts[4]); // استخراج الكمية

                // التأكد من أن الكمية تُجمع بشكل صحيح
                int currentQuantity = salesByProduct.getOrDefault(productName, 0);
                int newQuantity = currentQuantity + quantity;
                salesByProduct.put(productName, newQuantity);

               
            }
        } catch (IOException e) {
            e.printStackTrace();
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

        System.out.println("City with most users: " + cityWithMostUsers + " with " + userCount + " users");
        System.out.println("Most ordered product: " + mostOrderedProduct);

        // Write report to file
        StringBuilder report = new StringBuilder();
        report.append("City with most users: ").append(cityWithMostUsers).append(" with ").append(userCount).append(" users\n");
        report.append("Most ordered product: ").append(mostOrderedProduct).append("\n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("report.txt"))) {
            writer.write(report.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // New method to display each product and its total sales
    public void displayProductSales() {
        System.out.println("Product sales data:");
        salesByProduct.forEach((product, quantity) -> 
            System.out.println("Product: " + product + ", Quantity Sold: " + quantity)
        );

        // Write product sales data to file
        StringBuilder productSalesReport = new StringBuilder();
        productSalesReport.append("Product Sales Data:\n");
        salesByProduct.forEach((product, quantity) -> 
            productSalesReport.append("Product: ").append(product).append(", Quantity Sold: ").append(quantity).append("\n")
        );

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("report.txt"))) {
            writer.write(productSalesReport.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private String loggedInUserEmail; // To store email after login
    private String loggedInUsername;  // To store username after login
    private static final String FEEDBACK_FILE = "feedback.txt";
    private static final String COUNTER_FILE = "feedback_counter.txt";

    

    // Method to get role by email
    public String getRoleByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader("logsign.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[2].equals(email)) {
                    return parts[3]; // Return role
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading logsign.txt file.");
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
            System.out.println("Error reading feedback counter file. Defaulting to 1.");
        }
        return 1; // Default to 1 if there is an error
    }

    // Method to update the feedback ID counter
    private void updateFeedbackIdCounter(int newCounter) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COUNTER_FILE))) {
            writer.write(String.valueOf(newCounter));
        } catch (IOException e) {
            System.out.println("Error writing feedback counter file.");
        }
    }

    // Method to add feedback
    public void addFeedback(Scanner scanner) {
        System.out.println("Please enter your feedback:");
        String feedback = scanner.nextLine();
        
        // Get the current feedback ID counter and increment it
        int feedbackId = getFeedbackIdCounter();
        updateFeedbackIdCounter(feedbackId + 1);
        
        // Get the username of the logged-in user
        String username = getLoggedInUsername();
        
        // Write feedback to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FEEDBACK_FILE, true))) {
            writer.write(feedbackId + ";" + username + ";" + feedback);
            writer.newLine();
            System.out.println("Thank you for your feedback! We appreciate your input.");
        } catch (IOException e) {
            System.out.println("Error writing to feedback.txt file.");
        }
    }
    public static void displayFeedback() {
        try (BufferedReader reader = new BufferedReader(new FileReader("feedback.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading feedback file.");
        }
    }
  

    
    public boolean login(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("logsign.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[2].equals(email) && parts[1].equals(password)) {
                    loggedInUserEmail = email;  // تعيين البريد الإلكتروني
                    loggedInUsername = parts[0]; // تعيين اسم المستخدم
                    System.out.println("Logged in user email set to: " + loggedInUserEmail); // جملة لتصحيح الأخطاء
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading logsign.txt file.");
        }
        return false;
    }


    public void updateLogsignFile(String newEmail, String newPassword) {
        if (loggedInUserEmail == null) {
            System.out.println("Error: No user is currently logged in.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("logsign.txt"));
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
                System.out.println("Account details updated.");
            } else {
                System.out.println("No matching record found for the logged-in user.");
            }

        } catch (IOException e) {
            System.out.println("Error updating logsign.txt file.");
        }

        new File("logsign.txt").delete();
        new File("logsign_temp.txt").renameTo(new File("logsign.txt"));
    }


}

