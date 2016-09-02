/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glpi;

import java.util.ArrayList;

public class Room implements java.io.Serializable{
    
    private String name;
    private int capacity;
    public ArrayList<Computer> computers;
   
    /** Constructor **/
    public Room(String name, int capacity, ArrayList<Computer> computers) {
        this.name = name;
        this.capacity = capacity;
        this.computers = new ArrayList<>();
    }
    
    /** Getters **/
    public int getCapacity() {
        return capacity;
    }

    public ArrayList<Computer> getComputers() {
        return computers;
    }

    public String getName() {
        return name;
    }
    
    /** Setters **/
    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setComputers(ArrayList<Computer> computers) {
        this.computers = computers;
    }
    
    /** Méthods **/
    public void addComputer(Computer computer) {
       this.computers.add(computer);
    }
}
