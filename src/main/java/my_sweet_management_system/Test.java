package my_sweet_management_system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Test {
	private static final String FILE_PATH = "logsign.txt";

    public boolean loginClicked(String email, String passw) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] user = line.split(",");
                if (user[2].equals(email) && user[1].equals(passw)) {
                    return true; // Email and password match
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions properly
        }
        return false; // Return false if no matching user is found
    }

    public static boolean isValidEmail(String email) {
        return email.contains("@") && email.length() >= 11;
    }

    public static boolean isValidName(String name) {
        return name != null && name.trim().length() >= 2;
    }

    public static boolean isValidPassword(String password) {
        boolean hasLowerCase = false;
        boolean hasUpperCase = false;
        boolean hasDigit = false;
        if (password.length() < 4) return false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) hasLowerCase = true;
            if (Character.isUpperCase(password.charAt(i))) hasUpperCase = true;
            if (Character.isDigit(password.charAt(i))) hasDigit = true;
        }
        return hasLowerCase && hasUpperCase && hasDigit;
    }

    public static boolean isValidId(String id) {
        // In this case, ID validation might not be necessary since we're not using a database
        return id.length() > 2;
    }

    public static boolean isValidRole(String role) {
        return role.equalsIgnoreCase("Admin") || 
               role.equalsIgnoreCase("Store Owner") || role.equalsIgnoreCase("Supplier") ||
               role.equalsIgnoreCase("User");
    }

    private static boolean isEmailAlreadyRegistered(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] user = line.split(",");
                if (user[2].equals(email)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean registerUser(String username, String password, String email, String role) {
        if (!isValidEmail(email)) {
             System.out.println("email failed");
            return false; // Invalid email format
        }
        if (!isValidName(username)) {
             System.out.println("username failed");

            return false; // Invalid name
        }
        if (!isValidPassword(password)) {
             System.out.println("password failed");

            return false; // Invalid password
        }
        if (!isValidRole(role)) {
             System.out.println("role failed");

            return false;
        }
        if (isEmailAlreadyRegistered(email)) {
             System.out.println("email already failed");

            return false; // Email already registered
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(username + "," + password + "," + email + "," + role);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
 // دالة لجلب دور المستخدم باستخدام البريد الإلكتروني
    public String getRoleByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] user = line.split(",");
                if (user[2].equals(email)) {
                    return user[3]; // إرجاع الدور (role) الخاص بالمستخدم
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // معالجة الاستثناءات
        }
        return null; // في حال لم يتم العثور على المستخدم
    }
}