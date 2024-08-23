package my_sweet_management_system;

public class EmailService {

    private EmailDTO lastSentEmail; // لتخزين آخر بريد إلكتروني تم إرساله

    public boolean sendEmail(String to, String subject, String body) {
        lastSentEmail = new EmailDTO(to, subject, body); // تخزين تفاصيل البريد الإلكتروني
        return sendEmailInternal(to, subject, body); // محاكاة إرسال البريد الإلكتروني
    }

    public EmailDTO getLastSentEmail() {
        return lastSentEmail; // إرجاع آخر بريد إلكتروني تم إرساله
    }

    private boolean sendEmailInternal(String to, String subject, String body) {
        // طباعة معلومات البريد الإلكتروني
        System.out.println("Email Sent: To = " + to + ", Subject = " + subject + ", Body = " + body);
        return true; // افترض أن الإرسال كان ناجحاً
    }

    // فئة ثابتة لتمثيل تفاصيل البريد الإلكتروني
    public static class EmailDTO {
        private String to;
        private String subject;
        private String body;

        public EmailDTO(String to, String subject, String body) {
            this.to = to;
            this.subject = subject;
            this.body = body;
        }

        // getters
        public String getTo() { return to; }
        public String getSubject() { return subject; }
        public String getBody() { return body; }
    }
}
