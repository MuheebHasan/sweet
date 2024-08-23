package my_sweet_management_system;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailService {

    private static final Logger logger = Logger.getLogger(EmailService.class.getName());
    private EmailDTO lastSentEmail; // To store the last sent email

    public boolean sendEmail(String to, String subject, String body) {
        lastSentEmail = new EmailDTO(to, subject, body); // Store email details
        return sendEmailInternal(to, subject, body); // Simulate sending email
    }

    public EmailDTO getLastSentEmail() {
        return lastSentEmail; // Return the last sent email
    }

    private boolean sendEmailInternal(String to, String subject, String body) {
        // Log email information
        logger.log(Level.INFO, "Email Sent: To = {0}, Subject = {1}, Body = {2}", new Object[]{to, subject, body});
        return true; // Assume sending was successful
    }

    // Static class to represent email details
    public static class EmailDTO {
        private String to;
        private String subject;
        private String body;

        public EmailDTO(String to, String subject, String body) {
            this.to = to;
            this.subject = subject;
            this.body = body;
        }

        // Getters
        public String getTo() { return to; }
        public String getSubject() { return subject; }
        public String getBody() { return body; }
    }
}
