package my_sweet_management_system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class SupplierService {

    private final Set<String> owners;
    private final String loggedInUser;

    public SupplierService(String loggedInUser) {
        this.loggedInUser = loggedInUser;
        owners = new HashSet<>();
        loadOwnersFromFile("logsign.txt");
    }

    private void loadOwnersFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && "Store Owner".equals(parts[3])) {
                    owners.add(parts[0].trim()); // Ensure there are no leading/trailing spaces
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public String sendMessage(String ownerName, String message) {
        if (!owners.contains(ownerName.trim())) {
            return "Owner not found.";
        }
        storeMessage(loggedInUser, ownerName.trim(), message);
        return "Message sent successfully.";
    }

    private void storeMessage(String supplierName, String ownerName, String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("messages.txt", true))) {
            writer.write(supplierName + ";" + ownerName + ";" + message);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    

    public List<String> loadMessagesForOwner(String ownerName) {
        List<String> messages = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("messages.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String senderName = parts[0].trim();
                    String recipientName = parts[1].trim();
                    String message = parts[2].trim();
                    if (recipientName.equalsIgnoreCase(ownerName)) {
                        messages.add("From: " + senderName + " - Message: " + message);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;
    }


    public String getStoreOwnerNameFromEmail(String email) {
        String ownerName = "";
        try (BufferedReader br = new BufferedReader(new FileReader("logsign.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && email.equals(parts[2].trim()) && "Store Owner".equals(parts[3])) {
                    ownerName = parts[0].trim(); // Extract the owner's name
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ownerName;
    }




    public void appendReplyToMessageFile(String ownerName, String message, String reply) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("messages.txt", true))) {
            writer.write(loggedInUser + ";" + ownerName + ";" + reply);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
