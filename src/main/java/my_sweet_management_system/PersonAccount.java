package my_sweet_management_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class PersonAccount {
    // Define constants for file names
    private static final String RECIPES_FILE_NAME = "recipes.txt";
    private static final String POSTS_FILE_NAME = "posts.txt";
    private static final String FEEDBACK_FILE_NAME = "feedback.txt";
    private static final String ERROR_READING_RECIPES_FILE = "Error reading recipes file: {}";

    private static final Logger logger = LoggerFactory.getLogger(PersonAccount.class);

    private File recipesFile = new File(RECIPES_FILE_NAME);
    private File postsFile = new File(POSTS_FILE_NAME);
    private File feedbackFile = new File(FEEDBACK_FILE_NAME);

    public PersonAccount() {
        try {
            if (!recipesFile.exists()) {
                boolean created = recipesFile.createNewFile();
                if (!created) {
                    logger.warn("Failed to create recipes file: {}", RECIPES_FILE_NAME);
                }
            }
            if (!postsFile.exists()) {
                boolean created = postsFile.createNewFile();
                if (!created) {
                    logger.warn("Failed to create posts file: {}", POSTS_FILE_NAME);
                }
            }
            if (!feedbackFile.exists()) {
                boolean created = feedbackFile.createNewFile();
                if (!created) {
                    logger.warn("Failed to create feedback file: {}", FEEDBACK_FILE_NAME);
                }
            }
        } catch (IOException e) {
            logger.error("Error creating files: {}", e.getMessage());
        }
    }

    public static void searchRecipeByName(String name) {
        try (BufferedReader reader = new BufferedReader(new FileReader(RECIPES_FILE_NAME))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2 && parts[0].equalsIgnoreCase(name)) {
                    logger.info("{}: {}", parts[0], parts[1]);
                    found = true;
                    break;
                }
            }
            if (!found) {
                logger.info("Recipe not found.");
            }
        } catch (IOException e) {
            logger.error(ERROR_READING_RECIPES_FILE, e.getMessage());
        }
    }

    public static void filterRecipesByAllergies(String allergy) {
        try (BufferedReader reader = new BufferedReader(new FileReader(RECIPES_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String recipeName = parts[0];
                    String contents = parts[1];
                    if (contents.contains(allergy)) {
                        logger.info("{}: {}", recipeName, contents);
                    }
                }
            }
        } catch (IOException e) {
            logger.error(ERROR_READING_RECIPES_FILE, e.getMessage());
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
                        logger.info("{}: {}", recipeName, contents);
                    }
                }
            }
        } catch (IOException e) {
            logger.error(ERROR_READING_RECIPES_FILE, e.getMessage());
        }
    }

    public void showAllRecipes() {
        try (BufferedReader reader = new BufferedReader(new FileReader(RECIPES_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String recipeName = parts[0];
                    String contents = parts[1];
                    logger.info("{}: {}", recipeName, contents);
                }
            }
        } catch (IOException e) {
            logger.error(ERROR_READING_RECIPES_FILE, e.getMessage());
        }
    }

    public static void filterAndShowRecipes(String criterion) {
        try (BufferedReader reader = new BufferedReader(new FileReader(RECIPES_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String recipeName = parts[0];
                    String contents = parts[1];
                    if (contents.contains(criterion)) {
                        logger.info("{}: {}", recipeName, contents);
                    }
                }
            }
        } catch (IOException e) {
            logger.error(ERROR_READING_RECIPES_FILE, e.getMessage());
        }
    }

    public String addRecipe(String name, String ingredient, String quantity) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(recipesFile, true))) {
            writer.write(name + ";" + ingredient + ";" + quantity);
            writer.newLine();
            return "Recipe added successfully";
        } catch (IOException e) {
            logger.error("Failed to add recipe: {}", e.getMessage());
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
            logger.error("Failed to update recipe: {}", e.getMessage());
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
            logger.error("Failed to update recipe: {}", e.getMessage());
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
            logger.error("Failed to delete recipe: {}", e.getMessage());
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
            logger.error("Failed to delete recipe: {}", e.getMessage());
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
            logger.error("Failed to view recipe: {}", e.getMessage());
            return "Failed to view recipe";
        }
    }

    public String addPost(String title, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(postsFile, true))) {
            writer.write(title + ";" + content);
            writer.newLine();
            return "Post added successfully";
        } catch (IOException e) {
            logger.error("Failed to add post: {}", e.getMessage());
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
            logger.error("Failed to delete post: {}", e.getMessage());
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
            logger.error("Failed to delete post: {}", e.getMessage());
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
            logger.error("Failed to update post: {}", e.getMessage());
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
            logger.error("Failed to update post: {}", e.getMessage());
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
            logger.error("Failed to retrieve posts: {}", e.getMessage());
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
            logger.error("Failed to retrieve feedback: {}", e.getMessage());
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
                        // If there is an existing response, append the new response
                        line += " | " + response;
                    } else {
                        // If there are no responses, add the new response
                        line += "; Response: " + response;
                    }
                    found = true;
                }
                feedbacks.add(line);
            }
        } catch (IOException e) {
            logger.error("Failed to respond to feedback: {}", e.getMessage());
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
            logger.error("Failed to respond to feedback: {}", e.getMessage());
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
            logger.error("Failed to delete feedback: {}", e.getMessage());
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
            logger.error("Failed to delete feedback: {}", e.getMessage());
            return "Failed to delete feedback";
        }
    }
}
