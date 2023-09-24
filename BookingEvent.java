
package project.oop;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class BookingEvent implements Serializable
{
    private ArrayList<BookingEvent> bookings;
    private Scanner input;
    private String date;
    private String time;
    private String eventType;
    private int userId;
    private Room roomNo;
    private int attendees;
    
    public BookingEvent() {
        bookings = new ArrayList<>();
        input = new Scanner(System.in);
        date = null;
        time = null;
        eventType = null;
        userId = 0;
        roomNo = null;
        attendees = 0;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setTime(String time) {
        this.time = time;
    }
    
    public String getTime() {
        return time;
    }
    
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    
    public String getEventType() {
        return eventType;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setRoomNo(Room roomNo) {
        this.roomNo = roomNo;
    }
    
    public Room getRoomNo() {
        return roomNo;
    }
    
    public void setAttendees(int attendees) {
        this.attendees = attendees;
    }
    
    public int getAttendees() {
        return attendees;
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to the Smart Room Booking Event System!");

        // Create an instance of the BookingEvent class
        BookingEvent bookingEvent = new BookingEvent();

        // Create a menu for user interaction
        int choice;
        do {
            System.out.println("\n----- Menu -----");
            System.out.println("1. Create Booking");
            System.out.println("2. Cancel Booking");
            System.out.println("3. update Booking");
            System.out.println("4. View Booking");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine(); // Consume the remaining newline character

            switch (choice) {
                case 1:
                    bookingEvent.createBooking();
                    break;
                case 2:
                    bookingEvent.cancelBooking();
                    break;  
                case 3:
                    bookingEvent.updateBooking();
                    break; 
                case 4:
                    bookingEvent.viewBooking();
                    break;     
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 2);
        
        input.close();
    }
    public void createBooking() 
    {
        System.out.println("\t!!..Enter Booking Details..!!");
        System.out.print("Enter Your Id Number: ");
        int id = input.nextInt();
        input.nextLine(); // Consume the remaining newline character
        
        System.out.print("Enter The Date of Booking: ");
        String date = input.nextLine();
        
        System.out.print("Enter The Time of Booking: ");
        String time = input.nextLine();
        
        System.out.print("Enter The Event Type of Booking: ");
        String eventType = input.nextLine();
        
        System.out.print("Enter The Number of Attendees: ");
        int attendees = input.nextInt();
        
        // Check for available rooms that match the specified date, time, and capacity
        Admin admin = new Admin();
        admin.searchRoom(attendees);
        System.out.print("Enter the Room Number for your booking: ");
        int roomNumber = input.nextInt();
        // Find the selected room from the available rooms
        Room selectedRoom = admin.findRoomByNumber(roomNumber);
        if (selectedRoom == null) {
            System.out.println("Invalid room number. Booking creation failed.");
        } 
        else 
        {
        // Create the booking
        BookingEvent booking = new BookingEvent();
        booking.setDate(date);
        booking.setTime(time);
        booking.setEventType(eventType);
        booking.setUserId(id);
        booking.setRoomNo(selectedRoom);
        booking.setAttendees(attendees);
        // Store the booking in the system (e.g., add it to the ArrayList)
        bookings.add(booking);
        try 
        {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Bookingrecord.txt", true));
            objectOutputStream.writeObject(booking);
            objectOutputStream.close();
        } 
        catch (IOException e) 
        {
            System.out.println("Error writing to file: " + e.getMessage());
        }
        System.out.println("Booking created and added to the system.");
        }
    }
    public void cancelBooking() {
        System.out.println("\t!!..Cancel Booking..!!");
        System.out.print("Enter the Room Number: ");
        int roomNumber = input.nextInt();
        input.nextLine(); // Consume the remaining newline character

        System.out.print("Enter the Date of Booking: ");
        String date = input.nextLine();

        System.out.print("Enter the Time of Booking: ");
        String time = input.nextLine();

        // Check if the booking exists in the record
        boolean found = false;
        Iterator<BookingEvent> iterator = bookings.iterator();
        while (iterator.hasNext()) {
            BookingEvent booking = iterator.next();
            if (booking.getRoomNo().getRoomNo() == roomNumber && booking.getDate().equals(date) && booking.getTime().equals(time)) {
                iterator.remove();
                found = true;
                break;
            }
        }

        if (found) {
            // Remove the booking from the file
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Bookingrecord.txt"));
                for (BookingEvent booking : bookings) {
                    objectOutputStream.writeObject(booking);
                }
                objectOutputStream.close();
                System.out.println("Booking canceled successfully.");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        } else {
            System.out.println("Booking not found. Cancellation failed.");
        }
    }
    public void updateBooking() {
        System.out.println("\t!!..Update Booking..!!");
        System.out.print("Enter Your Id Number: ");
        int id = input.nextInt();
        input.nextLine(); // Consume the remaining newline character

        // Find the booking with the specified id
        BookingEvent bookingToUpdate = null;
        for (BookingEvent booking : bookings) {
            if (booking.getUserId() == id) {
                bookingToUpdate = booking;
                break;
            }
        }

        if (bookingToUpdate != null) {
            System.out.print("Enter The New Date of Booking: ");
            String newDate = input.nextLine();

            System.out.print("Enter The New Time of Booking: ");
            String newTime = input.nextLine();

            System.out.print("Enter The New Room Number for the Booking: ");
            int newRoomNumber = input.nextInt();
            input.nextLine(); // Consume the remaining newline character

            // Check if the new room number exists in the room record file
            Admin admin = new Admin();
            Room newRoom = admin.findRoomByNumber(newRoomNumber);
            if (newRoom == null) {
                System.out.println("Invalid room number. Booking update failed.");
                return;
            }

            System.out.print("Enter The New Number of Attendees: ");
            int newAttendees = input.nextInt();
            input.nextLine(); // Consume the remaining newline character

            System.out.print("Enter The New Event Type of Booking: ");
            String newEventType = input.nextLine();

            // Update the booking information
            bookingToUpdate.setDate(newDate);
            bookingToUpdate.setTime(newTime);
            bookingToUpdate.setRoomNo(newRoom);
            bookingToUpdate.setAttendees(newAttendees);
            bookingToUpdate.setEventType(newEventType);

            // Update the booking in the file
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Bookingrecord.txt"));
                for (BookingEvent booking : bookings) {
                    objectOutputStream.writeObject(booking);
                }
                objectOutputStream.close();
                System.out.println("Booking updated successfully.");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        } else {
            System.out.println("Booking With your Id is not found. Update failed.");
        }
    }
    public void viewBooking() 
    {
        System.out.println("\t!!..View Booking..!!");
        System.out.print("Enter Your Id Number: ");
        int id = input.nextInt();
        input.nextLine(); // Consume the remaining newline character

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Bookingrecord.txt"))) {
            BookingEvent bookingToView;
            while (true) {
                bookingToView = (BookingEvent) objectInputStream.readObject();
                if (bookingToView.getUserId() == id) {
                    System.out.println("Booking Details:");
                    System.out.println("User Id: " + bookingToView.getUserId());
                    System.out.println("Date: " + bookingToView.getDate());
                    System.out.println("Time: " + bookingToView.getTime());
                    System.out.println("Room Number: " + bookingToView.getRoomNo().getRoomNo());
                    System.out.println("Attendees: " + bookingToView.getAttendees());
                    System.out.println("Event Type: " + bookingToView.getEventType());
                    return;
                }
            }
        } catch (EOFException e) {
            System.out.println("Booking not found.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }
}    