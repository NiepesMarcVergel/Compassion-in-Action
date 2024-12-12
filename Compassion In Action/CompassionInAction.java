import java.util.ArrayList;
import java.util.Scanner;

public class CompassionInAction {
    public static ArrayList<User> users = new ArrayList<>();
    private static Admin admin = new Admin("admin", "admin123", "Headquarters");

    public static void main(String[] args) {
        users.add(admin); // The admin is added for access~

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Compassion in Action!");
        while (true) {
            System.out.println("\n1. Register\n2. Login\n3. Exit");
            System.out.print("Choose an option: ");
            
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid option. Try again.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    loginUser(scanner);
                    break;
                case 3:
                    System.out.println("Thank you for using Compassion in Action!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void registerUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter location: ");
        String location = scanner.nextLine();

        User user = new User(username, password, location);
        user.setProfile(scanner);
        users.add(user);
        System.out.println("User registered successfully.");
    }

    private static void loginUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User loggedInUser = null;
        for (User user : users) {
            if (user.getUsername().equals(username) && user.authenticate(password)) {
                loggedInUser = user;
                break;
            }
        }

        if (loggedInUser == null) {
            System.out.println("Invalid username or password.");
            return;
        }

        System.out.println("Welcome, " + loggedInUser.getUsername() + "!");
        if (loggedInUser instanceof Admin) {
            handleAdminMenu((Admin) loggedInUser, scanner);
        } else {
            handleUserMenu(loggedInUser, scanner);
        }
    }

    private static void handleUserMenu(User user, Scanner scanner) {
        while (true) {
            if (!admin.getFlashMessage().isEmpty()) {
                System.out.println("** " + admin.getFlashMessage() + " **");
            }
    
            System.out.println("\nHello, please pick your desired help~");
            System.out.println("1. Donate\n2. Volunteer\n3. Recent Participation\n4. View Notifications\n5. Exit");
            System.out.print("Choose an option: ");
    
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                scanner.next();
                continue;
            }
    
            int choice = scanner.nextInt();
            scanner.nextLine();
    
            switch (choice) {
                case 1:
                    handleDonation(user, scanner);
                    break;
                case 2:
                    applyForVolunteerWork(user, scanner);
                    break;
                case 3:
                    admin.displayRecentDonations();
                    break;
                case 4:
                    user.viewNotifications();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please choose a valid menu item.");
            }
        }
    }

    private static void handleAdminMenu(Admin admin, Scanner scanner) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Manage Donations\n2. Add Volunteer Work\n3. Remove Volunteer Work\n4. Manage Flash Message\n5. Review Volunteer Applications\n6. Logout");
            System.out.print("Choose an option: ");
    
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                scanner.next();
                continue;
            }
    
            int choice = scanner.nextInt();
            scanner.nextLine();
    
            switch (choice) {
                case 1:
                    manageDonations(admin, scanner);
                    break;
                case 2:
                    System.out.print("Enter description of volunteer work: ");
                    String description = scanner.nextLine();
                    admin.addVolunteerWork(description);
                    break;
                case 3:
                    removeVolunteerWork(admin, scanner);
                    break;
                case 4:
                    manageFlashMessage(admin, scanner);
                    break;
                case 5:
                    admin.reviewApplications();
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please choose a valid menu item.");
            }
        }
    }

    private static void manageDonations(Admin admin, Scanner scanner) {
        admin.displayRecentDonations();
        System.out.print("Enter the index of the donation to remove or -1 to cancel: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        if (index != -1) {
            admin.removeDonation(index);
        }
    }

    private static void removeVolunteerWork(Admin admin, Scanner scanner) {
        ArrayList<VolunteerWork> volunteerWorks = admin.getVolunteerWorks();
        if (volunteerWorks.isEmpty()) {
            System.out.println("No volunteer works available to remove.");
            return;
        }

        System.out.println("Current Volunteer Works:");
        for (int i = 0; i < volunteerWorks.size(); i++) {
            System.out.println(i + ". " + volunteerWorks.get(i).getDescription());
        }

        System.out.print("Enter the index of the volunteer work to remove or -1 to cancel: ");
        int index = scanner.nextInt();
        scanner.nextLine();

        if (index != -1) {
            admin.removeVolunteerWork(index);
        }
    }

    private static void manageFlashMessage(Admin admin, Scanner scanner) {
        System.out.println("Current Flash Message: " + (admin.getFlashMessage().isEmpty() ? "None" : admin.getFlashMessage()));
        System.out.println("1. Set Flash Message\n2. Clear Flash Message\n3. Cancel");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter new flash message: ");
                String message = scanner.nextLine();
                admin.setFlashMessage(message);
                break;
            case 2:
                admin.clearFlashMessage();
                break;
            case 3:
                System.out.println("Cancelled.");
                break;
            default:
                System.out.println("Invalid option. Try again.");
        }
    }

    private static void handleDonation(User user, Scanner scanner) {
        System.out.println("Enter the type of donation (e.g., Clothes, Food, Toiletries, Money, Other): ");
        String type = scanner.nextLine();
        System.out.println("Enter details of your donation: ");
        String details = scanner.nextLine();
        admin.acceptDonation(new Donation(user.getUsername(), type, details));
        System.out.println("Thank you for donating " + type + ": " + details);
    }

    private static void applyForVolunteerWork(User user, Scanner scanner) {
        System.out.println("Available Volunteer Opportunities:");
        ArrayList<VolunteerWork> volunteerWorks = admin.getVolunteerWorks();
        if (volunteerWorks.isEmpty()) {
            System.out.println("No available volunteer opportunities at the moment.");
            return;
        }

        for (int i = 0; i < volunteerWorks.size(); i++) {
            System.out.println(i + ". " + volunteerWorks.get(i).getDescription());
        }

        System.out.print("Enter the index of the work you'd like to apply for, or -1 to cancel: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice >= 0 && choice < volunteerWorks.size()) {
            VolunteerWork selectedWork = volunteerWorks.get(choice);
            VolunteerApplication application = new VolunteerApplication(
                user.getUsername(),
                selectedWork.getDescription(),
                user.getSkills(),
                user.getPreferredCauses()
            );
            admin.addApplication(application);
            System.out.println("Application submitted for volunteer work: " + selectedWork.getDescription());
        } else {
            System.out.println("Cancelled application.");
        }
    }
}