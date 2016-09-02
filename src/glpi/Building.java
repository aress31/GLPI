/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glpi;

import java.util.ArrayList;

public class Building implements java.io.Serializable {

    private String name;
    private int capacity;
    private ArrayList<Room> rooms;
    
    /** Constructor **/
     public Building(String name, int capacity, ArrayList<Room> rooms) {
        this.name = name;
        this.capacity = capacity;
        this.rooms = new ArrayList<>();
    }
     
    /** Getters **/
    public String getName() {
        return name;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public int getCapacity() {
        return capacity;
    }
    
    /** Setters **/
    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }
   
    //Add a Room 
    public void addRoom (Room room) {
        this.rooms.add(room);
    }
}
