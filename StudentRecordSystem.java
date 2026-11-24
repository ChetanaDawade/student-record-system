import java.util.ArrayList;
import java.util.Scanner;

// Student class to store record details
class Student {
    private int id;
    private String name;
    private double marks;
    private char grade;

    // Constructor
    public Student(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
        this.grade = calculateGrade(marks);
    }

    // Method to calculate grade based on marks
    private char calculateGrade(double marks) {
        if (marks >= 90) return 'A';
        else if (marks >= 80) return 'B';
        else if (marks >= 70) return 'C';
        else if (marks >= 60) return 'D';
        else return 'F';
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMarks(double marks) {
        this.marks = marks;
        this.grade = calculateGrade(marks); // Recalculate grade if marks change
    }

    public String getName() {
        return name;
    }

    public double getMarks() {
        return marks;
    }

    public char getGrade() {
        return grade;
    }

    // Display student details
    public void displayStudent() {
        System.out.printf("ID: %-5d | Name: %-15s | Marks: %-6.2f | Grade: %c%n", id, name, marks, grade);
    }
}

// Main class with console interface
public class StudentRecordSystem {
    private static ArrayList<Student> studentList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    printStatistics();
                    break;
                case 7:
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please choose between 1 and 7.");
            }
            System.out.println();
        } while (choice != 7);
        
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("=========================================");
        System.out.println("    STUDENT RECORD & GRADING SYSTEM      ");
        System.out.println("=========================================");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Update Student Details");
        System.out.println("5. Delete Student Record");
        System.out.println("6. View Class Statistics");
        System.out.println("7. Exit");
        System.out.println("-----------------------------------------");
    }

    private static void addStudent() {
        System.out.println("\n--- Add New Student ---");
        int id = getIntInput("Enter Student ID: ");

        // Check if ID already exists
        if (findStudentById(id) != null) {
            System.out.println("Error: A student with ID " + id + " already exists!");
            return;
        }

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();

        double marks = getDoubleInput("Enter Marks (0 - 100): ");
        while (marks < 0 || marks > 100) {
            System.out.println("Invalid marks! Please enter a value between 0 and 100.");
            marks = getDoubleInput("Enter Marks (0 - 100): ");
        }

        Student newStudent = new Student(id, name, marks);
        studentList.add(newStudent);
        System.out.println("Student added successfully!");
    }

    private static void viewAllStudents() {
        System.out.println("\n--- Student Records ---");
        if (studentList.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        for (Student s : studentList) {
            s.displayStudent();
        }
    }

    private static void searchStudent() {
        System.out.println("\n--- Search Student ---");
        int id = getIntInput("Enter Student ID to search: ");
        Student s = findStudentById(id);

        if (s != null) {
            System.out.println("Student Found:");
            s.displayStudent();
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }
    }

    private static void updateStudent() {
        System.out.println("\n--- Update Student Record ---");
        int id = getIntInput("Enter Student ID to update: ");
        Student s = findStudentById(id);

        if (s == null) {
            System.out.println("Student with ID " + id + " not found.");
            return;
        }

        System.out.println("Current details:");
        s.displayStudent();

        System.out.print("Enter new name (or press Enter to keep current): ");
        String newName = scanner.nextLine();
        if (!newName.trim().isEmpty()) {
            s.setName(newName);
        }

        System.out.print("Do you want to update marks? (y/n): ");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("y")) {
            double newMarks = getDoubleInput("Enter new Marks (0 - 100): ");
            while (newMarks < 0 || newMarks > 100) {
                System.out.println("Invalid marks! Please enter a value between 0 and 100.");
                newMarks = getDoubleInput("Enter new Marks (0 - 100): ");
            }
            s.setMarks(newMarks);
        }

        System.out.println("Student record updated successfully!");
    }

    private static void deleteStudent() {
        System.out.println("\n--- Delete Student Record ---");
        int id = getIntInput("Enter Student ID to delete: ");
        Student s = findStudentById(id);

        if (s != null) {
            studentList.remove(s);
            System.out.println("Student record deleted successfully.");
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }
    }

    private static void printStatistics() {
        System.out.println("\n--- Class Statistics ---");
        if (studentList.isEmpty()) {
            System.out.println("No records available to calculate statistics.");
            return;
        }

        double totalMarks = 0;
        double highestMarks = studentList.get(0).getMarks();
        double lowestMarks = studentList.get(0).getMarks();
        String topStudent = studentList.get(0).getName();

        for (Student s : studentList) {
            double marks = s.getMarks();
            totalMarks += marks;

            if (marks > highestMarks) {
                highestMarks = marks;
                topStudent = s.getName();
            }
            if (marks < lowestMarks) {
                lowestMarks = marks;
            }
        }

        double averageMarks = totalMarks / studentList.size();

        System.out.println("Total Students : " + studentList.size());
        System.out.printf("Average Marks  : %.2f%n", averageMarks);
        System.out.println("Highest Marks  : " + highestMarks + " (Scored by: " + topStudent + ")");
        System.out.println("Lowest Marks   : " + lowestMarks);
    }

    // Helper method to find a student object by ID
    private static Student findStudentById(int id) {
        for (Student s : studentList) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    // Helper method for safe integer inputs
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input! Please enter a valid integer.");
            scanner.next(); // consume invalid token
            System.out.print(prompt);
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline
        return value;
    }

    // Helper method for safe double inputs
    private static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input! Please enter a valid number.");
            scanner.next(); // consume invalid token
            System.out.print(prompt);
        }
        double value = scanner.nextDouble();
        scanner.nextLine(); // consume leftover newline
        return value;
    }
}