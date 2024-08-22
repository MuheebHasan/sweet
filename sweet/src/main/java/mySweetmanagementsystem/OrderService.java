package mySweetmanagementsystem;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class OrderService {
    private static final String ORDER_FILE = "orders.txt";
    private Map<String, String> orders = new HashMap<>();

    public OrderService() {
        loadOrders();
    }

    public String updateOrderStatus(String orderId, String status) {
        orders.put(orderId, status);
        saveOrders();
        return "Order status updated to " + status;
    }

    public String getOrderStatus(String orderId) {
        return orders.get(orderId);
    }

    private void loadOrders() {
        File file = new File(ORDER_FILE);
        if (!file.exists()) {
            System.out.println("No orders file found. Creating a new file.");
            saveOrders();  // Create an empty file
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ", 2);
                if (parts.length == 2) {
                    orders.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveOrders() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDER_FILE))) {
            for (Map.Entry<String, String> entry : orders.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
