package my_sweet_management_system;

import java.util.Scanner;
import java.util.Set;

 
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
 
public class MainMenu {
     private static final String INVALID_CHOICE_MESSAGE = "Invalid choice. Please try again.";
    private static final String ENTER_YOUR_CHOICE= "Enter your choice: ";
    private static final String BACK_TO_USER_MENUE= "3. Back to User Menu";

      private static Test userAuth = new Test();
    private static UserManagementSystem system = new UserManagementSystem(); // Assuming you have this class for managing users
    private static MonitoringAndReportingww reporting = new MonitoringAndReportingww(); // Assuming you have this class for reporting
    private static Product productSystem = new Product(); // Assuming you have this class for managing products
    private static PersonAccount Post = new PersonAccount();
    static PersonAccount account = new PersonAccount();
    private static ProductSearchtest pp ;
    
    private static void logInfo(String message) {
        System.out.println(message);
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
        	 logInfo("Welcome to Sweet Management System");
             logInfo("1. Login");
             logInfo("2. Signup");
             logInfo("3. Exit");
             logInfo("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    handleLogin(scanner);
                    break;

                case 2:
                    handleSignup(scanner);
                    break;

                case 3:
                    running = false;
                    logInfo("Exiting the system. Goodbye!");                    break;

                default:
                    logInfo(INVALID_CHOICE_MESSAGE);
            }
        }

