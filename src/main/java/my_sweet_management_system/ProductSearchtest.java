package my_sweet_management_system;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductSearchtest {
    private static final Logger logger = Logger.getLogger(ProductSearchtest.class.getName());

    private String productId;
    private String productName;
    private String description;
    private String price;
    private String availability;

    public ProductSearchtest() {
    }
    
    public ProductSearchtest(String productId, String productName, String description, String price, String availability) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.availability = availability;
    }

    // Method to display all products
    public static void showAllProducts() {
        logger.info("\n--- All Available Products ---");
        try (BufferedReader br = new BufferedReader(new FileReader("product.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                logger.info(line);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading the product file.", e);
        }
    }

    // Method to search for a product by ID
    public static List<ProductSearchtest> searchProductById(String productId) {
        List<ProductSearchtest> results = new ArrayList<>();
        logger.info("\n--- Searching for Product ID: " + productId + " ---");
        try (BufferedReader br = new BufferedReader(new FileReader("product.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] productDetails = line.split(",");
                if (productDetails[0].equals(productId)) {
                    results.add(new ProductSearchtest(
                        productDetails[0],
                        productDetails[1],
                        productDetails[2],
                        productDetails[3],
                        productDetails[4]
                    ));
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading the product file.", e);
        }

        if (results.isEmpty()) {
            logger.info("Product ID " + productId + " not found.");
        } else {
            logger.info("Product Found: " + results.get(0));
        }
        return results;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public static List<ProductSearchtest> searchProductByName(String name) {
        return new ArrayList<>();
    }

    public static List<ProductSearchtest> searchProductByDescription(String description) {
        return new ArrayList<>();
    }

    public static List<ProductSearchtest> searchProductByAvailability(String availability) {
        return new ArrayList<>();
    }

    public static void allProductShown() {
        showAllProducts();
    }
    
    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Description: %s, Price: %s, Availability: %s",
                             productId, productName, description, price, availability);
    }
}
