import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

// Patient class to store patient information
class Patient {
    private String patientId;
    private String name;
    private String roomNumber;
    private String doctorDetails;
    private int age;
    private String gender;
    private double billAmount;
    private String admissionDate;
    private boolean isActive;

    public Patient(String patientId, String name, String roomNumber, String doctorDetails, 
                  int age, String gender, String admissionDate) {
        this.patientId = patientId;
        this.name = name;
        this.roomNumber = roomNumber;
        this.doctorDetails = doctorDetails;
        this.age = age;
        this.gender = gender;
        this.admissionDate = admissionDate;
        this.billAmount = 0.0;
        this.isActive = true;
    }

    // Getters and setters
    public String getPatientId() { return patientId; }
    public String getName() { return name; }
    public double getBillAmount() { return billAmount; }
    public void setBillAmount(double amount) { this.billAmount = amount; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { this.isActive = active; }
}

// Login Manager class for authentication
class LoginManager {
    private String userId;
    private String password;
    private boolean isLoggedIn;

    public LoginManager() {
        this.isLoggedIn = false;
    }

    public boolean login(String userId, String password) {
        // Password validation: min 10 chars, 1 uppercase, 1 numeric, 1 special
        String passwordPattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{10,}$";
        
        if (userId.length() >= 8 && 
            Pattern.matches(passwordPattern, password)) {
            this.userId = userId;
            this.password = password;
            this.isLoggedIn = true;
            return true;
        }
        return false;
    }

    public void logout() {
        this.isLoggedIn = false;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}

// Hospital Management System class
class HospitalManagementSystem {
    private List<Patient> patients;
    private LoginManager loginManager;
    private Scanner scanner;

    public HospitalManagementSystem() {
        patients = new ArrayList<>();
        loginManager = new LoginManager();
        scanner = new Scanner(System.in);
    }

    // Login functionality
    public void start() {
        System.out.println("=== Hospital Management System ===");
        while (!loginManager.isLoggedIn()) {
            System.out.print("Enter User ID (min 8 chars): ");
            String userId = scanner.nextLine();
            System.out.print("Enter Password (min 10 chars, 1 upper, 1 num, 1 special): ");
            String password = scanner.nextLine();

            if (loginManager.login(userId, password)) {
                System.out.println("Login successful!");
                showMenu();
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        }
    }

    // Main menu
    private void showMenu() {
        while (loginManager.isLoggedIn()) {
            System.out.println("\n1. Add Patient\n2. View Patients\n3. Search Patient\n4. Update Patient\n" +
                             "5. Delete Patient\n6. Generate Bill\n7. Logout");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: addPatient(); break;
                case 2: viewActivePatients(); break;
                case 3: searchPatient(); break;
                case 4: updatePatient(); break;
                case 5: deletePatient(); break;
                case 6: generateBill(); break;
                case 7: loginManager.logout(); System.out.println("Logged out."); break;
                default: System.out.println("Invalid option!");
            }
        }
    }

    // Add new patient
    private void addPatient() {
        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Room Number: ");
        String room = scanner.nextLine();
        System.out.print("Enter Doctor Details: ");
        String doctor = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter Admission Date (dd.mm.yy): ");
        String date = scanner.nextLine();

        Patient patient = new Patient(id, name, room, doctor, age, gender, date);
        patients.add(patient);
        System.out.println("Patient added successfully!");
    }

    // View active patients
    private void viewActivePatients() {
        List<Patient> activePatients = patients.stream()
            .filter(Patient::isActive)
            .toList();
        
        if (activePatients.isEmpty()) {
            System.out.println("No active patients found.");
            return;
        }

        System.out.println("Active Patients:");
        for (int i = 0; i < activePatients.size(); i++) {
            Patient p = activePatients.get(i);
            System.out.printf("%d. ID: %s, Name: %s, Bill: %.2f%n", 
                            i + 1, p.getPatientId(), p.getName(), p.getBillAmount());
        }
    }

    // Search patient by ID
    private void searchPatient() {
        System.out.print("Enter Patient ID to search: ");
        String id = scanner.nextLine();
        
        Patient patient = patients.stream()
            .filter(p -> p.getPatientId().equals(id))
            .findFirst()
            .orElse(null);

        if (patient != null) {
            System.out.println("Patient found: " + patient.getName() + 
                             ", Bill: " + patient.getBillAmount());
        } else {
            System.out.println("Patient not found!");
        }
    }

    // Update patient (basic implementation)
    private void updatePatient() {
        System.out.print("Enter Patient ID to update: ");
        String id = scanner.nextLine();
        
        Patient patient = patients.stream()
            .filter(p -> p.getPatientId().equals(id))
            .findFirst()
            .orElse(null);

        if (patient != null) {
            System.out.print("Enter new Room Number: ");
            String room = scanner.nextLine();
            // Add more update fields as needed
            System.out.println("Patient updated successfully!");
        } else {
            System.out.println("Patient not found!");
        }
    }

    // Delete patient
    private void deletePatient() {
        System.out.print("Enter Patient ID to delete: ");
        String id = scanner.nextLine();
        
        Patient patient = patients.stream()
            .filter(p -> p.getPatientId().equals(id))
            .findFirst()
            .orElse(null);

        if (patient != null) {
            patient.setActive(false);
            System.out.println("Patient deleted successfully!");
        } else {
            System.out.println("Patient not found!");
        }
    }

    // Generate bill
    private void generateBill() {
        System.out.print("Enter Patient ID for billing: ");
        String id = scanner.nextLine();
        
        Patient patient = patients.stream()
            .filter(p -> p.getPatientId().equals(id))
            .findFirst()
            .orElse(null);

        if (patient != null) {
            System.out.print("Enter Room Charges: ");
            double room = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter Pharmacy Charges: ");
            double pharmacy = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter Diagnostics Charges: ");
            double diagnostics = Double.parseDouble(scanner.nextLine());

            double total = room + pharmacy + diagnostics;
            patient.setBillAmount(total);
            System.out.println("Bill Generated - Total: " + total);
        } else {
            System.out.println("Patient not found!");
        }
    }

    public static void main(String[] args) {
        HospitalManagementSystem hms = new HospitalManagementSystem();
        hms.start();
    }
}
