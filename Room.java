package project.oop;
import java.io.*;
import java.util.Scanner;

public class Room  
{
    private int roomNo;
    private int capacity;
    private String type;
    private String department;
    public Room()
    {
        roomNo = 000;
        capacity = 00;
        type = null;
        department = null;
    }
    public void setRoomNo(int roomNo)
    {
        this.roomNo = roomNo;
    }
    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public void setDepartment(String department)
    {
        this.department = department;
    }
    public int getRoomNo()
    {
        return roomNo;
    }
    public int getCapacity()
    {
        return capacity;
    }
    public String getType()
    {
        return type;
    }
    public String getDepartment()
    {
        return department;
    }
    public void displayRoom()
    {
        System.out.println("Room No: " + roomNo + "\nRoom Capacity: " + capacity + "\nRoom Type: " + type + "\nRoom location: " + department);
    }
}
