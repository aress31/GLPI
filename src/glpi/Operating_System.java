/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glpi;

public class Operating_System implements java.io.Serializable{
    //Exploitation System
    private String name;
    private String versionNumber;
    
    /** Constructor **/
     public Operating_System(String name, String versionNumber) {
        this.name = name;
        this.versionNumber = versionNumber;
     }
     
    /** Getters **/
    public String getName() {
      return name;
    }
    
    public String getVersionNumber() {
        return versionNumber;
    }
    
    /** Setters **/
    public void setName(String name) {
        this.name = name;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }
}
