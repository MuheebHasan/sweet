package my_sweet_management_system;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountService {
    private static final Logger logger = Logger.getLogger(AccountService.class.getName());
    private Map<String, UserAccount> accounts = new HashMap<>();
    private static final String ACCOUNT_FILE = "accounts.txt";

    public AccountService() {
        loadAccounts();
    }

    public void addAccount(String name, String email) {
        accounts.put(name, new UserAccount(name, email));
        saveAccounts();
        logger.info("Added account: " + name);
    }

    public UserAccount getAccountDetail(String name) {
        UserAccount account = accounts.get(name);
        if (account == null) {
            logger.warning("Account not found: " + name);
        }
        return account;
    }

    public boolean accountExists(String name) {
        boolean exists = accounts.containsKey(name);
        if (!exists) {
            logger.warning("Account does not exist: " + name);
        }
        return exists;
    }

    public String updateAccount(String name, String field, String value) {
        UserAccount account = accounts.get(name);
        if (account == null) {
            logger.warning("Account not found for update: " + name);
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
                logger.warning("Invalid field for update: " + field);
                return "Field does not exist";
        }
        saveAccounts();
        logger.info("Updated account: " + name + ", Field: " + field);
        return "Account details updated successfully";
    }

    private void loadAccounts() {
        File file = new File(ACCOUNT_FILE);
        if (!file.exists()) {
            logger.info("No accounts file found. Creating a new file.");
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
            logger.log(Level.SEVERE, "Error loading accounts", e);
        }
    }

    private void saveAccounts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNT_FILE))) {
            for (Map.Entry<String, UserAccount> entry : accounts.entrySet()) {
                UserAccount account = entry.getValue();
                writer.write(entry.getKey() + ", " + account.getEmail() + "\n");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving accounts", e);
        }
    }
}
