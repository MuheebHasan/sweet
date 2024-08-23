package my_sweet_management_system;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.HashMap;

import java.util.Map;
import java.util.Scanner;

public class ooorder {
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
            e.printStackTrace();
        }
    }

    public static void updateOrderStatus(String orderId, String newStatus) {
        if (orders.containsKey(orderId)) {
            String[] orderInfo = orders.get(orderId);
            orderInfo[5] = newStatus;
            orders.put(orderId, orderInfo);
            saveOrders(); // Save the updates to the file
        } else {
            System.out.println("Order ID not found.");
        }
    }
    
    private static void loadOrders() {
        try (BufferedReader reader = new BufferedReader(new FileReader("order.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                
                // Ignore the header line or any incorrect line
                if (parts[0].equals("Order ID") || parts.length != 7) {
                    continue; // Skip this line
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
                    System.out.println("Error parsing order: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void viewOrders() {
        System.out.println("Order List:");
        for (Map.Entry<String, String[]> entry : orders.entrySet()) {
            String orderId = entry.getKey();
            String[] orderInfo = entry.getValue();
            System.out.printf("Order ID: %s, Product ID: %s, Product Name: %s, Description: %s, Quantity: %s, Total Price: %s, Status: %s%n",
                    orderId, orderInfo[0], orderInfo[1], orderInfo[2], orderInfo[3], orderInfo[4], orderInfo[5]);
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
            e.printStackTrace();
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
            e.printStackTrace();
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
        System.out.println(message);
        for (Map.Entry<String, String[]> entry : orders.entrySet()) {
            String orderId = entry.getKey();
            String[] orderInfo = entry.getValue();
            System.out.printf("Order ID: %s, Product ID: %s, Product Name: %s, Description: %s, Quantity: %s, Total Price: %s, Status: %s%n",
                    orderId, orderInfo[0], orderInfo[1], orderInfo[2], orderInfo[3], orderInfo[4], orderInfo[5]);
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
        try (BufferedReader reader = new BufferedReader(new FileReader("discounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String discountName = parts[0];
                    double discountRate = Double.parseDouble(parts[1]);
                    discounts.put(discountName, discountRate);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        try (BufferedReader reader = new BufferedReader(new FileReader("product.txt"))) {
            String line;
            reader.readLine(); // Skip the header line
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String productId = parts[0];
                    String productName = parts[1];
                    String description = parts[2];
                    String price = parts[3];
                    String availability = parts[4];
                    products.put(productId, new String[]{productName, description, price, availability});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ooorder orderManager = new ooorder();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. View Orders");
            System.out.println("2. Add Order");
            System.out.println("3. Delete Order");
            System.out.println("4. Update Order");
            System.out.println("5. Apply Discount");
            System.out.println("6. Add Discount");
            System.out.println("7. Update Order Status");
            System.out.println("8. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    orderManager.viewOrders();
                    break;
                case 2:
                    System.out.print("Enter Order ID: ");
                    String orderId = scanner.nextLine();
                    System.out.print("Enter Product ID: ");
                    String productId = scanner.nextLine();
                    System.out.print("Enter Quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();
                    String result = addOrder(orderId, productId, quantity);
                    System.out.println(result);
                    break;
                case 3:
                    System.out.print("Enter Order ID: ");
                    String deleteOrderId = scanner.nextLine();
                    String deleteResult = deleteOrder(deleteOrderId);
                    System.out.println(deleteResult);
                    break;
                case 4:
                    System.out.print("Enter Order ID: ");
                    String updateOrderId = scanner.nextLine();
                    Map<String, String> updatedDetails = new HashMap<>();
                    System.out.print("Enter new Product ID (or press Enter to keep current): ");
                    String newProductId = scanner.nextLine();
                    if (!newProductId.isEmpty()) {
                        updatedDetails.put("Product ID", newProductId);
                    }
                    System.out.print("Enter new Quantity (or press Enter to keep current): ");
                    String newQuantityStr = scanner.nextLine();
                    if (!newQuantityStr.isEmpty()) {
                        updatedDetails.put("Quantity", newQuantityStr);
                    }
                    String updateResult = updateOrder(updateOrderId, updatedDetails);
                    System.out.println(updateResult);
                    break;
                case 5:
                    System.out.print("Enter Discount Name: ");
                    String discountName = scanner.nextLine();
                    String applyDiscountResult = applyDiscount(discountName);
                    System.out.println(applyDiscountResult);
                    break;
                case 6:
                    System.out.print("Enter Discount Name: ");
                    String discountNameAdd = scanner.nextLine();
                    System.out.print("Enter Discount Rate: ");
                    double discountRate = scanner.nextDouble();
                    scanner.nextLine();
                    String addDiscountResult = addDiscount(discountNameAdd, discountRate);
                    System.out.println(addDiscountResult);
                    break;
                case 7:
                    System.out.print("Enter Order ID: ");
                    String statusOrderId = scanner.nextLine();
                    System.out.print("Enter New Status: ");
                    String newStatus = scanner.nextLine();
                    updateOrderStatus(statusOrderId, newStatus);
                    break;
                case 8:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
