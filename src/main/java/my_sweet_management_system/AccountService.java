package my_sweet_management_system;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private Map<String, UserAccount> accounts = new HashMap<>();
    private static final String ACCOUNT_FILE = "accounts.txt";

    public AccountService() {
        loadAccounts();
    }

    public void addAccount(String name, String email) {
        accounts.put(name, new UserAccount(name, email));
        saveAccounts();
    }

    public UserAccount getAccountDetail(String name) {
        return accounts.get(name); // This might return null if the account doesn't exist
    }

    // Add the accountExists method here
    public boolean accountExists(String name) {
        return accounts.containsKey(name);
    }

    public String updateAccount(String name, String field, String value) {
        UserAccount account = accounts.get(name);
        if (account == null) {
            return "Account not found";
        }
        switch (field) {
            case "email":
                account.setEmail(value);
                break;
            case "phone":
                account.setPhone(value);
                break;
            default:
                return "Field does not exist";
        }
        saveAccounts();
        return "Account details updated successfully";
    }

    private void loadAccounts() {
        File file = new File(ACCOUNT_FILE);
        if (!file.exists()) {
            System.out.println("No accounts file found. Creating a new file.");
            saveAccounts();  // Save the empty map to create the file
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 2) {
                    accounts.put(parts[0], new UserAccount(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveAccounts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNT_FILE))) {
            for (Map.Entry<String, UserAccount> entry : accounts.entrySet()) {
                UserAccount account = entry.getValue();
                writer.write(entry.getKey() + ", " + account.getEmail() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
