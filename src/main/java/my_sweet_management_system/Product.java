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

public class Product {
    private static final Logger LOGGER = Logger.getLogger(Product.class.getName());
    private static Map<String, String[]> products = new HashMap<>();
    
    // Save product data to the file
    private static void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("product.txt"))) {
            writer.write("Product ID,Product Name,Description,Price,Availability");
            writer.newLine();
            for (Map.Entry<String, String[]> entry : products.entrySet()) {
                String productId = entry.getKey();
                String[] productInfo = entry.getValue();
                writer.write(String.format("%s,%s,%s,%s,%s",
                        productId,
                        productInfo[0],
                        productInfo[1],
                        productInfo[2],
                        productInfo[3]));
                writer.newLine();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving product data.", e);
        }
    }

    public static String addProduct(String productId, String productName, String description, String price, String availability) {
        if (products.containsKey(productId)) {
            return "Product ID already exists";
        } else {
            products.put(productId, new String[]{productName, description, price, availability});
            saveData();
            return "Product added successfully";
        }
    }

    public static String deleteProduct(String productId) {
        if (products.containsKey(productId)) {
            products.remove(productId);
            saveData();
            return "Product deleted successfully";
        } else {
            return "Product ID does not exist";
        }
    }

    public static String updateProduct(String productId, Map<String, String> updatedDetails) {
        if (products.containsKey(productId)) {
            String[] productInfo = products.get(productId);
            productInfo[0] = updatedDetails.getOrDefault("Product Name", productInfo[0]);
            productInfo[1] = updatedDetails.getOrDefault("Description", productInfo[1]);
            productInfo[2] = updatedDetails.getOrDefault("Price", productInfo[2]);
            productInfo[3] = updatedDetails.getOrDefault("Availability", productInfo[3]);
            products.put(productId, productInfo);
            saveData();
            return "Product details updated successfully";
        } else {
            return "Product ID does not exist";
        }
    }

    public static boolean productExists(String productId) {
        return products.containsKey(productId);
    }

    private static void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("product.txt"))) {
            String line;
            reader.readLine(); // Skip the header line
            while ((line = reader.readLine()) != null) {
                // Use or store the line read from the file
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String productId = parts[0];
                    String productName = parts[1];
                    String description = parts[2];
                    String price = parts[3];
                    String availability = parts[4];
                    products.put(productId, new String[]{productName, description, price, availability});
                } else {
                    // Log a warning if the line format is invalid
                    LOGGER.warning("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading product data.", e);
        }
    }

    static {
        loadData(); // Load data when class is first used
    }

    public static void main(String[] args) {
        Product productSystem = new Product();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            LOGGER.info("\n--- Manage Products ---");
            LOGGER.info("1. Add Product");
            LOGGER.info("2. Update Product");
            LOGGER.info("3. Delete Product");
            LOGGER.info("4. Exit");
            LOGGER.info("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    LOGGER.info("Enter Product ID: ");
                    String productId = scanner.nextLine();
                    LOGGER.info("Enter Product Name: ");
                    String productName = scanner.nextLine();
                    LOGGER.info("Enter Description: ");
                    String description = scanner.nextLine();
                    LOGGER.info("Enter Price: ");
                    String price = scanner.nextLine();
                    LOGGER.info("Enter Availability: ");
                    String availability = scanner.nextLine();
                    String addResult = Product.addProduct(productId, productName, description, price, availability);
                    LOGGER.info(addResult);
                    break;

                case 2:
                    LOGGER.info("Enter Product ID to update: ");
                    String updateId = scanner.nextLine();
                    if (Product.productExists(updateId)) {
                        LOGGER.info("Enter field to update (Product Name/Description/Price/Availability): ");
                        String field = scanner.nextLine();
                        LOGGER.info("Enter new value: ");
                        String value = scanner.nextLine();
                        Map<String, String> updatedDetails = new HashMap<>();
                        updatedDetails.put(field, value);
                        String updateResult = Product.updateProduct(updateId, updatedDetails);
                        LOGGER.info(updateResult);
                    } else {
                        LOGGER.info("Product ID does not exist.");
                    }
                    break;

                case 3:
                    LOGGER.info("Enter Product ID to delete: ");
                    String deleteId = scanner.nextLine();
                    String deleteResult = Product.deleteProduct(deleteId);
                    LOGGER.info(deleteResult);
                    break;

                case 4:
                    LOGGER.info("Exiting...");
                    scanner.close();
                    return;

                default:
                    LOGGER.info("Invalid choice. Please try again.");
            }
        }
    }
}
