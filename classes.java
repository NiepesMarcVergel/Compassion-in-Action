public abstract class User {
    protected String name;
    protected String email;
    protected String role;

    public User(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public abstract void viewDashboard();
}

// subclass for prob options
public class Donor extends User {
    private double totalDonated;

    public Donor(String name, String email) {
        super(name, email, "Donor");
    }

    @Override
    public void viewDashboard() {
        // donor dashB
    }

    public void donate(double amount, String campaignId) {
        // donation will prob taken out
    }
}

public class Volunteer extends User {
    private int hoursVolunteered;

    public Volunteer(String name, String email) {
        super(name, email, "Volunteer");
    }

    @Override
    public void viewDashboard() {
        // Show volunteer dashB   }

    public void signUpForEvent(String eventId) {
        // Logic for signing up for an event
    }
}
