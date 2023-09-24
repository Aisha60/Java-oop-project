package project.oop;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class admin_userLogin extends JFrame {
    JTextField t1, t2;
    JLabel l1, l2;
    JButton loginButton, signUpButton;
    boolean isStudent;
    boolean isFaculty;
    admin_userLogin(boolean isAdmin,boolean isStudent, boolean isFaculty) {
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        l1 = new JLabel("Login");
        l1.setFont(new Font("Times New Roman", Font.BOLD, 30));
        l1.setForeground(Color.GRAY);
        l1.setBounds(130, 10, 300, 40);
        add(l1);
        t1 = new JTextField(60);
        t2 = new JPasswordField(60);

        t1.setBounds(100, 60, 120, 30);
        t2.setBounds(100, 100, 120, 30);

        loginButton = new JButton("LOGIN");
        loginButton.setBounds(120, 140, 80, 30);
        loginButton.setBackground(Color.CYAN);
        add(t1);
        add(t2);
        add(loginButton);
        if (isStudent) {
            signUpButton = new JButton("SIGN UP");
            signUpButton.setBounds(120, 180, 80, 30);
            signUpButton.setBackground(Color.CYAN);
            add(signUpButton);
            signUpButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    StudentRegistrationWindow s = new StudentRegistrationWindow();
                    s.setSize(300, 250);
                    s.setVisible(true);
                    
                }
            });

        }
        if (isFaculty) {
            signUpButton = new JButton("SIGN UP");
            signUpButton.setBounds(120, 180, 80, 30);
            signUpButton.setBackground(Color.CYAN);
            add(signUpButton);
            signUpButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    FacultyRegistrationWindow f = new FacultyRegistrationWindow();
                    f.setSize(300, 250);
                    f.setVisible(true);
                    
                }
            });

        }


        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                boolean loginSuccessful = false;
               

                if (isAdmin) {
                    if (t1.getText().equals("Admin") && t2.getText().equals("123")) {
                        JOptionPane.showMessageDialog(null, "Admin Login successful!");
                        loginSuccessful = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Admin Login not successful!");
                    }
                } else {
                    String username = t1.getText();
                    String password = t2.getText();
                    if(isStudent){
                    if (studentcheckUserLogin(username, password,isStudent)) {
                        JOptionPane.showMessageDialog(null, "User Login successful!");
                        loginSuccessful = true;
                    } 
                    else if(isFaculty){
                        if (facultycheckUserLogin(username, password,isFaculty)) {
                            JOptionPane.showMessageDialog(null, "User Login successful!");
                            loginSuccessful = true;
                        } 
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
                    }
                }}

                if (loginSuccessful) {
                    setVisible(false);
                    dispose();
                }
            }
        });
        

        // if (isUser) {
        //     signUpButton.addActionListener(new ActionListener() {
        //         public void actionPerformed(ActionEvent ae) {
        //             SignUpWindow signUpWindow = new SignUpWindow();
        //             signUpWindow.setSize(300, 500);
        //             signUpWindow.setVisible(true);
        //         }
        //     });
        }
    public boolean studentcheckUserLogin(String username, String password,boolean isStudent) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("StudentAccount.txt"));
            Student student;

            while ((student = (Student) objectInputStream.readObject()) != null) {
                if (student.username.equals(username) && student.password.equals(password)) {
                    objectInputStream.close();
                    return true;
                }
            }

            objectInputStream.close();
        } catch (EOFException e) {
            // Reached the end of the file
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean facultycheckUserLogin(String username, String password,boolean isFaculty) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("FacultyAccount.txt"));
            Student student;

            while ((student = (Student) objectInputStream.readObject()) != null) {
                if (student.username.equals(username) && student.password.equals(password)) {
                    objectInputStream.close();
                    return true;
                }
            }

            objectInputStream.close();
        } catch (EOFException e) {
            // Reached the end of the file
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }
    public static class FirstWindow extends JFrame {
        JButton adminButton, studentButton,facultyButton;
        JLabel loginLabel;

        FirstWindow() {
            setLayout(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            adminButton = new JButton("Admin");
            studentButton = new JButton("Student");
            facultyButton = new JButton("Faculty");
            loginLabel = new JLabel("LOGIN/SIGNUP");
            loginLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
            loginLabel.setForeground(Color.BLACK);
            studentButton.setBackground(Color.CYAN);
            facultyButton.setBackground(Color.CYAN);
            adminButton.setBackground(Color.CYAN);
            loginLabel.setBounds(90, 10, 200, 30);
            adminButton.setBounds(100, 50, 110, 30);
            studentButton.setBounds(100, 100, 110, 30);
            facultyButton.setBounds(100, 150, 110, 30);
            add(adminButton);
            add(studentButton);
            add(facultyButton);
            add(loginLabel);
            adminButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    //boolean isAdmin=true;
                    admin_userLogin adminLoginWindow = new admin_userLogin(true,false,false);
                    adminLoginWindow.setSize(300, 250);
                    adminLoginWindow.setVisible(true);
                }
            });
            facultyButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                //public static boolean isFaculty=true;
                    admin_userLogin facultyLoginWindow = new admin_userLogin(false,false,true);
                    facultyLoginWindow.setSize(300, 250);
                    facultyLoginWindow.setVisible(true);
                }
            });
            studentButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                  //  public static boolean isStudent=true;
                    admin_userLogin studentLoginWindow = new admin_userLogin(false,true,false);
                    studentLoginWindow.setSize(300, 250);
                    studentLoginWindow.setVisible(true);
                    
                }
            });
        }
    }

    public static class StudentRegistrationWindow extends JFrame {
        JLabel registerLabel, usernameLabel, passwordLabel, userIDLabel, firstNameLabel, lastNameLabel, cnicLabel, phoneNoLabel, emailLabel, departmentLabel;
        JTextField usernameField, userIDField, firstNameField, lastNameField, cnicField, phoneNoField, emailField, departmentField;
        JPasswordField passwordField;
        JButton submitButton;

        StudentRegistrationWindow() {
            setLayout(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            registerLabel = new JLabel("STUDENT REGISTRATION");
            registerLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
            registerLabel.setForeground(Color.BLACK);
            registerLabel.setBounds(90, 10, 200, 30);
            add(registerLabel);

            usernameLabel = new JLabel("Username:");
            usernameLabel.setBounds(20, 50, 80, 30);
            add(usernameLabel);

            passwordLabel = new JLabel("Password:");
            passwordLabel.setBounds(20, 90, 80, 30);
            add(passwordLabel);

            userIDLabel = new JLabel("UserID:");
            userIDLabel.setBounds(20, 130, 80, 30);
            add(userIDLabel);

            firstNameLabel = new JLabel("First Name:");
            firstNameLabel.setBounds(20, 170, 80, 30);
            add(firstNameLabel);
            lastNameLabel = new JLabel("Last Name:");
            lastNameLabel.setBounds(20, 210, 80, 30);
            add(lastNameLabel);

            cnicLabel = new JLabel("CNIC:");
            cnicLabel.setBounds(20, 250, 80, 30);
            add(cnicLabel);

            phoneNoLabel = new JLabel("Phone No:");
            phoneNoLabel.setBounds(20, 290, 80, 30);
            add(phoneNoLabel);

            emailLabel = new JLabel("Email:");
            emailLabel.setBounds(20, 330, 80, 30);
            add(emailLabel);

            departmentLabel = new JLabel("Department:");
            departmentLabel.setBounds(20, 370, 80, 30);
            add(departmentLabel);

            usernameField = new JTextField(60);
            usernameField.setBounds(100, 50, 120, 30);
            add(usernameField);

            passwordField = new JPasswordField(60);
            passwordField.setBounds(100, 90, 120, 30);
            add(passwordField);
            userIDField = new JTextField(60);
            userIDField.setBounds(100, 130, 120, 30);
            add(userIDField);

            firstNameField = new JTextField(60);
            firstNameField.setBounds(100, 170, 120, 30);
            add(firstNameField);

            lastNameField = new JTextField(60);
            lastNameField.setBounds(100, 210, 120, 30);
            add(lastNameField);

            cnicField = new JTextField(60);
            cnicField.setBounds(100, 250, 120, 30);
            add(cnicField);

            phoneNoField = new JTextField(60);
            phoneNoField.setBounds(100, 290, 120, 30);
            add(phoneNoField);

            emailField = new JTextField(60);
            emailField.setBounds(100, 330, 120, 30);
            add(emailField);

            departmentField = new JTextField(60);
            departmentField.setBounds(100, 370, 120, 30);
            add(departmentField);

            submitButton = new JButton("SUBMIT");
            submitButton.setBounds(120, 410, 80, 30);
            submitButton.setBackground(Color.CYAN);
            add(submitButton);

            submitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    String username = usernameField.getText();
                    String password = passwordField.getText();
                    String userID = userIDField.getText();
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String cnic = cnicField.getText();
                    String phoneNo = phoneNoField.getText();
                    String email = emailField.getText();
                    String department = departmentField.getText();

                    try {
                        FileWriter fileWriter = new FileWriter("StudentAccount.txt", true);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                        bufferedWriter.write("Username: " + username);
                        bufferedWriter.newLine();
                        bufferedWriter.write("Password: " + password);
                        bufferedWriter.newLine();
                        bufferedWriter.write("UserID: " + userID);
                        bufferedWriter.newLine();
                        bufferedWriter.write("First Name: " + firstName);
                        bufferedWriter.newLine();
                        bufferedWriter.write("Last Name: " + lastName);
                        bufferedWriter.newLine();
                        bufferedWriter.write("CNIC: " + cnic);
                        bufferedWriter.newLine();
                        bufferedWriter.write("Phone No: " + phoneNo);
                        bufferedWriter.newLine();
                        bufferedWriter.write("Email: " + email);
                        bufferedWriter.newLine();
                        bufferedWriter.write("Department: " + department);
                        bufferedWriter.newLine();
                        bufferedWriter.newLine();

                        bufferedWriter.close();
                        fileWriter.close();

                        JOptionPane.showMessageDialog(null, "Student registration successful!");

                        usernameField.setText("");
                        passwordField.setText("");
                        userIDField.setText("");
                        firstNameField.setText("");
                        lastNameField.setText("");
                        cnicField.setText("");
                        phoneNoField.setText("");
                        emailField.setText("");
                        departmentField.setText("");
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "An error occurred. Please try again.");
                    }
                }
            });
        }
    }
    public static class FacultyRegistrationWindow extends JFrame {
        JLabel registerLabel, usernameLabel, DesignationLabel,passwordLabel, userIDLabel, firstNameLabel, lastNameLabel, cnicLabel, phoneNoLabel, emailLabel, departmentLabel;
        JTextField usernameField, userIDField, firstNameField,Designationfeild, lastNameField, cnicField, phoneNoField, emailField, departmentField;
        JPasswordField passwordField;
        JButton submitButton;
    
        FacultyRegistrationWindow() {
            setLayout(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
    
            registerLabel = new JLabel("FACULTY REGISTRATION");
            registerLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
            registerLabel.setForeground(Color.BLACK);
            registerLabel.setBounds(90, 10, 200, 30);
            add(registerLabel);
    
            usernameLabel = new JLabel("Username:");
            usernameLabel.setBounds(20, 50, 80, 30);
            add(usernameLabel);
    
            passwordLabel = new JLabel("Password:");
            passwordLabel.setBounds(20, 90, 80, 30);
            add(passwordLabel);
            DesignationLabel = new JLabel("Designation:");
            DesignationLabel.setBounds(20, 130, 80, 30);
        add(DesignationLabel);
    
    
            userIDLabel = new JLabel("UserID:");
            userIDLabel.setBounds(20, 170, 80, 30);
            add(userIDLabel);
    
            firstNameLabel = new JLabel("First Name:");
            firstNameLabel.setBounds(20, 210, 80, 30);
            add(firstNameLabel);
    
            lastNameLabel = new JLabel("Last Name:");
            lastNameLabel.setBounds(20, 250, 80, 30);
            add(lastNameLabel);
    
            cnicLabel = new JLabel("CNIC:");
            cnicLabel.setBounds(20, 290, 80, 30);
            add(cnicLabel);
    
            phoneNoLabel = new JLabel("Phone No:");
            phoneNoLabel.setBounds(20, 330, 80, 30);
            add(phoneNoLabel);
    
            emailLabel = new JLabel("Email:");
            emailLabel.setBounds(20, 370, 80, 30);
            add(emailLabel);
    
            departmentLabel = new JLabel("Department:");
            departmentLabel.setBounds(20, 410, 80, 30);
            add(departmentLabel);
    
            usernameField = new JTextField(60);
            usernameField.setBounds(100, 50, 120, 30);
            add(usernameField);
    
            passwordField = new JPasswordField(60);
            passwordField.setBounds(100, 90, 120, 30);
            add(passwordField);
    
            emailField = new JTextField(60);
            emailField.setBounds(100, 130, 120, 30);
            add(emailField);
            userIDField = new JTextField(60);
            userIDField.setBounds(100, 170, 120, 30);
            add(userIDField);
    
            firstNameField = new JTextField(60);
            firstNameField.setBounds(100, 210, 120, 30);
            add(firstNameField);
    
            lastNameField = new JTextField(60);
            lastNameField.setBounds(100, 250, 120, 30);
            add(lastNameField);
    
            cnicField = new JTextField(60);
            cnicField.setBounds(100, 290, 120, 30);
            add(cnicField);
    
            phoneNoField = new JTextField(60);
            phoneNoField.setBounds(100, 330, 120, 30);
            add(phoneNoField);
    
            emailField = new JTextField(60);
            emailField.setBounds(100, 370, 120, 30);
            add(emailField);
    
            departmentField = new JTextField(60);
            departmentField.setBounds(100, 410, 120, 30);
            add(departmentField);
    
            submitButton = new JButton("SUBMIT");
            submitButton.setBounds(120, 450, 80, 30);
            submitButton.setBackground(Color.CYAN);
            add(submitButton);
    
            submitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    String username = usernameField.getText();
                    String password = passwordField.getText();
                    String userID = userIDField.getText();
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String designation = Designationfeild.getText();
                    String cnic = cnicField.getText();
                    String phoneNo = phoneNoField.getText();
                    String email = emailField.getText();
                    String department = departmentField.getText();
    
                    try {
                        FileWriter fileWriter = new FileWriter("FacultyAccount.txt", true);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
    
                        bufferedWriter.write("Username: " + username);
                        bufferedWriter.newLine();
                        bufferedWriter.write("Password: " + password);
                        bufferedWriter.newLine();
                        bufferedWriter.write("UserID: " + userID);
                        bufferedWriter.newLine();
                        bufferedWriter.write("First Name: " + firstName);
                        bufferedWriter.newLine();
                        bufferedWriter.write("Last Name: " + lastName);
                        bufferedWriter.newLine();
                        bufferedWriter.write("Designation: " + designation);
                        bufferedWriter.newLine();
                        bufferedWriter.write("CNIC: " + cnic);
                        bufferedWriter.newLine();
                        bufferedWriter.write("Phone No: " + phoneNo);
                        bufferedWriter.newLine();
                        bufferedWriter.write("Email: " + email);
                        bufferedWriter.newLine();
                        bufferedWriter.write("Department: " + department);
                        bufferedWriter.newLine();
                        bufferedWriter.newLine();
    
                        bufferedWriter.close();
                        fileWriter.close();
    
                        JOptionPane.showMessageDialog(null, "Faculty registration successful!");
    
                        usernameField.setText("");
                        passwordField.setText("");
                        userIDField.setText("");
                        firstNameField.setText("");
                        lastNameField.setText("");
                        Designationfeild.setText("");
                        cnicField.setText("");
                        phoneNoField.setText("");
                        emailField.setText("");
                        departmentField.setText("");
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "An error occurred. Please try again.");
                    }
                }
            });
        }
    }
                        

    public void callGui() {
        FirstWindow firstWindow = new FirstWindow();
        firstWindow.setSize(300, 200);
        firstWindow.setVisible(true);
    }}