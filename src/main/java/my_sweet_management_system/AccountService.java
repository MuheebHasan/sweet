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

    // Constructor that loads accounts from file if available
    public AccountService() {
        loadAccounts();
    }

    // Adds a new account only if it does not already exist
    public void addAccount(String name, String email) {
        if (!accounts.containsKey(name)) {
            accounts.put(name, new UserAccount(name, email));
            saveAccountsIfNeeded(); // Save the new account to the file if necessary
            logger.info(String.format("Added account: %s", name));
        } else {
            logger.warning(String.format("Account already exists: %s", name));
        }
    }

    // Retrieves account details; logs a warning if the account is not found
    public UserAccount getAccountDetail(String name) {
        UserAccount account = accounts.get(name);
        if (account == null) {
            logger.warning(String.format("Account not found: %s", name));
        }
        return account;
    }

    // Checks if an account exists and logs a warning if it does not
    public boolean accountExists(String name) {
        boolean exists = accounts.containsKey(name);
        if (!exists) {
            logger.warning(String.format("Account does not exist: %s", name));
        }
        return exists;
    }

    // Updates account details only if the account exists and the field is valid
    public String updateAccount(String name, String field, String value) {
        UserAccount account = accounts.get(name);
        if (account == null) {
            logger.warning(String.format("Account not found for update: %s", name));
            return "Account not found";
        }

        boolean updated = false;
        switch (field.toLowerCase()) {
            case "email":
                account.setEmail(value);
                updated = true;
                break;
            case "phone":
                account.setPhone(value);
                updated = true;
                break;
            default:
                logger.warning(String.format("Invalid field for update: %s", field));
                return "Field does not exist";
        }

        // Save the account details only if there was an update
        if (updated) {
            saveAccountsIfNeeded();
            logger.info(String.format("Updated account: %s, Field: %s", name, field));
            return "Account details updated successfully";
        } else {
            return "No changes made";
        }
    }

    // Loads accounts from the file; creates a new file if it does not exist
    private void loadAccounts() {
        File file = new File(ACCOUNT_FILE);
        if (!file.exists()) {
            logger.info("No accounts file found. Creating a new file.");
            saveAccountsIfNeeded(); // Save an empty map to create the file if needed
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 2) {
                    accounts.put(parts[0], new UserAccount(parts[0], parts[1]));
                } else {
                    logger.warning("Malformed line in accounts file: " + line);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading accounts", e);
        }
    }

    // Saves account details to the file if there are changes
    private void saveAccountsIfNeeded() {
        // Determine if accounts have been modified and need saving
        boolean needsSaving = !accounts.isEmpty();
        if (needsSaving) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNT_FILE))) {
                for (Map.Entry<String, UserAccount> entry : accounts.entrySet()) {
                    UserAccount account = entry.getValue();
                    writer.write(String.format("%s, %s%n", entry.getKey(), account.getEmail()));
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error saving accounts", e);
            }
        }
    }

    // Inner class for UserAccount
    public static class UserAccount {
        private String name;
        private String email;
        private String phone;

        public UserAccount(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getPhone() { return phone; }

        public void setEmail(String email) { this.email = email; }
        public void setPhone(String phone) { this.phone = phone; }
    }
}
