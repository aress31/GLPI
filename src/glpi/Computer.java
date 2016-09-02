/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glpi;

import java.util.Date;

public class Computer implements java.io.Serializable {
 
    //Computer Attributes
    private String name;
    private String model;
    private String serialNumber;
    private String manufacturerName;
    private String computerInstallationDate;
    //Operating System Attributes
    Operating_System os;
    String osInstallationDate;
    //Gestion Attributes
    private String status;

    /** Constructor **/
    public Computer(String name, String model, String serialNumber, String manufacturerName, String computerInstallationDate, Operating_System os, String osInstallationDate, String status) {
        this.name = name;
        this.model = model;
        this.serialNumber = serialNumber;
        this.manufacturerName = manufacturerName;
        this.computerInstallationDate = computerInstallationDate;
        this.os = os;
        this.osInstallationDate = osInstallationDate;
        this.status = status;
    }

    /** Getters **/
    public String getComputerInstallationDate() {
        return computerInstallationDate;
    }
    
    public String getName() {
      return name;
    }
    
    public String getModel() {
        return model;
    }
    
    public String getSerialNumber() {
        return serialNumber;
    }
    
    public String getManufacturerName() {
        return manufacturerName;
    }

    public Operating_System getOs() {
        return os;
    }

    public String getOsInstallationDate() {
        return osInstallationDate;
    }

    public String getStatus() {
        return status;
    }
    
    /** Setters **/
    public void setName(String name) {
        this.name = name;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    
     public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public void setComputerInstallationDate(String computerInstallationDate) {
        this.computerInstallationDate = computerInstallationDate;
    }

    public void setOs(Operating_System os) {
        this.os = os;
    }

    public void setOsInstallationDate(String osInstallationDate) {
        this.osInstallationDate = osInstallationDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