        scanner.close();
    }
    
    private static String currentLoggedInUser = null;


    
    private static void handleLogin(Scanner scanner) {
        logInfo("Enter your email: ");
        String email = scanner.nextLine();
        logInfo("Enter your password: ");
        String password = scanner.nextLine();

        // Check login using the MonitoringAndReportingww class
        if (reporting.login(email, password)) {
            logInfo("Login successful!");

            // Set the current logged-in user
            currentLoggedInUser = email;

            // Show the appropriate menu based on the role
            String role = reporting.getRoleByEmail(email);
            switch (role) {
                case "Admin":
                    handleAdminMenu(scanner);
                    break;
                case "Store Owner":
                    handleStoreOwnerMenu(scanner);
                    break;
                case "User":
                    handleUserMenu(scanner);
                    break;
                case "Supplier":
                    handleSupplierMenu(scanner, currentLoggedInUser);
                    break;
                default:
                    logInfo("Welcome, " + role + "!"); // For unexpected roles
                    break;
            }
        } else {
            logInfo("Invalid email or password.");
        }
    }




    private static void handleSignup(Scanner scanner) {
        logInfo("Select your role:");
        logInfo("1. Admin");
        logInfo("2. Store Owner");
        logInfo("3. Supplier");
        logInfo("4. User");
        logInfo("Enter the number corresponding to your role: ");
        int roleChoice = scanner.nextInt();
        scanner.nextLine();

        String role;
        switch (roleChoice) {
            case 1:
                role = "Admin";
                break;
            case 2:
                role = "Store Owner";
                break;
            case 3:
                role = "Supplier";
                break;
            case 4:
                role = "User";
                break;
            default:
                logInfo("Invalid choice. Role set to 'User' by default.");
                role = "User";
        }

        // Input user details after selecting role
        logInfo("Enter your username: ");
        String username = scanner.nextLine();
        logInfo("Enter your password: ");
        String password = scanner.nextLine();
        logInfo("Enter your email: ");
        String email = scanner.nextLine();

        if (userAuth.registerUser(username, password, email, role)) {
            logInfo("Signup successful!");
        } else {
            logInfo("Signup failed. Please check your details and try again.");
        }
    }
    
    private static void handleSupplierMenu(Scanner scanner, String loggedInUser) {
        // Example Supplier Menu implementation
        while (true) {
        	logInfo("\n** Supplier Menu **");
            logInfo("1. Messaging system to communicate");
            logInfo("2. Logout");
            logInfo(ENTER_YOUR_CHOICE);
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    handleMessaging(scanner, loggedInUser);
                    break;
                case 2:
                	  logInfo("Logging out...");
                      return;
                  default:
                      logInfo(INVALID_CHOICE_MESSAGE);
            }
        }
    }
    private static void handleAdminMenu(Scanner scanner) {
        while (true) {
            logInfo("\n** Admin Menu **");
            logInfo("1. User Management System");
            logInfo("2. View Statistics");
            logInfo("3. Manage Recipes");
            logInfo("4. Manage Posts");
            logInfo("5. Manage Feedback");
            logInfo("6. Logout");
            logInfo(ENTER_YOUR_CHOICE);
            int adminChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (adminChoice) {
                case 1:
                    handleUserManagement(scanner);
                    break;

                case 2:
                    reporting.displayStatistics();
                    reporting.displayProductSales();
                    break;
                case 3:
                    handleManageRecipes(scanner);
                    break;
                case 4:
                    handleManagePosts(scanner);
                    break;
                case 5:
                    handleManageFeedback(scanner);
                    break;

                case 6:
                    logInfo("Logging out...");
                    return;

                default:
                    logInfo(INVALID_CHOICE_MESSAGE);
            }
        }
    }

    private static void handleStoreOwnerMenu(Scanner scanner) {
        while (true) {
        	logInfo("\n** Store Owner Menu **");
            logInfo("1. Manage Products");
            logInfo("2. Best-selling products and profits");
            logInfo("3. Manage Discounts");
            logInfo("4. Manage Orders");
            logInfo("5. Communication and Notification");
            logInfo("6. Logout");
            logInfo(ENTER_YOUR_CHOICE);
            int ownerChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (ownerChoice) {
                case 1:
                    handleProductManagement(scanner);
                    break;

                case 2:
                    reporting.displayStatistics();
                    reporting.displayProductSales();
                    break;

                case 3:
                    handleDiscountManagement(scanner);
                    break;

                case 4:
                    handleOrderManagement(scanner);
                    break;

                case 5:
                    // Ensure currentLoggedInUser is properly set to the logged-in user's email
                    handleCommunicationAndNotification(scanner, currentLoggedInUser);
                    break;


                case 6:
                    logInfo("Logging out...");
                    return;

                default:
                    logInfo(INVALID_CHOICE_MESSAGE);
            }
        }
    }

    private static void handleUserManagement(Scanner scanner) {
        while (true) {
        	logInfo("\n** User Management System **");
            logInfo("1. Add User");
            logInfo("2. Update User");
            logInfo("3. Delete User");
             logInfo("4. Back to Admin Menu");
            logInfo(ENTER_YOUR_CHOICE);
            int userMgmtChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (userMgmtChoice) {
                case 1:
                	  logInfo("Enter User ID: ");
                      String id = scanner.nextLine();
                      logInfo("Enter User Type (User/Owner/Supplier): ");
                      String type = scanner.nextLine().toLowerCase();
                      logInfo("Enter User Name: ");
                      String name = scanner.nextLine();
                      logInfo("Enter User Address: ");
                      String address = scanner.nextLine();
                      logInfo("Enter User Phone: ");
                    String phone = scanner.nextLine();
                    String addResult = system.addUser(id, type, name, address, phone);
                    System.out.println(addResult);
                    break;

                case 2:
                	   logInfo("Enter User ID to update: ");
                       String updateId = scanner.nextLine();
                       system.setCurrentId(updateId);
                       logInfo("Enter field to update (Name/Address/Phone): ");
                       String field = scanner.nextLine();
                       logInfo("Enter new value: ");
                       String value = scanner.nextLine();
                       String updateResult = system.updateUser(field, value);
                       logInfo(updateResult);
                       break;
                case 3:
                	  logInfo("Enter User ID to delete: ");
                      String deleteId = scanner.nextLine();
                      system.setCurrentId(deleteId);
                      String deleteResult = system.deleteUser();
                      logInfo(deleteResult);
                      break;

                case 4:
                    logInfo("Returning to Admin Menu...");
                    return;

                default:
                    logInfo(INVALID_CHOICE_MESSAGE);
            }
        }
    }

    private static void handleManageRecipes(Scanner scanner) {
        while (true) {
            logInfo("\n--- Manage Recipes ---");
            logInfo("1. Add Recipe");
            logInfo("2. Update Recipe");
            logInfo("3. Delete Recipe");
            logInfo("4. View Recipe");
            logInfo("5. Back to Menu Admin ");
            logInfo(ENTER_YOUR_CHOICE);
            int recipeChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (recipeChoice) {
                case 1:
                    logInfo("Enter Recipe Name: ");
                    String recipeName = scanner.nextLine();
                    logInfo("Enter Recipe Description: ");
                    String recipeDesc = scanner.nextLine();
                    String resultAdd = account.addRecipe(recipeName, recipeDesc, ""); // assuming you have a third parameter
                    logInfo(resultAdd);
                    break;

                case 2:
                    logInfo("Enter Recipe Name to update: ");
                    String updateRecipeName = scanner.nextLine();
                    logInfo("Enter new Recipe Description: ");
                    String updateRecipeDesc = scanner.nextLine();
                    String resultUpdate = account.updateRecipe(updateRecipeName, updateRecipeDesc); // Only two parameters
                    logInfo(resultUpdate);
                    break;

                case 3:
                    logInfo("Enter Recipe Name to delete: ");
                    String deleteRecipeName = scanner.nextLine();
                    String resultDelete = account.deleteRecipe(deleteRecipeName);
                    logInfo(resultDelete);
                    break;

                case 4:
                    logInfo("Enter Recipe Name to view: ");
                    String viewRecipeName = scanner.nextLine();
                    String recipeDetails = account.viewRecipe(viewRecipeName);
                    logInfo(recipeDetails);
                    break;

                case 5:
                    logInfo("Returning to Admin Menu...");
                    return;

                default:
                    logInfo(INVALID_CHOICE_MESSAGE);
            }
        }
    }

    private static void handleManagePosts(Scanner scanner) {
        while (true) {
            logInfo("\n--- Manage Posts ---");
            logInfo("1. Add Post");
            logInfo("2. Update Post");
            logInfo("3. Delete Post");
            logInfo("4. View All Posts");
            logInfo("5. Back to Admin Menu");
            logInfo(ENTER_YOUR_CHOICE);
            int postChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (postChoice) {
                case 1:
                    logInfo("Enter Post Title: ");
                    String title = scanner.nextLine();
                    logInfo("Enter Post Content: ");
                    String content = scanner.nextLine();
                    String addResult = Post.addPost(title, content);
                    logInfo(addResult);
                    break;

                case 2:
                    logInfo("Enter Post Title to update: ");
                    String updateTitle = scanner.nextLine();
                    logInfo("Enter New Content: ");
                    String newContent = scanner.nextLine();
                    String updateResult = Post.updatePost(updateTitle, newContent);
                    logInfo(updateResult);
                    break;

                case 3:
                    logInfo("Enter Post Title to delete: ");
                    String deleteTitle = scanner.nextLine();
                    String deleteResult = Post.deletePost(deleteTitle);
                    logInfo(deleteResult);
                    break;

                case 4:
                    String allPosts = Post.viewAllPosts();
                    logInfo(allPosts);
                    break;

                case 5:
                    logInfo("Returning to Admin Menu...");
                    return;

                default:
                    logInfo(INVALID_CHOICE_MESSAGE);
            }
        }
    }

    private static void handleManageFeedback(Scanner scanner) {
        while (true) {
            logInfo("\n--- Manage Feedback ---");
            logInfo("1. View Feedback");
            logInfo("2. Respond to Feedback");
            logInfo("3. Delete Feedback");
            logInfo("4. Back to Admin Menu");
            logInfo(ENTER_YOUR_CHOICE);
            int feedbackChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (feedbackChoice) {
                case 1:
                    logInfo(account.viewFeedback()); // استدعاء الدالة من personaccount
                    break;
                case 2:
                    logInfo("Enter feedback ID to respond to: ");
                    int feedbackId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    logInfo("Enter your response: ");
                    String response = scanner.nextLine();
                    logInfo(account.respondToFeedback(feedbackId, response)); // استدعاء الدالة من personaccount
                    break;
                case 3:
                    logInfo("Enter feedback ID to delete: ");
                    int deleteFeedbackId = scanner.nextInt();
                    logInfo(account.deleteFeedback(deleteFeedbackId)); // استدعاء الدالة من personaccount
                    break;

                case 4:
                    logInfo("Returning to Admin Menu...");
                    return;

                default:
                    logInfo(INVALID_CHOICE_MESSAGE);
            }
        }
    }

    private static void handleProductManagement(Scanner scanner) {
        while (true) {
            logInfo("\n--- Manage Products ---");
            logInfo("1. Add Product");
            logInfo("2. Update Product");
            logInfo("3. Delete Product");
            logInfo("4. Exit");
            logInfo(ENTER_YOUR_CHOICE);
            int productChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (productChoice) {
                case 1:
                    logInfo("Enter Product ID: ");
                    String productId = scanner.nextLine();
                    logInfo("Enter Product Name: ");
                    String productName = scanner.nextLine();
                    logInfo("Enter Description: ");
                    String description = scanner.nextLine();
                    logInfo("Enter Price: ");
                    String price = scanner.nextLine();
                    logInfo("Enter Availability: ");
                    String availability = scanner.nextLine();
                    String addResult = productSystem.addProduct(productId, productName, description, price, availability);
                    logInfo(addResult);
                    break;

                case 2:
                    logInfo("Enter Product ID to update: ");
                    String updateId = scanner.nextLine();
                    if (productSystem.productExists(updateId)) {
                        logInfo("Enter field to update (Product Name/Description/Price/Availability): ");
                        String field = scanner.nextLine();
                        logInfo("Enter new value: ");
                        String value = scanner.nextLine();
                        Map<String, String> updatedDetails = new HashMap<>();
                        updatedDetails.put(field, value);
                        String updateResult = productSystem.updateProduct(updateId, updatedDetails);
                        logInfo(updateResult);
                    } else {
                        logInfo("Product ID does not exist.");
                    }
                    break;

                case 3:
                    logInfo("Enter Product ID to delete: ");
                    String deleteProdId = scanner.nextLine();
                    productSystem.deleteProduct(deleteProdId);
                    break;

                case 4:
                    logInfo("Exiting Product Management.");
                    return; // Exit the product management menu loop

                default:
                    logInfo(INVALID_CHOICE_MESSAGE);
            }
        }
    }

    private static void handleDiscountManagement(Scanner scanner) {
        while (true) {
            logInfo("\n--- Manage Discounts and Orders ---");
            logInfo("1. Add Discount");
            logInfo("2. Apply Discount");
            logInfo("3. Print Orders");
            logInfo("4. Exit");
            logInfo(ENTER_YOUR_CHOICE);
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    logInfo("Enter Discount Name: ");
                    String discountName = scanner.nextLine();
                    logInfo("Enter Discount Rate (in percentage): ");
                    double discountRate = Double.parseDouble(scanner.nextLine());
                    logInfo(ooorder.addDiscount(discountName, discountRate));
                    break;

                case 2:
                    logInfo("Enter Discount Name to Apply: ");
                    String applyDiscountName = scanner.nextLine();
                    logInfo(ooorder.applyDiscount(applyDiscountName));
                    break;

                case 3:
                    ooorder.printOrders("Order List:", ooorder.orders);
                    break;

                case 4:
                    logInfo("Exiting Discount Management.");
                    return; // Exit the discount management menu loop

                default:
                    logInfo("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void handleOrderManagement(Scanner scanner) {
        while (true) {
            logInfo("\n--- Manage Orders ---");
            logInfo("1. View Order");
            logInfo("2. Update Order");
            logInfo("3. Delete Order");
            logInfo("4. Exit");
            logInfo(ENTER_YOUR_CHOICE);
            int orderChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (orderChoice) {
                case 1:
                    ooorder.printOrders("Order List:", ooorder.orders);
                    break;

                case 2:
                    logInfo("Enter Order ID to update: ");
                    String updateOrderId = scanner.nextLine();
                    if (ooorder.orders.containsKey(updateOrderId)) {
                         String[] orderInfo = ooorder.orders.get(updateOrderId);

                         orderInfo[5] = "Processing"; // assuming index 5 is the status

                         ooorder.orders.put(updateOrderId, orderInfo);

                         ooorder.saveOrders();

                        logInfo("Order status updated to Processing.");
                    } else {
                        logInfo("Order ID not found.");
                    }
                    break;

                case 3:
                    logInfo("Enter Order ID to delete: ");
                    String deleteOrderId = scanner.nextLine();
                    String result = ooorder.deleteOrder(deleteOrderId);
                    logInfo(result);
                    break;

                case 4:
                    logInfo("Exiting Order Management.");
                    return;  

                default:
                    logInfo(INVALID_CHOICE_MESSAGE);
            }
        }
    }

    private static void handleCommunicationAndNotification(Scanner scanner, String currentLoggedInUser) {
        SupplierService supplierService = new SupplierService(currentLoggedInUser);

        String currentOwner = supplierService.getStoreOwnerNameFromEmail(currentLoggedInUser);

        if (currentOwner.isEmpty()) {
            logInfo("No messages to reply to.");
            return;
        }

        List<String> messagesForOwner = supplierService.loadMessagesForOwner(currentOwner);

        if (messagesForOwner.isEmpty()) {
            logInfo("No messages to reply to.");
            return;
        }

        while (true) {
            logInfo("\n** Communication and Notification **");
            logInfo("1. Reply to Message");
            logInfo("2. Back to Store Owner Menu");
            logInfo(ENTER_YOUR_CHOICE);
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                     logInfo("Messages for you:");
                    for (int i = 0; i < messagesForOwner.size(); i++) {
                        logInfo((i + 1) + ". " + messagesForOwner.get(i));
                    }

                    // Get the message to reply to
                    logInfo("Enter the number of the message you want to reply to: ");
                    int messageNumber = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (messageNumber < 1 || messageNumber > messagesForOwner.size()) {
                        logInfo("Invalid message number.");
                        break;
                    }

                    String selectedMessage = messagesForOwner.get(messageNumber - 1);
                    logInfo("Selected message: " + selectedMessage);

                     logInfo("Enter your reply: ");
                    String reply = scanner.nextLine();

                     supplierService.appendReplyToMessageFile(currentOwner, selectedMessage, reply);

                    logInfo("Reply sent successfully.");
                    break;

                case 2:
                    logInfo("Returning to Store Owner Menu...");
                    return; // Exit Communication and Notification Menu and return to the Store Owner Menu

                default:
                    logInfo(INVALID_CHOICE_MESSAGE);
            }
        }
    }




    private static void handleUserMenu(Scanner scanner) {
        while (true) {
            logInfo("\n** User Menu **");
            logInfo("1. Product Browsing Menu");
            logInfo("2. Update Account Details");
            logInfo("3. Make purchases ");
            logInfo("4. search for dessert recipes");
            logInfo("5. Provide feedback");
            logInfo("6. Logout");
            logInfo(ENTER_YOUR_CHOICE);
            int userChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (userChoice) {
                case 1:
                    handleBrowseproducts(scanner);
                    break;
                case 2:
                    handleManageUserAccount(scanner, reporting); // Pass the correct instance here
                    break;
                case 3:
                    handleMakepurchases(scanner);
                    break;
                case 4:
                    handleSearchForDessertRecipes(scanner);
                    break;
                case 5:
                    handleProvidefeedback(scanner);
                    break;
                case 6:
                    logInfo("Logging out...");
                    return;
                default:
                    logInfo(INVALID_CHOICE_MESSAGE);
            }
        }
    }

    public static void handleBrowseproducts(Scanner scanner) {
        while (true) {
            logInfo("\n--- Welcome to the Product Browsing Menu ---");
            logInfo("1. Show all products");
            logInfo("2. Search for a product by ID");
            logInfo(BACK_TO_USER_MENUE);
            logInfo(ENTER_YOUR_CHOICE);
            int accountChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (accountChoice) {
                case 1:
                    ProductSearchtest.showAllProducts();
                    break;
                case 2:
                    logInfo("Enter the Product ID to search: ");
                    String productId = scanner.nextLine();
                    pp.searchProductById(productId);
                    break;
                case 3:
                    logInfo("Returning to User Menu...");
                    return;
                default:
                    logInfo(INVALID_CHOICE_MESSAGE);
            }
        }
    }

    public static void handleMakepurchases(Scanner scanner) {
        while (true) {
            logInfo("\n--- Welcome to the Purchase Menu ---");
            logInfo("1. Add Order");
            logInfo("2. Cancel Order");
            logInfo(BACK_TO_USER_MENUE);

            int accountChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (accountChoice) {
                case 1:
                    logInfo("Great choice! Let's proceed to add your order.");
                    logInfo("Enter Product ID: ");
                    String productId = scanner.nextLine();
                    logInfo("Enter Quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    String addOrderResult = ooorder.addOrder(productId, quantity);
                    logInfo(addOrderResult);
                    break;
                case 2:
                    logInfo("Need to cancel an order? We’ll help you with that.");
                    logInfo("Enter Order ID to cancel: ");
                    String deleteOrderId = scanner.nextLine();
                    String result = ooorder.deleteOrder(deleteOrderId);
                    logInfo(result);
                    break;
                case 3:
                    logInfo("Returning to the User Menu. Have a great day!");
                    return;
                default:
                    logInfo("Oops! It looks like you've entered an invalid option. Please choose a number between 1 and 4.");
            }
        }
    }

    public static void handleSearchForDessertRecipes(Scanner scanner) {
        while (true) {
            logInfo("\n--- Recipe Filter Menu ---");
            logInfo("1. Search for a Recipe by Name");
            logInfo("2. Filter Recipes by Allergies");
            logInfo("3. Filter Recipes by Nutritional Value");
            logInfo("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    logInfo("Enter the name of the recipe to search for: ");
                    String name = scanner.nextLine();
                    account.searchRecipeByName(name);
                    break;
                case 2:
                    logInfo("\nRecipes containing dairy:");
                    account.filterRecipesByAllergies("Contains dairy");
                    break;
                case 3:
                    logInfo("Recipes matching all nutritional values:");
                    Set<String> criteria = new HashSet<>();
                    criteria.add("Contains nuts");
                    criteria.add("Contains dairy");
                    account.filterRecipesByNutritionalValue(criteria);
                    break;
                case 4:
                    logInfo("Exiting...");
                    return;
                default:
                    logInfo("Invalid choice, please try again.");
            }
        }
    }

    private static void handleManageUserAccount(Scanner scanner, MonitoringAndReportingww manager) {
        if (manager.getLoggedInUsername() == null) {
            logInfo("Error: No user is currently logged in.");
            return;
        }

        while (true) {
            logInfo("\n--- Manage Account ---");
            logInfo("1. Change Password");
            logInfo("2. Change Email");
            logInfo(BACK_TO_USER_MENUE);
            logInfo(ENTER_YOUR_CHOICE);
            int accountChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (accountChoice) {
                case 1:
                    logInfo("Enter your new password: ");
                    String newPassword = scanner.nextLine();
                    manager.updateLogsignFile(null, newPassword);
                    logInfo("Password updated successfully.");
                    break;
                case 2:
                    logInfo("Enter your new email: ");
                    String newEmail = scanner.nextLine();
                    manager.updateLogsignFile(newEmail, null);
                    logInfo("Email updated successfully.");
                    break;
                case 3:
                    logInfo("Returning to User Menu...");
                    return;
                default:
                    logInfo(INVALID_CHOICE_MESSAGE);
            }
        }
    }

    private static void handleProvidefeedback(Scanner scanner) {
        logInfo("We’d love to hear your thoughts! Please choose an option:");
        logInfo("1. Add Feedback");
        logInfo("2. Back to User Menu");

        int feedbackChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (feedbackChoice) {
            case 1:
                reporting.addFeedback(scanner);
                break;
            case 2:
                logInfo("Returning to the User Menu. We hope you find what you're looking for!");
                return; // Exit the feedback menu and return to the purchase menu
            default:
                logInfo("Oops! It seems you entered an invalid choice. Please try again.");
        }
    }

    private static void handleMessaging(Scanner scanner, String loggedInUser) {
        // Create or get the SupplierService instance with the logged-in user
        SupplierService supplierService = new SupplierService(loggedInUser);

        while (true) {
            logInfo("\n** Messaging System **");
            logInfo("1. Send a message to a store owner");
            logInfo("2. Back to Supplier Menu");
            logInfo(ENTER_YOUR_CHOICE);
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Get the owner's name and the message from the supplier
                    logInfo("Enter the store owner's name: ");
                    String ownerName = scanner.nextLine();
                    logInfo("Enter your message: ");
                    String message = scanner.nextLine();

                    // Send the message
                    String result = supplierService.sendMessage(ownerName, message);
                    logInfo(result);
                    break;
                case 2:
                    logInfo("Returning to Supplier Menu...");
                    return;
                default:
                    logInfo(INVALID_CHOICE_MESSAGE);
            }
        }
    }}
