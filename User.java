package project.oop;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
    protected Scanner input;
    protected String username;
    protected String password;
    protected int userId;
    protected String firstName;
    protected String lastName;
    protected String userCNIC;
    protected String userPhoneNo;
    protected String userEMail;
    protected String userDepartment;

    public User() {
        input = new Scanner(System.in);
        username = null;
        password = null;
        userId = (int) (Math.random() * 999);
        firstName = null;
        lastName = null;
        userCNIC = null;
        userPhoneNo = null;
        userEMail = null;
        userDepartment = null;
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("\n\t\t\t*** Menu ***");
            System.out.println("1. Student Account");
            System.out.println("2. Faculty Account");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = input.nextInt();
            input.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    Student student = new Student();
                    System.out.println("\n\t*** Student Account ***");
                    System.out.println("1. Add Student");
                    System.out.println("2. View Student");
                    System.out.println("3. Update Student");
                    System.out.println("4. Delete Student");
                    System.out.println("5. Back to Main Menu");
                    System.out.print("Enter your choice: ");
                    int studentChoice = input.nextInt();
                    input.nextLine(); // Consume the newline character

                    switch (studentChoice) {
                        case 1:
                            student.addStudent();
                            break;
                        case 2:
                            student.viewStudent();
                            break;
                        case 3:
                            student.updateStudent();
                            break;
                        case 4:
                            student.deleteStudent();
                            break;
                        case 5:
                            continue; // Go back to the main menu
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                    break;
                case 2:
                    Faculty faculty = new Faculty();
                    System.out.println("\n\t*** Faculty Account ***");
                    System.out.println("1. Add Faculty");
                    System.out.println("2. View Faculty");
                    System.out.println("3. Update Faculty");
                    System.out.println("4. Delete Faculty");
                    System.out.println("5. Back to Main Menu");
                    System.out.print("Enter your choice: ");
                    int facultyChoice = input.nextInt();
                    input.nextLine(); // Consume the newline character

                    switch (facultyChoice) {
                        case 1:
                            faculty.addFaculty();
                            break;
                        case 2:
                            faculty.viewFaculty();
                            break;
                        case 3:
                            faculty.updateFaculty();
                            break;
                        case 4:
                            faculty.deleteFaculty();
                            break;
                        case 5:
                            continue; // Go back to the main menu
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                    break;
                case 3:
                    System.out.println("Exiting program...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}

class Student extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Student> students;
    private String regNo;
    private String sClass;

    Student() {
        super();
        students = new ArrayList<>();
        regNo = null;
        sClass = null;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public void setStudentClass(String sClass) {
        this.sClass = sClass;
    }

    public void addStudent() {
        System.out.println("\t!!..Enter Student Details..!!");
        int studentId = (int) (Math.random() * 999);
        System.out.println("\nEnter your Id Number: " + studentId);
        System.out.print("Enter your Registration Number: ");
        String regNo = input.nextLine();
        System.out.print("Enter your Class/Section: ");
        String sClass = input.nextLine();
        System.out.print("Enter Your First Name: ");
        String firstName = input.nextLine();
        System.out.print("Enter Your Last Name: ");
        String lastName = input.nextLine();
        System.out.print("Enter your CNIC in (00000-0000000-0) format: ");
        String cnic = input.nextLine();
        System.out.print("Enter your phone no (without dashes): ");
        String phoneNo = input.nextLine();
        System.out.print("Enter your E-mail Address: ");
        String email = input.nextLine();
        System.out.print("Enter your Department: ");
        String department = input.nextLine();

        Student newStudent = new Student();
        newStudent.userId = studentId;
        newStudent.setRegNo(regNo);
        newStudent.setStudentClass(sClass);
        newStudent.firstName = firstName;
        newStudent.lastName = lastName;
        newStudent.userCNIC = cnic;
        newStudent.userPhoneNo = phoneNo;
        newStudent.userEMail = email;
        newStudent.userDepartment = department;
        students.add(newStudent);

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    new FileOutputStream("Studentrecord.txt", true));
            objectOutputStream.writeObject(newStudent);
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
        System.out.println("\n\t\t!!..Your Information Saved Successfully..!!");
    }

    public void deleteStudent() {
        System.out.print("Enter Student ID Number to Delete: ");
        int studentId = input.nextInt();
        input.nextLine(); // Consume the newline character

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Studentrecord.txt"));

            ArrayList<Student> studentRecords = new ArrayList<>();
            try {
                while (true) {
                    Student student = (Student) objectInputStream.readObject();
                    studentRecords.add(student);
                }
            } catch (EOFException e) {
                // Reached end of file
            }

            objectInputStream.close();

            boolean found = false;
            for (int i = 0; i < studentRecords.size(); i++) {
                if (studentRecords.get(i).userId == studentId) {
                    studentRecords.remove(i);
                    found = true;
                    break;
                }
            }

            if (found) {
                File file = new File("Studentrecord.txt");
                if (file.exists()) {
                    file.delete();
                }

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                        new FileOutputStream("Studentrecord.txt"));
                for (Student student : studentRecords) {
                    objectOutputStream.writeObject(student);
                }
                objectOutputStream.close();
                System.out.println("Student record with ID " + studentId + " has been deleted.");
            } else {
                System.out.println("No student record found with ID " + studentId + ".");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error accessing file: " + e.getMessage());
        }
    }

    public void updateStudent() {
        System.out.print("\n\t!!..Update Student Details..!!");
        System.out.print("\nEnter Student ID Number: ");
        int studentId = input.nextInt();
        input.nextLine(); // Consume the newline character

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Studentrecord.txt"));

            ArrayList<Student> studentRecords = new ArrayList<>();
            try {
                while (true) {
                    Student student = (Student) objectInputStream.readObject();
                    studentRecords.add(student);
                }
            } catch (EOFException e) {
                // Reached end of file
            }

            objectInputStream.close();

            boolean found = false;
            for (int i = 0; i < studentRecords.size(); i++) {
                if (studentRecords.get(i).userId == studentId) {
                    Student student = studentRecords.get(i);
                    found = true;

                    System.out.print("\nEnter your Registration Number: ");
                    String regNo = input.nextLine();
                    System.out.print("Enter your Class/Section: ");
                    String sClass = input.nextLine();
                    System.out.print("Enter Your First Name: ");
                    String firstName = input.nextLine();
                    System.out.print("Enter Your Last Name: ");
                    String lastName = input.nextLine();
                    System.out.print("Enter your CNIC in (00000-0000000-0) format: ");
                    String cnic = input.nextLine();
                    System.out.print("Enter your phone no (without dashes): ");
                    String phoneNo = input.nextLine();
                    System.out.print("Enter your E-mail Address: ");
                    String email = input.nextLine();
                    System.out.print("Enter your Department: ");
                    String department = input.nextLine();

                    student.setRegNo(regNo);
                    student.setStudentClass(sClass);
                    student.firstName = firstName;
                    student.lastName = lastName;
                    student.userCNIC = cnic;
                    student.userPhoneNo = phoneNo;
                    student.userEMail = email;
                    student.userDepartment = department;

                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                            new FileOutputStream("Studentrecord.txt"));
                    for (Student s : studentRecords) {
                        objectOutputStream.writeObject(s);
                    }
                    objectOutputStream.close();

                    System.out.println("\n\t\t!!..Student Information Updated Successfully..!!");
                    break;
                }
            }

            if (!found) {
                System.out.println("No student record found with ID " + studentId + ".");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error accessing file: " + e.getMessage());
        }
    }

    public void viewStudent() {
        System.out.println("\n\t!!..View Student Details..!!");
        System.out.print("Enter Student ID Number: ");
        int studentId = input.nextInt();
        input.nextLine(); // Consume the newline character

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Studentrecord.txt"));

            ArrayList<Student> studentRecords = new ArrayList<>();
            try {
                while (true) {
                    Student student = (Student) objectInputStream.readObject();
                    studentRecords.add(student);
                }
            } catch (EOFException e) {
                // Reached end of file
            }

            objectInputStream.close();

            boolean found = false;
            for (Student student : studentRecords) {
                if (student.userId == studentId) {
                    found = true;
                    System.out.println("\nStudent ID: " + student.userId);
                    System.out.println("Registration Number: " + student.regNo);
                    System.out.println("Class/Section: " + student.sClass);
                    System.out.println("First Name: " + student.firstName);
                    System.out.println("Last Name: " + student.lastName);
                    System.out.println("CNIC: " + student.userCNIC);
                    System.out.println("Phone Number: " + student.userPhoneNo);
                    System.out.println("Email Address: " + student.userEMail);
                    System.out.println("Department: " + student.userDepartment);
                    break;
                }
            }

            if (!found) {
                System.out.println("No student record found with ID " + studentId + ".");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error accessing file: " + e.getMessage());
        }
    }
}
class Faculty extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Faculty> facultys;
    private String designation;

    Faculty() {
        super();
        facultys = new ArrayList<>();
        designation = null;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }

    public void addFaculty() {
        System.out.println("\t!!..Enter Faculty Details..!!");
        int facultyId = (int) (Math.random() * 999);
        System.out.println("\nEnter your Id Number: " + facultyId);
        System.out.print("Enter Your First Name: ");
        String firstName = input.nextLine();
        System.out.print("Enter Your Last Name: ");
        String lastName = input.nextLine();
        System.out.print("Enter your Designation: ");
        String desig = input.nextLine();
        System.out.print("Enter your CNIC in (00000-0000000-0) format: ");
        String cnic = input.nextLine();
        System.out.print("Enter your phone no (without dashes): ");
        String phoneNo = input.nextLine();
        System.out.print("Enter your E-mail Address: ");
        String email = input.nextLine();
        System.out.print("Enter your Department: ");
        String department = input.nextLine();

        Faculty newFaculty = new Faculty();
        newFaculty.userId = facultyId;
        newFaculty.firstName = firstName;
        newFaculty.lastName = lastName;
        newFaculty.setDesignation(desig);
        newFaculty.userCNIC = cnic;
        newFaculty.userPhoneNo = phoneNo;
        newFaculty.userEMail = email;
        newFaculty.userDepartment = department;
        facultys.add(newFaculty);

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Facultyrecord.txt", true));
            objectOutputStream.writeObject(newFaculty);
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
        System.out.println("\n\t\t!!..Your Information Saved Successfully..!!");
    }
    public void deleteFaculty() {
        System.out.print("Enter Faculty ID Number to Delete: ");
        int facultyId = input.nextInt();
        input.nextLine(); // Consume the newline character

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Facultyrecord.txt"));

            ArrayList<Faculty> facultyRecords = new ArrayList<>();
            try {
                while (true) {
                    Faculty faculty = (Faculty) objectInputStream.readObject();
                    facultyRecords.add(faculty);
                }
            } catch (EOFException e) {
                // Reached end of file
            }

            objectInputStream.close();

            boolean found = false;
            for (int i = 0; i < facultyRecords.size(); i++) {
                if (facultyRecords.get(i).userId == facultyId) {
                    facultyRecords.remove(i);
                    found = true;
                    break;
                }
            }

            if (found) {
                File file = new File("Facultyrecord.txt");
                if (file.exists()) {
                    file.delete();
                }

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                        new FileOutputStream("Facultyrecord.txt"));
                for (Faculty faculty : facultyRecords) {
                    objectOutputStream.writeObject(faculty);
                }
                objectOutputStream.close();
                System.out.println("Faculty record with ID " + facultyId + " has been deleted.");
            } else {
                System.out.println("No faculty record found with ID " + facultyId + ".");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error accessing file: " + e.getMessage());
        }
    }

    public void updateFaculty() {
        System.out.print("\n\t!!..Update Faculty Details..!!");
        System.out.print("\nEnter Faculty ID Number: ");
        int facultyId = input.nextInt();
        input.nextLine(); // Consume the newline character

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Facultyrecord.txt"));

            ArrayList<Faculty> facultyRecords = new ArrayList<>();
            try {
                while (true) {
                    Faculty faculty = (Faculty) objectInputStream.readObject();
                    facultyRecords.add(faculty);
                }
            } catch (EOFException e) {
                // Reached end of file
            }

            objectInputStream.close();

            boolean found = false;
            for (int i = 0; i < facultyRecords.size(); i++) {
                if (facultyRecords.get(i).userId == facultyId) {
                    Faculty faculty = facultyRecords.get(i);
                    found = true;

                    System.out.print("\nEnter Your First Name: ");
                    String firstName = input.nextLine();
                    System.out.print("Enter Your Last Name: ");
                    String lastName = input.nextLine();
                    System.out.print("Enter your Designation: ");
                    String desig = input.nextLine();
                    System.out.print("Enter your CNIC in (00000-0000000-0) format: ");
                    String cnic = input.nextLine();
                    System.out.print("Enter your phone no (without dashes): ");
                    String phoneNo = input.nextLine();
                    System.out.print("Enter your E-mail Address: ");
                    String email = input.nextLine();
                    System.out.print("Enter your Department: ");
                    String department = input.nextLine();

                    faculty.firstName = firstName;
                    faculty.lastName = lastName;
                    faculty.setDesignation(desig);
                    faculty.userCNIC = cnic;
                    faculty.userPhoneNo = phoneNo;
                    faculty.userEMail = email;
                    faculty.userDepartment = department;

                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                            new FileOutputStream("Facultyrecord.txt"));
                    for (Faculty f : facultyRecords) {
                        objectOutputStream.writeObject(f);
                    }
                    objectOutputStream.close();

                    System.out.println("\n\t\t!!..Faculty Information Updated Successfully..!!");
                    break;
                }
            }

            if (!found) {
                System.out.println("No faculty record found with ID " + facultyId + ".");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error accessing file: " + e.getMessage());
        }
    }

    public void viewFaculty() {
        System.out.println("\n\t!!..View Faculty Details..!!");
        System.out.print("Enter Faculty ID Number: ");
        int facultyId = input.nextInt();
        input.nextLine(); // Consume the newline character

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Facultyrecord.txt"));

            ArrayList<Faculty> facultyRecords = new ArrayList<>();
            try {
                while (true) {
                    Faculty faculty = (Faculty) objectInputStream.readObject();
                    facultyRecords.add(faculty);
                }
            } catch (EOFException e) {
                // Reached end of file
            }

            objectInputStream.close();

            boolean found = false;
            for (Faculty faculty : facultyRecords) {
                if (faculty.userId == facultyId) {
                    found = true;
                    System.out.println("\nFaculty ID: " + faculty.userId);
                    System.out.println("First Name: " + faculty.firstName);
                    System.out.println("Last Name: " + faculty.lastName);
                    System.out.println("Designation: " + faculty.getDesignation());
                    System.out.println("CNIC: " + faculty.userCNIC);
                    System.out.println("Phone Number: " + faculty.userPhoneNo);
                    System.out.println("Email Address: " + faculty.userEMail);
                    System.out.println("Department: " + faculty.userDepartment);
                    break;
                }
            }

            if (!found) {
                System.out.println("No faculty record found with ID " + facultyId + ".");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error accessing file: " + e.getMessage());
        }
    }
}
