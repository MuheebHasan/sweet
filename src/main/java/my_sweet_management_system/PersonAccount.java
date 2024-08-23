package my_sweet_management_system;
import java.io.*;
import java.util.*;
public class PersonAccount {
    private File recipesFile = new File("recipes.txt");
    private File postsFile = new File("posts.txt");
    private File feedbackFile = new File("feedback.txt");
    public PersonAccount() {
        try {
            if (!recipesFile.exists()) recipesFile.createNewFile();
            if (!postsFile.exists()) postsFile.createNewFile();
            if (!feedbackFile.exists()) feedbackFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void writeLinesToFile(List<String> lines, File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
    
    
    public static void searchRecipeByName(String name) {
        try (BufferedReader reader = new BufferedReader(new FileReader("recipes.txt"))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2 && parts[0].equalsIgnoreCase(name)) {
                    System.out.println(parts[0] + ": " + parts[1]);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Recipe not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void filterRecipesByAllergies(String allergy) {
        try (BufferedReader reader = new BufferedReader(new FileReader("recipes.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String recipeName = parts[0];
                    String contents = parts[1];
                    if (contents.contains(allergy)) {
                        System.out.println(recipeName + ": " + contents);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void filterRecipesByMultipleNutritionalValues(Set<String> criteria) {
        try (BufferedReader reader = new BufferedReader(new FileReader("recipes.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String recipeName = parts[0];
                    String contents = parts[1];
                    boolean matchesAll = true;
                    
                    // Check if all criteria are present in the contents
                    for (String criterion : criteria) {
                        if (!contents.contains(criterion)) {
                            matchesAll = false;
                            break;
                        }
                    }
                    
                    if (matchesAll) {
                        System.out.println(recipeName + ": " + contents);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void filterRecipesByNutritionalValue(Set<String> criteria) {
        try (BufferedReader reader = new BufferedReader(new FileReader(recipesFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String recipeName = parts[0];
                    String contents = parts[1];
                    boolean matchesAll = true;
                    
                    // Check if all criteria are present in the contents
                    for (String criterion : criteria) {
                        if (!contents.contains(criterion)) {
                            matchesAll = false;
                            break;
                        }
                    }
                    
                    if (matchesAll) {
                        System.out.println(recipeName + ": " + contents);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    
    public void showAllRecipes() {
        try (BufferedReader reader = new BufferedReader(new FileReader("recipes.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String recipeName = parts[0];
                    String contents = parts[1];
                    System.out.println(recipeName + ": " + contents);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Method to filter and show recipes based on a criterion
    public static void filterAndShowRecipes(String criterion) {
        try (BufferedReader reader = new BufferedReader(new FileReader("recipes.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String recipeName = parts[0];
                    String contents = parts[1];
                    if (contents.contains(criterion)) {
                        System.out.println(recipeName + ": " + contents);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   
    private List<String> readLinesFromFile(File file) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
    
    
    public String addRecipe(String name, String ingredient, String quantity) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(recipesFile, true))) {
            writer.write(name + ";" + ingredient + ";" + quantity);
            writer.newLine();
            return "Recipe added successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to add recipe";
        }
    }
    public String updateRecipe(String name, String newDescription) {
        List<String> recipes = new ArrayList<>();
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(recipesFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts[0].equals(name)) {
                    line = name + ";" + newDescription;
                    found = true;
                }
                recipes.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to update recipe";
        }
        if (!found) {
            return "Recipe not found";
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(recipesFile))) {
            for (String recipe : recipes) {
                writer.write(recipe);
                writer.newLine();
            }
            return "Recipe updated successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to update recipe";
        }
    }
    public String deleteRecipe(String name) {
        List<String> recipes = new ArrayList<>();
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(recipesFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(name + ";")) {
                    recipes.add(line);
                } else {
                    found = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to delete recipe";
        }
        if (!found) {
            return "Recipe not found";
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(recipesFile))) {
            for (String recipe : recipes) {
                writer.write(recipe);
                writer.newLine();
            }
            return "Recipe deleted successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to delete recipe";
        }
    }
    public String viewRecipe(String name) {
        try (BufferedReader reader = new BufferedReader(new FileReader(recipesFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(name + ";")) {
                    return line;
                }
            }
            return "Recipe not found";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to view recipe";
        }
    }
    public String addPost(String title, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(postsFile, true))) {
            writer.write(title + ";" + content);
            writer.newLine();
            return "Post added successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to add post";
        }
    }
    public String deletePost(String title) {
        List<String> posts = new ArrayList<>();
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(postsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(title + ";")) {
                    posts.add(line);
                } else {
                    found = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to delete post";
        }
        if (!found) {
            return "Post not found";
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(postsFile))) {
            for (String post : posts) {
                writer.write(post);
                writer.newLine();
            }
            return "Post deleted successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to delete post";
        }
    }
    public String updatePost(String title, String newContent) {
        List<String> posts = new ArrayList<>();
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(postsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts[0].equals(title)) {
                    line = title + ";" + newContent;
                    found = true;
                }
                posts.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to update post";
        }
        if (!found) {
            return "Post not found";
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(postsFile))) {
            for (String post : posts) {
                writer.write(post);
                writer.newLine();
            }
            return "Post updated successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to update post";
        }
    }
    
    public String viewAllPosts() {
        StringBuilder allPosts = new StringBuilder();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(postsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                allPosts.append(line).append("\n\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to retrieve posts";
        }
        if (allPosts.length() == 0) {
            return "No posts available.";
        }
        return allPosts.toString();
    }
    public String viewFeedback() {
        StringBuilder feedbackList = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(feedbackFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                feedbackList.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return feedbackList.toString();
    }
    public String respondToFeedback(int feedbackId, String response) {
        List<String> feedbacks = new ArrayList<>();
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(feedbackFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (Integer.parseInt(parts[0]) == feedbackId) {
                    if (parts.length > 3) {
                        // إذا كان هناك رد موجود، نضيف الرد الجديد
                        line += " | " + response;
                    } else {
                        // إذا لم يكن هناك ردود، نضيف الرد الجديد فقط
                        line += "; Response: " + response;
                    }
                    found = true;
                }
                feedbacks.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to respond to feedback";
        }
        if (!found) {
            return "Feedback with ID " + feedbackId + " not found.";
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(feedbackFile))) {
            for (String feedback : feedbacks) {
                writer.write(feedback);
                writer.newLine();
            }
            return "Feedback responded to successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to respond to feedback";
        }
    }
    public String deleteFeedback(int feedbackId) {
        List<String> feedbacks = new ArrayList<>();
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(feedbackFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (Integer.parseInt(parts[0]) != feedbackId) {
                    feedbacks.add(line);
                } else {
                    found = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to delete feedback";
        }
        if (!found) {
            return "Feedback with ID " + feedbackId + " not found.";
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(feedbackFile))) {
            for (String feedback : feedbacks) {
                writer.write(feedback);
                writer.newLine();
            }
            return "Feedback deleted successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to delete feedback";
        }
    }
}