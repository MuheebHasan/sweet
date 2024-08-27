package my_sweet_management_system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
 import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ooorder {
    private static final Logger logger = Logger.getLogger(ooorder.class.getName());

    static Map<String, String[]> orders = new HashMap<>();
    private static Map<String, String[]> products = new HashMap<>();
    private static Map<String, Double> discounts = new HashMap<>();
    private static List<Integer> availableOrderIds = new ArrayList<>();

    public ooorder() {
        loadOrders();
        loadProducts();
        loadDiscounts();
        initializeOrderIds(); // Initialize order IDs
    }

    private static void initializeOrderIds() {
        for (int i = 1; i <= 100; i++) {
            availableOrderIds.add(i);
        }
        Collections.shuffle(availableOrderIds); // Shuffle to randomize
    }

    private static int generateRandomOrderId() {
        if (availableOrderIds.isEmpty()) {
            throw new IllegalStateException("No more available order IDs.");
        }
        return availableOrderIds.remove(0); // Remove and return the first element
    }

    public static String addOrder(String productId, int quantity) {
        if (!products.containsKey(productId)) {
            return "Product ID does not exist";
        }
        
        int orderId = generateRandomOrderId(); // Generate random order ID
        
        if (orders.containsKey(String.valueOf(orderId))) {
            return "Order ID already exists";
        }

        String[] productInfo = products.get(productId);
        int availableQuantity = Integer.parseInt(productInfo[3]);
        if (quantity > availableQuantity) {
            return "Not enough quantity available";
        }

        double pricePerUnit = Double.parseDouble(productInfo[2]);
        double totalPrice = pricePerUnit * quantity;

        // Adding order with a default status of "Pending"
        orders.put(String.valueOf(orderId), new String[]{productId, productInfo[0], productInfo[1], String.valueOf(quantity), String.valueOf(totalPrice), "Pending"});

        productInfo[3] = String.valueOf(availableQuantity - quantity);
        products.put(productId, productInfo);

        saveOrders();
        saveProducts();
        return "Order added successfully with Order ID: " + orderId;
    }

    public static void saveOrders() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("order.txt"))) {
            for (Map.Entry<String, String[]> entry : orders.entrySet()) {
                String orderId = entry.getKey();
                String[] orderInfo = entry.getValue();
                writer.write(String.join(",", orderId, orderInfo[0], orderInfo[1], orderInfo[2], orderInfo[3], orderInfo[4], orderInfo[5]));
                writer.newLine();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving orders.", e);
        }
    }

    public static void updateOrderStatus(String orderId, String newStatus) {
        if (orders.containsKey(orderId)) {
            String[] orderInfo = orders.get(orderId);
            orderInfo[5] = newStatus;
            orders.put(orderId, orderInfo);
            saveOrders(); // Save the updates to the file
        } else {
            logger.info("Order ID not found.");
        }
    }

    private static void loadOrders() {
        loadFromFile("order.txt", (line) -> {
            String[] parts = line.split(",");
            
            // Ignore the header line or any incorrect line
            if (parts[0].equals("Order ID") || parts.length != 7) {
                return;
            }
            
            try {
                String orderId = parts[0];
                String productId = parts[1];
                String productName = parts[2];
                String description = parts[3];
                String quantity = parts[4];
                String totalPrice = parts[5];
                String status = parts[6];
                orders.put(orderId, new String[]{productId, productName, description, quantity, totalPrice, status});
            } catch (NumberFormatException e) {
                logger.info("Error parsing order: " + line);
            }
        });
    }

    private static void loadFromFile(String filename, LineProcessor processor) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processor.process(line);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading file: " + filename, e);
        }
    }

    public void viewOrders() {
        logger.info("Order List:");
        for (Map.Entry<String, String[]> entry : orders.entrySet()) {
            String orderId = entry.getKey();
            String[] orderInfo = entry.getValue();
            logger.info(String.format("Order ID: %s, Product ID: %s, Product Name: %s, Description: %s, Quantity: %s, Total Price: %s, Status: %s",
                    orderId, orderInfo[0], orderInfo[1], orderInfo[2], orderInfo[3], orderInfo[4], orderInfo[5]));
        }
    }

    private static void saveProducts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("product.txt", false))) {
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
            logger.log(Level.SEVERE, "Error saving products.", e);
        }
    }

    private static void saveDiscounts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("discounts.txt", false))) {
            for (Map.Entry<String, Double> entry : discounts.entrySet()) {
                String discountName = entry.getKey();
                Double discountRate = entry.getValue();
                writer.write(String.format("%s,%s", discountName, discountRate));
                writer.newLine();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving discounts.", e);
        }
    }

    public static String addDiscount(String discountName, double discountRate) {
        if (discounts.containsKey(discountName)) {
            return "Discount already exists";
        }

        discounts.put(discountName, discountRate);
        saveDiscounts(); // Save new discount
        return "Discount added successfully";
    }

    public static String applyDiscount(String discountName) {
        if (!discounts.containsKey(discountName)) {
            return "Discount does not exist";
        }

        double discountRate = discounts.get(discountName);
        applyDiscountToAllOrders(discountName, discountRate);
        return "Discount applied successfully";
    }

    private static void applyDiscountToAllOrders(String discountName, double discountRate) {
        for (Map.Entry<String, String[]> entry : orders.entrySet()) {
            String[] orderInfo = entry.getValue();
            // Parse the original price and apply the discount
            double originalPrice = Double.parseDouble(orderInfo[4]);
            double discountedPrice = originalPrice * (1 - discountRate / 100);
            orderInfo[4] = String.valueOf(discountedPrice);
            // Update the order in the map
            orders.put(entry.getKey(), orderInfo);
        }

        saveOrders(); // Save updated orders
    }

    public static void printOrders(String message, Map<String, String[]> orders) {
        logger.info(message);
        for (Map.Entry<String, String[]> entry : orders.entrySet()) {
            String orderId = entry.getKey();
            String[] orderInfo = entry.getValue();
            logger.info(String.format("Order ID: %s, Product ID: %s, Product Name: %s, Description: %s, Quantity: %s, Total Price: %s, Status: %s",
                    orderId, orderInfo[0], orderInfo[1], orderInfo[2], orderInfo[3], orderInfo[4], orderInfo[5]));
        }
    }

    public static String addOrder(String orderId, String productId, int quantity) {
        if (!products.containsKey(productId)) {
            return "Product ID does not exist";
        }
        if (orders.containsKey(orderId)) {
            return "Order ID already exists";
        }

        String[] productInfo = products.get(productId);
        int availableQuantity = Integer.parseInt(productInfo[3]);
        if (quantity > availableQuantity) {
            return "Not enough quantity available";
        }

        double pricePerUnit = Double.parseDouble(productInfo[2]);
        double totalPrice = pricePerUnit * quantity;

        // Adding order with a default status of "Pending"
        orders.put(orderId, new String[]{productId, productInfo[0], productInfo[1], String.valueOf(quantity), String.valueOf(totalPrice), "Pending"});

        productInfo[3] = String.valueOf(availableQuantity - quantity);
        products.put(productId, productInfo);

        saveOrders();
        saveProducts();
        return "Order added successfully";
    }

    public static String deleteOrder(String orderId) {
        if (orders.containsKey(orderId)) {
            String[] orderInfo = orders.get(orderId);
            String productId = orderInfo[0];
            int quantity = Integer.parseInt(orderInfo[3]);

            if (products.containsKey(productId)) {
                String[] productInfo = products.get(productId);
                int availableQuantity = Integer.parseInt(productInfo[3]);
                productInfo[3] = String.valueOf(availableQuantity + quantity);
                products.put(productId, productInfo);
            }

            orders.remove(orderId);
            saveOrders();
            saveProducts();
            return "Order deleted successfully";
        } else {
            return "Order ID does not exist";
        }
    }

    private static void loadDiscounts() {
        loadFromFile("discounts.txt", (line) -> {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                try {
                    String discountName = parts[0];
                    double discountRate = Double.parseDouble(parts[1]);
                    discounts.put(discountName, discountRate);
                } catch (NumberFormatException e) {
                    logger.info("Error parsing discount: " + line);
                }
            }
        });
    }

    public static String updateOrder(String orderId, Map<String, String> updatedDetails) {
        if (!orders.containsKey(orderId)) {
            return "Order ID does not exist";
        }

        String[] orderInfo = orders.get(orderId);
        String oldProductId = orderInfo[0];
        int oldQuantity = Integer.parseInt(orderInfo[3]);

        String[] oldProductInfo = products.get(oldProductId);
        int availableQuantity = Integer.parseInt(oldProductInfo[3]);
        oldProductInfo[3] = String.valueOf(availableQuantity + oldQuantity);
        products.put(oldProductId, oldProductInfo);

        String newProductId = updatedDetails.getOrDefault("Product ID", oldProductId);
        String[] newProductInfo = products.get(newProductId);

        if (newProductInfo == null) {
            return "New Product ID does not exist";
        }

        int newQuantity = Integer.parseInt(updatedDetails.getOrDefault("Quantity", orderInfo[3]));
        if (newQuantity > Integer.parseInt(newProductInfo[3])) {
            return "Not enough quantity available for the new product";
        }

        double pricePerUnit = Double.parseDouble(newProductInfo[2]);
        double newTotalPrice = pricePerUnit * newQuantity;

        orderInfo[0] = newProductId;
        orderInfo[1] = newProductInfo[0];
        orderInfo[2] = newProductInfo[1];
        orderInfo[3] = String.valueOf(newQuantity);
        orderInfo[4] = String.valueOf(newTotalPrice);

        newProductInfo[3] = String.valueOf(Integer.parseInt(newProductInfo[3]) - newQuantity);
        products.put(newProductId, newProductInfo);

        orders.put(orderId, orderInfo);

        saveOrders();
        saveProducts();
        return "Order details updated successfully";
    }

    public static boolean orderExists(String orderId) {
        return orders.containsKey(orderId);
    }

    private static void loadProducts() {
        loadFromFile("product.txt", (line) -> {
            String[] parts = line.split(",");
            if (parts.length == 5) {
                String productId = parts[0];
                String productName = parts[1];
                String description = parts[2];
                String price = parts[3];
                String availability = parts[4];
                products.put(productId, new String[]{productName, description, price, availability});
            }
        });
    }

    public static void main(String[] args) {
        ooorder orderManager = new ooorder();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean exit = false;
            while (!exit) {
                logger.info("\n1. View Orders");
                logger.info("2. Add Order");
                logger.info("3. Delete Order");
                logger.info("4. Update Order");
                logger.info("5. Apply Discount");
                logger.info("6. Add Discount");
                logger.info("7. Update Order Status");
                logger.info("8. Exit");

                logger.info("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        orderManager.viewOrders();
                        break;
                    case 2:
                        logger.info("Enter Order ID: ");
                        String orderId = scanner.nextLine();
                        logger.info("Enter Product ID: ");
                        String productId = scanner.nextLine();
                        logger.info("Enter Quantity: ");
                        int quantity = scanner.nextInt();
                        scanner.nextLine();
                        String result = addOrder(orderId, productId, quantity);
                        logger.info(result);
                        break;
                    case 3:
                        logger.info("Enter Order ID: ");
                        String deleteOrderId = scanner.nextLine();
                        String deleteResult = deleteOrder(deleteOrderId);
                        logger.info(deleteResult);
                        break;
                    case 4:
                        logger.info("Enter Order ID: ");
                        String updateOrderId = scanner.nextLine();
                        Map<String, String> updatedDetails = new HashMap<>();
                        logger.info("Enter new Product ID (or press Enter to keep current): ");
                        String newProductId = scanner.nextLine();
                        if (!newProductId.isEmpty()) {
                            updatedDetails.put("Product ID", newProductId);
                        }
                        logger.info("Enter new Quantity (or press Enter to keep current): ");
                        String newQuantityStr = scanner.nextLine();
                        if (!newQuantityStr.isEmpty()) {
                            updatedDetails.put("Quantity", newQuantityStr);
                        }
                        String updateResult = updateOrder(updateOrderId, updatedDetails);
                        logger.info(updateResult);
                        break;
                    case 5:
                        logger.info("Enter Discount Name: ");
                        String discountName = scanner.nextLine();
                        String applyDiscountResult = applyDiscount(discountName);
                        logger.info(applyDiscountResult);
                        break;
                    case 6:
                        logger.info("Enter Discount Name: ");
                        String discountNameAdd = scanner.nextLine();
                        logger.info("Enter Discount Rate: ");
                        double discountRate = scanner.nextDouble();
                        scanner.nextLine();
                        String addDiscountResult = addDiscount(discountNameAdd, discountRate);
                        logger.info(addDiscountResult);
                        break;
                    case 7:
                        logger.info("Enter Order ID: ");
                        String statusOrderId = scanner.nextLine();
                        logger.info("Enter New Status: ");
                        String newStatus = scanner.nextLine();
                        updateOrderStatus(statusOrderId, newStatus);
                        break;
                    case 8:
                        logger.info("Exiting...");
                        exit = true; // Set exit to true to break the loop
                        break;
                    default:
                        logger.info("Invalid choice, please try again.");
                }
            }
        }
    }

    @FunctionalInterface
    private interface LineProcessor {
        void process(String line);
    }
}
