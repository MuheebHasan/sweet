package my_sweet_management_system;

import java.util.Scanner;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class MainMenu {
  
     private static ooorder orderManager = new ooorder();
     private static Test userAuth = new Test();
    private static UserManagementSystem system = new UserManagementSystem(); // Assuming you have this class for managing users
    private static MonitoringAndReportingww reporting = new MonitoringAndReportingww(); // Assuming you have this class for reporting
    private static Product productSystem = new Product(); // Assuming you have this class for managing products
    private static PersonAccount Post = new PersonAccount();
    static PersonAccount account = new PersonAccount();
    private static ProductSearchtest pp ;
    
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to Sweet Management System");
            System.out.println("1. Login");
            System.out.println("2. Signup");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
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
                    System.out.println("Exiting the system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
    
    private static String currentLoggedInUser = null;


    
    private static void handleLogin(Scanner scanner) {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Check login using the MonitoringAndReportingww class
        if (reporting.login(email, password)) {
            System.out.println("Login successful!");

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
                    System.out.println("Welcome, " + role + "!"); // For unexpected roles
                    break;
            }
        } else {
            System.out.println("Invalid email or password.");
        }
    }




    private static void handleSignup(Scanner scanner) {
        System.out.println("Select your role:");
        System.out.println("1. Admin");
        System.out.println("2. Store Owner");
        System.out.println("3. Supplier");
        System.out.println("4. User");
        System.out.print("Enter the number corresponding to your role: ");
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
                System.out.println("Invalid choice. Role set to 'User' by default.");
                role = "User";
        }

        // Input user details after selecting role
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        if (userAuth.registerUser(username, password, email, role)) {
            System.out.println("Signup successful!");
        } else {
            System.out.println("Signup failed. Please check your details and try again.");
        }
    }
    
    private static void handleSupplierMenu(Scanner scanner, String loggedInUser) {
        // Example Supplier Menu implementation
        while (true) {
            System.out.println("\n** Supplier Menu **");
            System.out.println("1. Messaging system to communicate");
            System.out.println("2. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    handleMessaging(scanner, loggedInUser);
                    break;
                case 2:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private static void handleAdminMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n** Admin Menu **");
            System.out.println("1. User Management System");
            System.out.println("2. View Statistics");
            System.out.println("3. Manage Recipes");
            System.out.println("4. Manage Posts");
            System.out.println("5. Manage Feedback");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
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
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleStoreOwnerMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n** Store Owner Menu **");
            System.out.println("1. Manage Products");
            System.out.println("2. Best-selling products and profits");
            System.out.println("3. Manage Discounts");
            System.out.println("4. Manage Orders");
            System.out.println("5. Communication and Notification");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
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
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleUserManagement(Scanner scanner) {
        while (true) {
            System.out.println("\n** User Management System **");
            System.out.println("1. Add User");
            System.out.println("2. Update User");
            System.out.println("3. Delete User");
            System.out.println("4. View All Users");
            System.out.println("5. Back to Admin Menu");
            System.out.print("Enter your choice: ");
            int userMgmtChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (userMgmtChoice) {
                case 1:
                    System.out.print("Enter User ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter User Type (User/Owner/Supplier): ");
                    String type = scanner.nextLine().toLowerCase();
                    System.out.print("Enter User Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter User Address: ");
                    String address = scanner.nextLine();
                    System.out.print("Enter User Phone: ");
                    String phone = scanner.nextLine();
                    String addResult = system.addUser(id, type, name, address, phone);
                    System.out.println(addResult);
                    break;

                case 2:
                    System.out.print("Enter User ID to update: ");
                    String updateId = scanner.nextLine();
                    system.setCurrentId(updateId);
                    System.out.print("Enter field to update (Name/Address/Phone): ");
                    String field = scanner.nextLine();
                    System.out.print("Enter new value: ");
                    String value = scanner.nextLine();
                    String updateResult = system.updateUser(field, value);
                    System.out.println(updateResult);
                    break;

                case 3:
                    System.out.print("Enter User ID to delete: ");
                    String deleteId = scanner.nextLine();
                    system.setCurrentId(deleteId);
                    String deleteResult = system.deleteUser();
                    System.out.println(deleteResult);
                    break;

                case 4:
                    // Display all users
                    // String allUsers = system.getAllUsers();
                    // System.out.println(allUsers);
                    break;

                case 5:
                    System.out.println("Returning to Admin Menu...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleManageRecipes(Scanner scanner) {
    	while (true) {
            System.out.println("\n--- Manage Recipes ---");
            System.out.println("1. Add Recipe");
            System.out.println("2. Update Recipe");
            System.out.println("3. Delete Recipe");
            System.out.println("4. View Recipe");
            System.out.println("5. Back to Admin Menu");
            System.out.print("Enter your choice: ");
            int recipeChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (recipeChoice) {
                case 1:
                    System.out.print("Enter Recipe Name: ");
                    String recipeName = scanner.nextLine();
                    System.out.print("Enter Recipe Description: ");
                    String recipeDesc = scanner.nextLine();
                    String resultAdd = account.addRecipe(recipeName, recipeDesc, ""); // assuming you have a third parameter
                    System.out.println(resultAdd);
                    break;

                case 2:
                    System.out.print("Enter Recipe Name to update: ");
                    String updateRecipeName = scanner.nextLine();
                    System.out.print("Enter new Recipe Description: ");
                    String updateRecipeDesc = scanner.nextLine();
                    String resultUpdate = account.updateRecipe(updateRecipeName, updateRecipeDesc); // Only two parameters
                    System.out.println(resultUpdate);
                    break;


                case 3:
                    System.out.print("Enter Recipe Name to delete: ");
                    String deleteRecipeName = scanner.nextLine();
                    String resultDelete = account.deleteRecipe(deleteRecipeName);
                    System.out.println(resultDelete);
                    break;

                case 4:
                    System.out.print("Enter Recipe Name to view: ");
                    String viewRecipeName = scanner.nextLine();
                    String recipeDetails = account.viewRecipe(viewRecipeName);
                    System.out.println(recipeDetails);
                    break;

                case 5:
                    System.out.println("Returning to Admin Menu...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleManagePosts(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Manage Posts ---");
            System.out.println("1. Add Post");
            System.out.println("2. Update Post");
            System.out.println("3. Delete Post");
            System.out.println("4. View All Posts");
            System.out.println("5. Back to Admin Menu");
            System.out.print("Enter your choice: ");
            int postChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (postChoice) {
                case 1:
                    System.out.print("Enter Post Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Post Content: ");
                    String content = scanner.nextLine();
                    String addResult = Post.addPost(title, content);
                    System.out.println(addResult);
                    break;

                case 2:
                    System.out.print("Enter Post Title to update: ");
                    String updateTitle = scanner.nextLine();
                    System.out.print("Enter New Content: ");
                    String newContent = scanner.nextLine();
                    String updateResult = Post.updatePost(updateTitle, newContent);
                    System.out.println(updateResult);
                    break;

                case 3:
                    System.out.print("Enter Post Title to delete: ");
                    String deleteTitle = scanner.nextLine();
                    String deleteResult = Post.deletePost(deleteTitle);
                    System.out.println(deleteResult);
                    break;

                case 4:
                    String allPosts = Post.viewAllPosts();
                    System.out.println(allPosts);
                    break;

                case 5:
                    System.out.println("Returning to Admin Menu...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleManageFeedback(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Manage Feedback ---");
            System.out.println("1. View Feedback");
            System.out.println("2. Respond to Feedback");
            System.out.println("3. delete Feedback");
            System.out.println("4. Back to Admin Menu");
            System.out.print("Enter your choice: ");
            int feedbackChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (feedbackChoice) {
            case 1:
                System.out.println(account.viewFeedback()); // استدعاء الدالة من personaccount
                break;
            case 2:
                System.out.print("Enter feedback ID to respond to: ");
                int feedbackId = scanner.nextInt();
                scanner.nextLine(); // لاستهلاك السطر الجديد
                System.out.print("Enter your response: ");
                String response = scanner.nextLine();
                System.out.println(account.respondToFeedback(feedbackId, response)); // استدعاء الدالة من personaccount
                break;
            case 3:
                System.out.print("Enter feedback ID to delete: ");
                int deleteFeedbackId = scanner.nextInt();
                System.out.println(account.deleteFeedback(deleteFeedbackId)); // استدعاء الدالة من personaccount
                break;

                case 4:
                    System.out.println("Returning to Admin Menu...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleProductManagement(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Manage Products ---");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int productChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (productChoice) {
            case 1:
                System.out.print("Enter Product ID: ");
                String productId = scanner.nextLine();
                System.out.print("Enter Product Name: ");
                String productName = scanner.nextLine();
                System.out.print("Enter Description: ");
                String description = scanner.nextLine();
                System.out.print("Enter Price: ");
                String price = scanner.nextLine();
                System.out.print("Enter Availability: ");
                String availability = scanner.nextLine();
                String addResult = productSystem.addProduct(productId, productName, description, price, availability);
                System.out.println(addResult);
                break;

            case 2:
                System.out.print("Enter Product ID to update: ");
                String updateId = scanner.nextLine();
                if (productSystem.productExists(updateId)) {
                    System.out.print("Enter field to update (Product Name/Description/Price/Availability): ");
                    String field = scanner.nextLine();
                    System.out.print("Enter new value: ");
                    String value = scanner.nextLine();
                    Map<String, String> updatedDetails = new HashMap<>();
                    updatedDetails.put(field, value);
                    String updateResult = productSystem.updateProduct(updateId, updatedDetails);
                    System.out.println(updateResult);
                } else {
                    System.out.println("Product ID does not exist.");
                }
                break;

                case 3:
                    System.out.print("Enter Product ID to delete: ");
                    String deleteProdId = scanner.nextLine();
                    productSystem.deleteProduct(deleteProdId);
                    break;

                case 4:
                    System.out.println("Exiting Product Management.");
                    return; // Exit the product management menu loop

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void handleDiscountManagement(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Manage Discounts and Orders ---");
            System.out.println("1. Add Discount");
            System.out.println("2. Apply Discount");
            System.out.println("3. Print Orders");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Discount Name: ");
                    String discountName = scanner.nextLine();
                    System.out.print("Enter Discount Rate (in percentage): ");
                    double discountRate = Double.parseDouble(scanner.nextLine());
                    System.out.println(orderManager.addDiscount(discountName, discountRate));
                    break;

                case 2:
                    System.out.print("Enter Discount Name to Apply: ");
                    String applyDiscountName = scanner.nextLine();
                    System.out.println(orderManager.applyDiscount(applyDiscountName));
                    break;

               
                case 3:
                    ooorder.printOrders("Order List:", ooorder.orders);
                    break;

                   

                case 4:
                    System.out.println("Exiting Discount Management.");
                    return; // Exit the discount management menu loop

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }


    private static void handleOrderManagement(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Manage Orders ---");
            System.out.println("1. View Order");
            System.out.println("2. Update Order");
            System.out.println("3. Delete Order");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int orderChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (orderChoice) {
                case 1:
                	 ooorder.printOrders("Order List:", ooorder.orders);
                     break;

                case 2:
                    System.out.print("Enter Order ID to update: ");
                    String updateOrderId = scanner.nextLine();
                    if (ooorder.orders.containsKey(updateOrderId)) {
                        // الحصول على المعلومات الحالية للطلب
                        String[] orderInfo = ooorder.orders.get(updateOrderId);

                        // تحديث الحالة فقط إلى "Processing"
                        orderInfo[5] = "Processing"; // assuming index 5 is the status
                        
                        // تحديث الخريطة
                        ooorder.orders.put(updateOrderId, orderInfo);
                        
                        // حفظ التحديثات إلى الملف
                        ooorder.saveOrders();
                        
                        System.out.println("Order status updated to Processing.");
                    } else {
                        System.out.println("Order ID not found.");
                    }
                    break;


                case 3:
                    System.out.print("Enter Order ID to delete: ");
                    String deleteOrderId = scanner.nextLine();
                    String result = ooorder.deleteOrder(deleteOrderId);
                    System.out.println(result);
                    break;


                case 4:
                    System.out.println("Exiting Order Management.");
                    return; // Exit the order management menu loop

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    
    private static void handleCommunicationAndNotification(Scanner scanner, String currentLoggedInUser) {
        SupplierService supplierService = new SupplierService(currentLoggedInUser);

        String currentOwner = supplierService.getStoreOwnerNameFromEmail(currentLoggedInUser);

        if (currentOwner.isEmpty()) {
            System.out.println("No messages to reply to.");
            return;
        }

        List<String> messagesForOwner = supplierService.loadMessagesForOwner(currentOwner);

        if (messagesForOwner.isEmpty()) {
            System.out.println("No messages to reply to.");
            return;
        }

        while (true) {
            System.out.println("\n** Communication and Notification **");
            System.out.println("1. Reply to Message");
            System.out.println("2. Back to Store Owner Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Display messages for the owner
                    System.out.println("Messages for you:");
                    for (int i = 0; i < messagesForOwner.size(); i++) {
                        System.out.println((i + 1) + ". " + messagesForOwner.get(i));
                    }

                    // Get the message to reply to
                    System.out.print("Enter the number of the message you want to reply to: ");
                    int messageNumber = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (messageNumber < 1 || messageNumber > messagesForOwner.size()) {
                        System.out.println("Invalid message number.");
                        break;
                    }

                    String selectedMessage = messagesForOwner.get(messageNumber - 1);
                    System.out.println("Selected message: " + selectedMessage);

                    // Get the reply from the store owner
                    System.out.print("Enter your reply: ");
                    String reply = scanner.nextLine();

                    // Append the reply to the message.txt file
                    supplierService.appendReplyToMessageFile(currentOwner, selectedMessage, reply);

                    System.out.println("Reply sent successfully.");
                    break;

                case 2:
                    System.out.println("Returning to Store Owner Menu...");
                    return; // Exit Communication and Notification Menu and return to the Store Owner Menu

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }



    
    private static void handleUserMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n** User Menu **");
            System.out.println("1. Product Browsing Menu");
            System.out.println("2. Update Account Details");
            System.out.println("3. Make purchases ");
            System.out.println("4. search for dessert recipes");
            System.out.println("5. Provide feedback");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
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
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    public static void handleBrowseproducts(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Welcome to the Product Browsing Menu ---");
            System.out.println("1. Show all products");
            System.out.println("2. Search for a product by ID");
            System.out.println("3. Back to User Menu");
            System.out.print("Enter your choice: ");
            int accountChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (accountChoice) {
                case 1:
                 pp.showAllProducts();
                    break;

                case 2:
                    System.out.print("Enter the Product ID to search: ");
                    String productId = scanner.nextLine();
                   pp.searchProductById(productId);
                    break;

                case 3:
                    System.out.println("Returning to User Menu...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    
    public static void handleMakepurchases(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Welcome to the Purchase Menu ---");
            System.out.println("1. Add Order");
            System.out.println("2. Cancel Order");
            System.out.println("3. Back to User Menu");
            
            int accountChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (accountChoice) {
            case 1:
                System.out.println("Great choice! Let's proceed to add your order.");
                
                System.out.print("Enter Product ID: ");
                String productId = scanner.nextLine();
                
                System.out.print("Enter Quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                String addOrderResult = ooorder.addOrder(productId, quantity);
                System.out.println(addOrderResult);
                break;


                

                case 2:
                    System.out.println("Need to cancel an order? We’ll help you with that.");
                    System.out.print("Enter Order ID to cancel: ");
                    String deleteOrderId = scanner.nextLine();
                    String result = ooorder.deleteOrder(deleteOrderId);
                    System.out.println(result);
                    break;
                   

                case 3:
                    System.out.println("Returning to the User Menu. Have a great day!");
                    return;

                default:
                    System.out.println("Oops! It looks like you've entered an invalid option. Please choose a number between 1 and 4.");
            }
        }
    }

    
    public static void handleSearchForDessertRecipes(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Recipe Filter Menu ---");
            System.out.println("1. Search for a Recipe by Name");
            System.out.println("2. Filter Recipes by Allergies");
            System.out.println("3. Filter Recipes by Nutritional Value");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter the name of the recipe to search for: ");
                    String name = scanner.nextLine();
                    account.searchRecipeByName(name);
                    break;

                case 2:
                    

                    System.out.println("\nRecipes containing dairy:");
                    account.filterRecipesByAllergies("Contains dairy");

                   

                case 3:
                	System.out.println("Recipes matching all nutritional values:");
                    Set<String> criteria = new HashSet<>();
                    criteria.add("Contains nuts");
                    criteria.add("Contains dairy");
                    account.filterRecipesByNutritionalValue(criteria);
                    break;

                case 4:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    
  
    private static void handleManageUserAccount(Scanner scanner, MonitoringAndReportingww manager) {
        if (manager.getLoggedInUsername() == null) {
            System.out.println("Error: No user is currently logged in.");
            return;
        }

        while (true) {
            System.out.println("\n--- Manage Account ---");
            System.out.println("1. Change Password");
            System.out.println("2. Change Email");
            System.out.println("3. Back to User Menu");
            System.out.print("Enter your choice: ");
            int accountChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (accountChoice) {
                case 1:
                    System.out.print("Enter your new password: ");
                    String newPassword = scanner.nextLine();
                    manager.updateLogsignFile(null, newPassword);
                    System.out.println("Password updated successfully.");
                    break;

                case 2:
                    System.out.print("Enter your new email: ");
                    String newEmail = scanner.nextLine();
                    manager.updateLogsignFile(newEmail, null);
                    System.out.println("Email updated successfully.");
                    break;

                case 3:
                    System.out.println("Returning to User Menu...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private static void handleProvidefeedback(Scanner scanner) {
        System.out.println("We’d love to hear your thoughts! Please choose an option:");
        System.out.println("1. Add Feedback");
        System.out.println("2. Back to User Menu");
        
        int feedbackChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (feedbackChoice) {
            case 1:
                reporting.addFeedback(scanner);
                break;
                
            case 2:
                System.out.println("Returning to the User Menu. We hope you find what you're looking for!");
                return; // Exit the feedback menu and return to the purchase menu
                
            default:
                System.out.println("Oops! It seems you entered an invalid choice. Please try again.");
        }
    }
    
    private static void handleMessaging(Scanner scanner, String loggedInUser) {
        // Create or get the SupplierService instance with the logged-in user
        SupplierService supplierService = new SupplierService(loggedInUser);

        while (true) {
            System.out.println("\n** Messaging System **");
            System.out.println("1. Send a message to a store owner");
            System.out.println("2. Back to Supplier Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Get the owner's name and the message from the supplier
                    System.out.print("Enter the store owner's name: ");
                    String ownerName = scanner.nextLine();
                    System.out.print("Enter your message: ");
                    String message = scanner.nextLine();
                    
                    // Send the message
                    String result = supplierService.sendMessage(ownerName, message);
                    System.out.println(result);
                    break;

                case 2:
                    System.out.println("Returning to Supplier Menu...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }



}

