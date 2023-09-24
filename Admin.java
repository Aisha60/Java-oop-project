
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Admin implements Serializable
{
    private ArrayList<Room> rooms;
    private String username;
    private String password;
    private Scanner input;

    public Admin() {
        username = "Admin";
        password = "admin1234";
        rooms = new ArrayList<Room>();
        input = new Scanner(System.in);
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public static void main(String[] args) {
        Admin admin = new Admin();
        Scanner input = new Scanner(System.in);
        boolean loggedIn = false;

        while (true) {
            if (!loggedIn) {
                System.out.println("\n\t\t *** LOGIN MENU ***");
                System.out.print("Enter Username: ");
                String username = input.nextLine();
                System.out.print("Enter Password: ");
                String password = input.nextLine();

                if (username.equals(admin.getUsername()) && password.equals(admin.getPassword())) {
                    System.out.println("Login Successful!");
                    loggedIn = true;
                } else {
                    System.out.println("Invalid username or password. Please try again.");
                }
            } else {
                System.out.println("\n\t *** ADMIN MENU ***");
                System.out.println("1. Add Room");
                System.out.println("2. View Rooms");
                System.out.println("3. Update Room");
                System.out.println("4. Delete Room");
                System.out.println("5. Logout");

                System.out.print("Enter your choice: ");
                int choice = input.nextInt();
                input.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        admin.addRoom();
                        break;
                    case 2:
                        admin.viewRooms();
                        break;
                    case 3:
                        admin.updateRoom();
                        break;
                    case 4:
                        admin.deleteRoom();
                        break;
                    case 5:
                        loggedIn = false;
                        System.out.println("\t!!..Thank You For Being Here..!!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        }
    }
    public void login() {
        System.out.println("\n\t\t !! Admin Account Login !! ");
        System.out.print("\nEnter User Name : ");
        String User_name = input.nextLine();
        System.out.print("Enter Password : ");
        String pass = input.nextLine();

        User_name = User_name.trim();
        pass = pass.trim();

        while (true) {
            if (User_name.equals(username) && pass.equals(password)) {
                System.out.println("Login successful!");
                break;
            } else {
                if (!User_name.equals(username)) {
                    System.out.println("Username is incorrect!..enter username again:");
                    User_name = input.nextLine();
                    User_name = User_name.trim();
                }

                if (!pass.equals(password)) {
                    System.out.println("Password is incorrect!..enter password again:");
                    pass = input.nextLine();
                    pass = pass.trim();
                }
            }
        }
    }

    public void logout() {
        System.out.println("\t!!..Thank You For Being Here..!!");
        System.exit(0);
    }

    public void addRoom() {
        System.out.println("\t!!..Enter Room Details..!!");
        System.out.print("Enter Room No: ");
        int rNo = input.nextInt();
        input.nextLine(); // Consume the newline character

        boolean matchFound = true;
        while (matchFound) {
            matchFound = false;
            for (Room room : rooms) {
                if (room.getRoomNo() == rNo) {
                    System.out.println("This room number is already taken...");
                    System.out.println("Enter new room no:");
                    rNo = input.nextInt();
                    input.nextLine(); // Consume the newline character
                    matchFound = true;
                    break;
                }
            }
        }

        System.out.print("Enter Room Capacity: ");
        int capacity = input.nextInt();
        input.nextLine(); // Consume the newline character

        System.out.print("Enter Room Type (Classroom, lab, seminarHall): ");
        String type = input.nextLine();

        System.out.print("Enter department of new Room: ");
        String department = input.nextLine();

        Room newRoom = new Room();
        newRoom.setRoomNo(rNo);
        newRoom.setCapacity(capacity);
        newRoom.setType(type);
        newRoom.setDepartment(department);

        rooms.add(newRoom);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Roomrecord.txt", true));
            objectOutputStream.writeObject(newRoom);
            objectOutputStream.close();
        } 
        catch (IOException e) 
        {
            System.out.println("Error writing to file: " + e.getMessage());
        }
        System.out.println("Room added Successfully!");
    }

    public void deleteRoom() {
    System.out.println("Enter Room Number to Delete:");
    int roomNo = input.nextInt();
    input.nextLine(); // Consume the newline character

    try {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Roomrecord.txt"));

        ArrayList<Room> roomRecords = new ArrayList<>();
        try {
            while (true) {
                Room room = (Room) objectInputStream.readObject();
                roomRecords.add(room);
            }
        } catch (EOFException e) {
            // Reached end of file
        }
        objectInputStream.close();

        boolean found = false;
        for (int i = 0; i < roomRecords.size(); i++) {
            if (roomRecords.get(i).getRoomNo() == roomNo) {
                roomRecords.remove(i);
                found = true;
                break;
            }
        }

        if (found) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Roomrecord.txt"));
            for (Room room : roomRecords) {
                objectOutputStream.writeObject(room);
            }
            objectOutputStream.close();
            System.out.println("Room deleted successfully!");
        } else {
            System.out.println("Room not found!");
        }
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error accessing file: " + e.getMessage());
    }
}

    public void updateRoom() {
    System.out.println("Enter Room Number to Update:");
    int roomNo = input.nextInt();
    input.nextLine(); // Consume the newline character
    boolean roomFound = false;

    try {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Roomrecord.txt"));

        ArrayList<Room> roomRecords = new ArrayList<>();
        try {
            while (true) {
                Room room = (Room) objectInputStream.readObject();
                roomRecords.add(room);
            }
        } catch (EOFException e) {
            // Reached end of file
        }
        objectInputStream.close();

        for (Room room : roomRecords) {
            if (room.getRoomNo() == roomNo) {
                System.out.println("Enter new room capacity: ");
                int newCapacity = input.nextInt();
                input.nextLine();
                room.setCapacity(newCapacity);

                System.out.println("Enter new room Type: ");
                String newType = input.nextLine();
                room.setType(newType);

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Roomrecord.txt"));
                for (Room updatedRoom : roomRecords) {
                    objectOutputStream.writeObject(updatedRoom);
                }
                objectOutputStream.close();

                System.out.println("Room Information updated successfully!");
                roomFound = true;
                break;
            }
        }
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error updating room information: " + e.getMessage());
    }

    if (!roomFound) {
        System.out.println("Sorry...Room not Found!");
    }
}

    public void viewRooms() {
    System.out.println("Enter Room Number to View:");
    int roomNo = input.nextInt();
    input.nextLine(); // Consume the newline character
    boolean roomFound = false;

    try {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Roomrecord.txt"));

        ArrayList<Room> roomRecords = new ArrayList<>();
        try {
            while (true) {
                Room room = (Room) objectInputStream.readObject();
                roomRecords.add(room);
            }
        } catch (EOFException e) {
            // Reached end of file
        }
        objectInputStream.close();

        for (Room room : roomRecords) {
            if (room.getRoomNo() == roomNo) {
                System.out.println("Room Details:");
                System.out.println("-----------------------");
                System.out.println("Room No: " + room.getRoomNo());
                System.out.println("Capacity: " + room.getCapacity());
                System.out.println("Type: " + room.getType());
                System.out.println("Department: " + room.getDepartment());
                System.out.println("-----------------------");
                roomFound = true;
                break;
            }
        }
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error viewing room information: " + e.getMessage());
    }

    if (!roomFound) {
        System.out.println("Sorry...Room not Found!");
    }
}   
    public void searchRoom(int attendees) {
    boolean roomFound = false;

    try {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Roomrecord.txt"));

        ArrayList<Room> roomRecords = new ArrayList<>();
        try {
            while (true) {
                Room room = (Room) objectInputStream.readObject();
                roomRecords.add(room);
            }
        } catch (EOFException e) {
            // Reached end of file
        }
        objectInputStream.close();

        for (Room room : roomRecords) {
            if (room.getCapacity() >= attendees) {
                System.out.println("Available Rooms:");
                System.out.println("-----------------------");
                System.out.println("Room No: " + room.getRoomNo());
                System.out.println("Capacity: " + room.getCapacity());
                System.out.println("Type: " + room.getType());
                System.out.println("Department: " + room.getDepartment());
                System.out.println("-----------------------");
                roomFound = true;
            }
        }
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error searching room: " + e.getMessage());
    }

    if (!roomFound) {
        System.out.println("No available rooms found for the specified date, time, and capacity.");
    }
}
    public Room findRoomByNumber(int roomNumber) {
    try {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Roomrecord.txt"));

        ArrayList<Room> roomRecords = new ArrayList<>();
        try {
            while (true) {
                Room room = (Room) objectInputStream.readObject();
                roomRecords.add(room);
            }
        } catch (EOFException e) {
            // Reached end of file
        }
        objectInputStream.close();

        for (Room room : roomRecords) {
            if (room.getRoomNo() == roomNumber) {
                return room;
            }
        }
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error accessing file: " + e.getMessage());
    }

    return null; // Room not found
}
}
