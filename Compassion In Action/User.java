import java.util.ArrayList;
import java.util.Scanner;

class Notification {
    private String message;
    private boolean isRead;

    public Notification(String message) {
        this.message = message;
        this.isRead = false;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void markAsRead() {
        isRead = true;
    }
}

public class User {
    private String username;
    private String password;
    private String location;
    private String preferredCauses;
    private String skills;
    private ArrayList<Notification> notifications;

    public User(String username, String password, String location) {
        this.username = username;
        this.password = password;
        this.location = location;
        this.preferredCauses = "";
        this.skills = "";
        this.notifications = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getLocation() {
        return location;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public void setProfile(Scanner scanner) {
        System.out.print("Enter your preferred causes (e.g., Education, Health, Environment): ");
        this.preferredCauses = scanner.nextLine();
        System.out.print("Enter your skills (e.g., Teaching, First Aid, Cooking): ");
        this.skills = scanner.nextLine();
    }

    public void displayProfile() {
        System.out.println("User Profile:");
        System.out.println("Preferred Causes: " + (preferredCauses.isEmpty() ? "Not set" : preferredCauses));
        System.out.println("Skills: " + (skills.isEmpty() ? "Not set" : skills));
    }

    public String getPreferredCauses() {
        return preferredCauses;
    }

    public String getSkills() {
        return skills;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void addNotification(String message) {
        notifications.add(new Notification(message));
    }

    public void viewNotifications() {
        System.out.println("\nNotifications:");
        if (notifications.isEmpty()) {
            System.out.println("No new notifications.");
        } else {
            for (int i = 0; i < notifications.size(); i++) {
                Notification notification = notifications.get(i);
                String status = notification.isRead() ? "[Read]" : "[Unread]";
                System.out.println(i + ". " + status + " " + notification.getMessage());
            }
            markNotificationsAsRead();
        }
    }

    private void markNotificationsAsRead() {
        for (Notification notification : notifications) {
            notification.markAsRead();
        }
    }
}