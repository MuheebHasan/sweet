package mySweetmanagementsystem;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MessageService {
    private static final String MESSAGE_FILE = "messages.txt";
    private static final String EMAIL_FILE = "emails.txt";

    private Map<String, StringBuilder> messages = new HashMap<>();
    private Map<String, String> emails = new HashMap<>();
    private AccountService accountService;

    public MessageService() {
        this.accountService = new AccountService();
        loadMessages();
        loadEmails();
    }

    public String sendMessage(String sender, String receiver, String message) {
        if (accountService.accountExists(sender) && accountService.accountExists(receiver)) {
            StringBuilder messageContent = messages.getOrDefault(receiver, new StringBuilder());
            messageContent.append(sender).append(": ").append(message).append("\n");
            messages.put(receiver, messageContent);
            saveMessages();
            return "Message sent successfully!";
        } else {
            return "Sender or receiver does not exist";
        }
    }

    public String receiveMessage(String recipient) {
        StringBuilder messageContent = messages.get(recipient);
        if (messageContent != null && messageContent.length() > 0) {
            return messageContent.toString();
        } else {
            return "No new messages for " + recipient;
        }
    }


    public String sendEmail(String sender, String recipientEmail, String subject, String body) {
        String email = "From: " + sender + "\nSubject: " + subject + "\nBody: " + body;
        emails.put(recipientEmail, email);
        saveEmails();
        return "Email sent successfully!";
    }

    public String receiveEmail(String recipient) {
        return emails.getOrDefault(recipient, "No new emails for " + recipient);
    }

    private void loadMessages() {
        File file = new File(MESSAGE_FILE);
        if (!file.exists()) {
            return; // No messages to load
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ", 3);
                if (parts.length == 3) {
                    String recipient = parts[0].trim();
                    String senderAndMessage = parts[1].trim() + ": " + parts[2].trim();
                    StringBuilder existingMessages = messages.getOrDefault(recipient, new StringBuilder());
                    existingMessages.append(senderAndMessage).append("\n");
                    messages.put(recipient, existingMessages);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveMessages() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MESSAGE_FILE))) {
            for (Map.Entry<String, StringBuilder> entry : messages.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEmails() {
        File file = new File(EMAIL_FILE);
        if (!file.exists()) {
            return; // No emails to load
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ", 2);
                if (parts.length == 2) {
                    emails.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveEmails() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EMAIL_FILE))) {
            for (Map.Entry<String, String> entry : emails.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
