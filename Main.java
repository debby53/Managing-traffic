package TRAFFIC;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.



// Abstract Base Class: Person
abstract class Person {
    private String name;
    private String licenseNumber;

    public Person(String name, String licenseNumber) throws InvalidLicenseException {
        if (!isValidLicenseNumber(licenseNumber)) {
            throw new InvalidLicenseException("License number must be alphanumeric and 8-12 characters long.");
        }
        this.name = name;
        this.licenseNumber = licenseNumber;
    }

    public String getName() {
        return name;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    private boolean isValidLicenseNumber(String licenseNumber) {
        return licenseNumber.matches("[a-zA-Z0-9]{8,12}");
    }

    public abstract void displayDetails();
}

// Driver Class (Derived from Person)
class Driver extends Person {
    private double totalFines;
    private int violationCount;
    private List<SpecificViolation> violations;

    public Driver(String name, String licenseNumber) throws InvalidLicenseException {
        super(name, licenseNumber);
        this.totalFines = 0.0;
        this.violationCount = 0;
        this.violations = new ArrayList<>();
    }

    public void addViolation(SpecificViolation violation) {
        this.violations.add(violation);
        this.totalFines += violation.getFineAmount();
        this.violationCount++;
    }

    public void resetViolations() {
        this.violations.clear();
        this.totalFines = 0.0;
        this.violationCount = 0;
    }

    public double getTotalFines() {
        return totalFines;
    }

    public int getViolationCount() {
        return violationCount;
    }

    public List<SpecificViolation> getViolations() {
        return violations;
    }

    @Override
    public void displayDetails() {
        System.out.println("Driver Name: " + getName());
        System.out.println("License Number: " + getLicenseNumber());
        System.out.println("Total Fines: $" + totalFines);
        System.out.println("Violation Count: " + violationCount);
        System.out.println("Violations:");
        for (SpecificViolation violation : violations) {
            System.out.println(violation.getViolationDetails());
        }
    }
}

// Interface: TrafficViolation
interface TrafficViolation {
    void validateViolation() throws InvalidViolationException;
}

// SpecificViolation Class (Implements TrafficViolation)
class SpecificViolation implements TrafficViolation {
    private String violationType;
    private double fineAmount;

    private static final List<String> VALID_TYPES = List.of("Speeding", "Parking", "Signal Violation");

    public SpecificViolation(String violationType, double fineAmount) throws InvalidViolationException {
        this.violationType = violationType;
        this.fineAmount = fineAmount;
        validateViolation();
    }

    public String getViolationType() {
        return violationType;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    @Override
    public void validateViolation() throws InvalidViolationException {
        if (fineAmount <= 0) {
            throw new InvalidViolationException("Fine amount must be greater than zero.");
        }
        if (!VALID_TYPES.contains(violationType)) {
            throw new InvalidViolationException("Invalid violation type. Valid types are: " + VALID_TYPES);
        }
    }

    public String getViolationDetails() {
        return "Type: " + violationType + ", Fine: $" + fineAmount;
    }
}

// Custom Exceptions
class InvalidLicenseException extends Exception {
    public InvalidLicenseException(String message) {
        super(message);
    }
}

class InvalidViolationException extends Exception {
    public InvalidViolationException(String message) {
        super(message);
    }
}

// Main Class
class TrafficFineManagementSystem {
    private static final List<Driver> drivers = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nTraffic Fine Management System");
            System.out.println("1. Add Driver");
            System.out.println("2. Add Violation");
            System.out.println("3. View Driver Details");
            System.out.println("4. Reset Violations");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addDriver();
                case 2 -> addViolation();
                case 3 -> viewDriverDetails();
                case 4 -> resetViolations();
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addDriver() {
        try {
            System.out.print("Enter Driver Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter License Number: ");
            String licenseNumber = scanner.nextLine();

            Driver driver = new Driver(name, licenseNumber);
            drivers.add(driver);
            System.out.println("Driver added successfully.");
        } catch (InvalidLicenseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addViolation() {
        try {
            System.out.print("Enter Driver's License Number: ");
            String licenseNumber = scanner.nextLine();

            Driver driver = findDriverByLicense(licenseNumber);
            if (driver == null) {
                System.out.println("Driver not found.");
                return;
            }

            System.out.print("Enter Violation Type (Speeding, Parking, Signal Violation): ");
            String violationType = scanner.nextLine();
            System.out.print("Enter Fine Amount: ");
            double fineAmount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            SpecificViolation violation = new SpecificViolation(violationType, fineAmount);
            driver.addViolation(violation);
            System.out.println("Violation added successfully.");
        } catch (InvalidViolationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewDriverDetails() {
        System.out.print("Enter Driver's License Number: ");
        String licenseNumber = scanner.nextLine();

        Driver driver = findDriverByLicense(licenseNumber);
        if (driver != null) {
            driver.displayDetails();
        } else {
            System.out.println("Driver not found.");
        }
    }

    private static void resetViolations() {
        System.out.print("Enter Driver's License Number: ");
        String licenseNumber = scanner.nextLine();

        Driver driver = findDriverByLicense(licenseNumber);
        if (driver != null) {
            driver.resetViolations();
            System.out.println("Violations reset successfully.");
        } else {
            System.out.println("Driver not found.");
        }
    }

    private static Driver findDriverByLicense(String licenseNumber) {
        return drivers.stream()
                .filter(driver -> driver.getLicenseNumber().equals(licenseNumber))
                .findFirst()
                .orElse(null);
    }
}
