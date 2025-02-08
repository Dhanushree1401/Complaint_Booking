import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Complaints {
    String Name;
    String Location;
    String Phone_No; // Change data type to String
    String Natureofcomplaint;

    public Complaints(String Name, String Location, String Phone_No, String Natureofcomplaint) {
        this.Name = Name;
        this.Location = Location;
        this.Phone_No = Phone_No;
        this.Natureofcomplaint = Natureofcomplaint;
    }
}

public class Main {

    private static final String CSV_FILE_NAME = "complaint_data.csv";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Complaints> complaintsList = loadFromFile();

        System.out.println("Welcome to DA agents\nUPS SERVICE AND REPAIR");

        while (true) {
            System.out.println("1. Add Complaint(s)");
            System.out.println("2. Display Complaints");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice;
            try {
                choice = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    addComplaints(scanner, complaintsList);
                    saveToFile(complaintsList);
                    break;
                case 2:
                    displayComplaints(complaintsList);
                    break;
                case 3:
                    saveToFile(complaintsList);
                    System.out.println("Exiting program. Thank you!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void addComplaints(Scanner scanner, ArrayList<Complaints> complaintsList) {
        System.out.print("Enter the number of complaints you want to add: ");
        int numberOfComplaints = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (int i = 0; i < numberOfComplaints; i++) {
            System.out.println("Enter details for Complaint #" + (i + 1));
            System.out.print("Enter Name: ");
            String Name = scanner.nextLine();
            System.out.print("Enter Location: ");
            String Location = scanner.nextLine();
            System.out.print("Enter Phone No: ");
            String Phone_No = scanner.nextLine(); // Change data type to String

            // Custom prompt for Nature of Complaints
            System.out.print("Describe the Nature of Complaint (e.g., 'Issue with UPS delivery', 'Service disruption', 'Package damage'): ");
            String Natureofcomplaint = scanner.nextLine();

            Complaints complaint = new Complaints(Name, Location, Phone_No, Natureofcomplaint);
            complaintsList.add(complaint);
            System.out.println("Complaint #" + (i + 1) + " added successfully!");
        }
    }

    private static void displayComplaints(ArrayList<Complaints> complaintsList) {
        if (complaintsList.isEmpty()) {
            System.out.println("No complaints to display.");
        } else {
            for (Complaints complaint : complaintsList) {
                System.out.println(complaint.Name + ", " + complaint.Location + ", " +
                        complaint.Phone_No + ", " + complaint.Natureofcomplaint);
            }
        }
    }

    private static void saveToFile(ArrayList<Complaints> complaintsList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_NAME))) {
            writer.write("Name,Location,Phone_No,Natureofcomplaint\n");
            for (Complaints complaint : complaintsList) {
                writer.write(String.format("%s,%s,%s,%s%n",
                        complaint.Name,
                        complaint.Location,
                        complaint.Phone_No,
                        complaint.Natureofcomplaint));
            }
            System.out.println("Data saved to file: " + CSV_FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }

    private static ArrayList<Complaints> loadFromFile() {
        ArrayList<Complaints> complaintsList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_NAME))) {
            String line;
            boolean headerSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!headerSkipped) {
                    // Skip the header line
                    headerSkipped = true;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String Name = parts[0];
                    String Location = parts[1];
                    String Phone_No = parts[2]; // Change data type to String
                    String Natureofcomplaint = parts[3];

                    Complaints complaint = new Complaints(Name, Location, Phone_No, Natureofcomplaint);
                    complaintsList.add(complaint);
                } else {
                    System.out.println("Error: Invalid data format in CSV file.");
                }
            }
            System.out.println("Data loaded from file: " + CSV_FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
        }
        return complaintsList;
    }
}
