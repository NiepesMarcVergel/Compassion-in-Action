import java.util.ArrayList;
import java.util.Scanner;

class Donation {
    private String donorName;
    private String type;
    private String details;

    public Donation(String donorName, String type, String details) {
        this.donorName = donorName;
        this.type = type;
        this.details = details;
    }

    public String getDonorName() {
        return donorName;
    }

    public String getDetails() {
        return type + " - " + details;
    }
}

class VolunteerWork {
    private String description;

    public VolunteerWork(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

class VolunteerApplication {
    private String applicantName;
    private String workDescription;
    private String applicantSkills;
    private String applicantPreferredCauses;
    private String status;

    public VolunteerApplication(String applicantName, String workDescription, String skills, String causes) {
        this.applicantName = applicantName;
        this.workDescription = workDescription;
        this.applicantSkills = skills;
        this.applicantPreferredCauses = causes;
        this.status = "Pending";
    }

    public String getApplicantName() {
        return applicantName;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    public String getApplicantSkills() {
        return applicantSkills;
    }

    public String getApplicantPreferredCauses() {
        return applicantPreferredCauses;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

public class Admin extends User {
    private ArrayList<Donation> pendingDonations;
    private ArrayList<VolunteerWork> volunteerWorks;
    private ArrayList<VolunteerApplication> applications;
    private String flashMessage;

    public Admin(String username, String password, String location) {
        super(username, password, location);
        this.pendingDonations = new ArrayList<>();
        this.volunteerWorks = new ArrayList<>();
        this.applications = new ArrayList<>();
        this.flashMessage = "";
    }

    public void addVolunteerWork(String description) {
        volunteerWorks.add(new VolunteerWork(description));
        System.out.println("Added new volunteer work: " + description);
    }

    public void removeVolunteerWork(int index) {
        if (index >= 0 && index < volunteerWorks.size()) {
            VolunteerWork removed = volunteerWorks.remove(index);
            System.out.println("Removed volunteer work: " + removed.getDescription());
        } else {
            System.out.println("Invalid volunteer work index.");
        }
    }

    public void acceptDonation(Donation donation) {
        pendingDonations.add(donation);
        System.out.println("Accepted donation from " + donation.getDonorName() + ": " + donation.getDetails());
    }

    public void removeDonation(int index) {
        if (index >= 0 && index < pendingDonations.size()) {
            Donation removed = pendingDonations.remove(index);
            System.out.println("Removed donation from " + removed.getDonorName() + ": " + removed.getDetails());
        } else {
            System.out.println("Invalid donation index.");
        }
    }

    public void displayRecentDonations() {
        System.out.println("Recent Donations:");
        for (int i = 0; i < Math.min(10, pendingDonations.size()); i++) {
            Donation donation = pendingDonations.get(i);
            System.out.println(i + ". " + donation.getDonorName() + ": " + donation.getDetails());
        }
    }

    public ArrayList<VolunteerWork> getVolunteerWorks() {
        return volunteerWorks;
    }

    public String getFlashMessage() {
        return flashMessage;
    }

    public void setFlashMessage(String message) {
        this.flashMessage = message;
        System.out.println("Flash message updated.");
    }

    public void clearFlashMessage() {
        this.flashMessage = "";
        System.out.println("Flash message cleared.");
    }

    public void addApplication(VolunteerApplication application) {
        applications.add(application);
        System.out.println("Application received from " + application.getApplicantName() + " for work: " + application.getWorkDescription());
    }

    public void reviewApplications() {
        if (applications.isEmpty()) {
            System.out.println("No applications to review.");
            return;
        }
    
        System.out.println("Pending Volunteer Applications:");
        for (int i = 0; i < applications.size(); i++) {
            VolunteerApplication app = applications.get(i);
            System.out.println(i + ". " + app.getApplicantName() + " applied for: " + app.getWorkDescription() + " [Status: " + app.getStatus() + "]");
        }
    
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the index of the application to review or -1 to cancel: ");
        int index = scanner.nextInt();
        scanner.nextLine();
    
        if (index >= 0 && index < applications.size()) {
            VolunteerApplication app = applications.get(index);
            System.out.println("Reviewing application from " + app.getApplicantName() + " for " + app.getWorkDescription());
            System.out.println("Applicant's Skills: " + app.getApplicantSkills());
            System.out.println("Applicant's Preferred Causes: " + app.getApplicantPreferredCauses());
            System.out.println("1. Accept\n2. Decline\n3. Cancel");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
    
            User applicant = findUserByUsername(app.getApplicantName());
            if (choice == 1) {
                app.setStatus("Accepted");
                System.out.println("Application accepted.");
                if (applicant != null) {
                    applicant.addNotification("Your application for volunteer work (" + app.getWorkDescription() + ") was accepted.");
                }
            } else if (choice == 2) {
                app.setStatus("Declined");
                System.out.println("Application declined.");
                if (applicant != null) {
                    applicant.addNotification("Your application for volunteer work (" + app.getWorkDescription() + ") was declined.");
                }
            } else {
                System.out.println("Cancelled.");
            }
        } else {
            System.out.println("No action taken.");
        }
    }
    
    private User findUserByUsername(String username) {
        for (User user : CompassionInAction.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }    
}