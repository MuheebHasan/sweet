package my_sweet_management_system;

public class UserAccount {
    private String name;
    private String email;
    private String phone;

    public UserAccount(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Email: %s, Phone: %s", name, email, phone);
    }
}



