
package project.oop;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class EMS extends JFrame {
   
  public static void main(String[] args) 
 {
     Scanner scanner = new Scanner(System.in);
   Admin ad = new Admin();
  System.out.println("-------------------- EVENT MANAGEMENT SYSTEM -----------------");
 System.out.println("-----------------------------Admin:-------------------------");
 System.out.println("_____________________________________________________________");
   admin_userLogin l= new admin_userLogin(true,true,true);
   int choice;
   do {
          System.out.println("1. Login/Signup:");
          System.out.println("2. Add Room:");
          System.out.println("3. View Rooms:");
           System.out.println("4. Update Room:");
        System.out.println("5. Delete Room:");
         System.out.println("0. Exit");
         System.out.print("Enter your choice: ");
        choice = scanner.nextInt();
        switch (choice) {
                case 1:
                    l.callGui();
                    EMS.main(args);
                case 2:
                    ad.addRoom();
                    break;
                case 3:
                    ad.viewRooms();
                    break;
                case 4:
                    ad.updateRoom();
                  break;
               case 5:
                   ad.deleteRoom();
                break;
              case 0:
                    System.out.println("Thanks for using our System");
                  System.out.println("Exiting...");
                    //ad.logout();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    //break;
            }
            System.out.println("--------------------------");
        } while (choice != 0);
    }
}