/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glpi;

import java.awt.Choice;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MyInterface extends javax.swing.JFrame {

    /**
     * Declaring the different static ArrayLists to stock all our objects
     * (Computers, Rooms, Buildings)
     * Declaring a static Computer, Room and Building to be used for updating
     * these items later
     */
    
    public  static ArrayList<Building> buildings = new ArrayList<>();
    public  static ArrayList<Room> rooms = new ArrayList<>();
    public  static ArrayList<Computer> computers = new ArrayList<>();
    public  static Computer computerInRequest;
    public  static Room roomInRequest;
    public  static Building buildingInRequest;
    
    /**
     * Creates new form MyInterface
     */
    public MyInterface() {
        
        initComponents();

    }
    
    /**
     * Save the added and the updated data to our file
     * @param filePath Our file path
     */
    void saveData(String filePath)
    {
	
        try {
                FileOutputStream file = new FileOutputStream(filePath);
                try (ObjectOutputStream oos = new ObjectOutputStream(file)) {
                    oos.writeObject(buildings);
                    oos.flush();
                }
             }
        catch (java.io.IOException e) {
                System.out.println("Error!");
        }

    }
    
    /**
     * Lod saved data from our file 
     */
     void loadData()
      {
        try {
                FileInputStream file = new FileInputStream("save.dat");
                ObjectInputStream ois = new ObjectInputStream(file);
                buildings =   (ArrayList<Building>) ois.readObject();
             }
                catch (java.io.IOException | ClassNotFoundException e) {
                }
      }
     
    /**
     * Getting the information relative to a computer and set it in their
     * corresponding fields to get ready to update it
     */
    public void computerInformation(java.awt.event.MouseEvent evt) {
        String selectedBuilding;
        String selectedRoom;
        String selectedComputer;
        ArrayList<Room> buildingRooms;
        buildingRooms = new ArrayList<>();
        ArrayList<Computer> roomComputers;
        roomComputers = new ArrayList<>();
        int i,n,m;
        
        //if the computer droplist is not empty
        if(field_choose_comp.getItemCount()>0)
        {
            //get the building from the building droplist
            selectedBuilding = field_choose_comp_building.getSelectedItem();
            //get the room from room droplist
            selectedRoom = field_choose_comp_room.getSelectedItem();
            //get the computer from the computer droplist
            selectedComputer = field_choose_comp.getSelectedItem();
            //search our building in the buildings list
            for( i=0; i<buildings.size(); i++)
            {
                if((buildings.get(i).getName()).equals(selectedBuilding))
                {
                    //when we find the building we select it's rooms list
                    buildingRooms = buildings.get(i).getRooms();
                    break;
                }
            }
            //search our room in that droplist
            for( n=0; n<buildingRooms.size(); n++)
            {
                if((buildingRooms.get(n).getName()).equals(selectedRoom))
                {
                    //get the list of computers of that room
                    roomInRequest = buildingRooms.get(n);
                    roomComputers = buildingRooms.get(n).getComputers();
                    break;
                }
            }
            for( m=0; m<roomComputers.size(); m++)
            {
                //when we find our computer in the list we set all its information in th text fields
                if((roomComputers.get(m).getName()).equals(selectedComputer))
                {
                    computerInRequest = roomComputers.get(m);
                    field_change_OS_installation_date.setText(computerInRequest.getComputerInstallationDate());
                    field_change_OS_version.setText(computerInRequest.getOs().getVersionNumber());
                    field_change_comp_SN.setText(computerInRequest.getSerialNumber());
                    field_change_comp_manufacturer.setText(computerInRequest.getManufacturerName());
                    field_change_comp_model.setText(computerInRequest.getModel());
                    field_change_comp_name.setText(computerInRequest.getName());
                    field_change_installation_date.setText(computerInRequest.getComputerInstallationDate());
                    field_move_to_building.select(selectedBuilding);
                    field_move_to_room.select(selectedRoom);
                    switch (computerInRequest.getOs().getName()) {
                        case "Windows":
                            field_change_comp_OS.setSelectedIndex(0);
                            break;
                        case "Linux":
                            field_change_comp_OS.setSelectedIndex(1);
                            break;
                        default:
                            field_change_comp_OS.setSelectedIndex(2);
                            break;
                    }
                    
                    switch (computerInRequest.getStatus()) {
                        case "Out of service":
                            field_change_comp_status.setSelectedIndex(0);
                            break;
                        case "In service":
                            field_change_comp_status.setSelectedIndex(1);
                            break;
                        default:
                            field_change_comp_status.setSelectedIndex(2);
                            break;
                    }
                    break;
                }
            }
            
        }
    }
    
    /**
     * Getting the information relative to a room and set it in their
     * corresponding fields to get ready to update it
     */
    public void roomInformation(java.awt.event.MouseEvent evt) {
        String selectedBuilding;
        String selectedRoom;
        ArrayList<Room> buildingRooms;
        buildingRooms = new ArrayList<>();
        int i,n;
        
        if(field_choose_room.getItemCount()>0)
        {
            selectedBuilding = field_choose_room_building.getSelectedItem();
            selectedRoom = field_choose_room.getSelectedItem();
            for( i=0; i<buildings.size(); i++)
            {
                if((buildings.get(i).getName()).equals(selectedBuilding))
                {
                    //selecting the building
                    buildingRooms = buildings.get(i).getRooms();
                    break;
                }
            }
            for( n=0; n<buildingRooms.size(); n++)
            {
                if((buildingRooms.get(n).getName()).equals(selectedRoom))
                {
                    //selecting the room
                    //setting the information in the fields
                    roomInRequest = buildingRooms.get(n);
                    field_change_room_name.setText(roomInRequest.getName());
                    field_change_room_capacity.setText(Integer.toString(roomInRequest.getCapacity()));
                    break;
                }
            }
            
        }
    }
    
    /**
     * Getting the information relative to a building and set it in their
     * corresponding fields to get ready to update it
     */
    public void buildingInformation(java.awt.event.MouseEvent evt) {
        String selectedBuilding;
        int i,n,m;
        
        if(field_choose_building.getItemCount()>0)
        {
            selectedBuilding = field_choose_building.getSelectedItem();
            for( i=0; i<buildings.size(); i++)
            {
                if((buildings.get(i).getName()).equals(selectedBuilding))
                {
                    //selecting the building and setting all the information in the fields
                    buildingInRequest = buildings.get(i);
                    field_change_building_name.setText(buildingInRequest.getName());
                    field_change_building_capacity.setText(Integer.toString(buildingInRequest.getCapacity()));
                    break;
                }
            }           
        }
    }  
   
    //Getters for OS while adding a new computer
    
    /**
     * Getting the selected OS
     */
    public String getField_comp_OS() {
        Object element = field_comp_OS.getSelectedValue();
        String text = element.toString();
        return text;
    }
    
    /**
     * Getting entered OS version
     */
    public String getField_OS_version() {
        return field_OS_version.getText();
    }
    
    //Getters for Computer while adding a new computer

    /**
     * Getting entered OS installation date
     */
    public String getField_OS_instDate() {
        return field_OS_instDate.getText();
    }

    /**
     * Getting entered computer installation date in the room
     */   
    public String getField_installation_date() {
        return field_installation_date.getText();
    }

    /**
     * Getting entered computer serial number
     */   
    public String getField_comp_SN() {
        return field_comp_SN.getText();
    }

    /**
     * Getting entered computer manufacturer
     */   
    public String getField_comp_manufacturer() {
        return field_comp_manufacturer.getText();
    }

    /**
     * Getting entered computer model
     */
    public String getField_comp_model() {
        return field_comp_model.getText();
    }

    /**
     * Getting entered computer name
     */
    public String getField_comp_name() {
        return field_comp_name.getText();
    }

    /**
     * Getting selected computer status
     */
    public String getField_comp_status() {
        Object element = field_comp_status.getSelectedValue();
        String text = element.toString();
        return text;
    }
    
    //Getters for OS while updating an existing computer
    
     /**
     * Getting updated OS installation date
     */   
    public String getField_change_OS_version() {
        return field_change_OS_version.getText();
    }
    
     /**
     * Getting updated OS 
     */ 
    public String getField_change_comp_OS() {
        Object element = field_change_comp_OS.getSelectedValue();
        String text = element.toString();
        return text;
    }
    
    //Getters for computer while updating an existing computer
    
    /**
     * Getting updated OS installation date
     */
    public String getField_change_OS_installation_date() {
        return field_change_OS_installation_date.getText();
    }
    
    /**
     * Getting updated computer name
     */
    public String getField_change_comp_name() {
        return field_change_comp_name.getText();
    }

    /**
     * Getting updated computer status
     */
    public String getField_change_comp_status() {
        Object element = field_change_comp_status.getSelectedValue();
        String text = element.toString();
        return text;
    }

    /**
     * Getting updated computer installation date in the room
     */
    public String getField_change_installation_date() {
        return field_change_installation_date.getText();
    }
    
   
    //Getters for Room while adding a new room
    
    /**
     * Getting entered room capacity
     */  
    public int getField_room_capacity() {
        String text =  field_room_capacity.getText();
        return Integer.parseInt(text);        
    }

    /**
     * Getting entered room name
     */  
    public String getField_room_name() {
        return field_room_name.getText();
    }
    
    //Getter for Building while adding a new building
    
    /**
     * Getting entered building capacity
     */  
    public int getField_building_capacity() {
        String text =  field_building_capacity.getText();
        return Integer.parseInt(text);        
    }

    /**
     * Getting entered building name
     */  
    public String getField_building_name() {
        return field_building_name.getText();
    }
    
    //Getters for building while updating an existing building
    
    /**
     * Getting updated building capacity
     */  
    public int getField_change_building_capacity() {
        String text =  field_change_building_capacity.getText();
        return Integer.parseInt(text);  
    }

    /**
     * Getting updated building name
     */ 
    public String getField_change_building_name() {
        return field_change_building_name.getText();
    }

    //Getters for room while updating an existing room
    
    /**
     * Getting updated room capacity
     */ 
    public int getField_change_room_capacity() {
       String text = field_change_room_capacity.getText();
       return Integer.parseInt(text);         
    }
    
    /**
     * Getting updated room name
     */ 
    public String getField_change_room_name() {
        return field_change_room_name.getText();
    }

    //filling tables and drop lists
    
    /**
     * Fills the table of buildings
     */  
   public void fill_building_table() {

       //empty the table at each time we use this function to avoid repetition 
       DefaultTableModel model = (DefaultTableModel) BuildingListTable.getModel();
       for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
       //fill the table by buildings while there is more buildings in the array 
       if(!buildings.isEmpty())
       {
            for (int i = 0; i < buildings.size(); i++) {  
                 model.addRow(new Object[]{buildings.get(i).getName(), buildings.get(i).getCapacity()});
            }  
       }
   }
   
    /**
     * Fills the buildings drop lists for filtering purposes
     */  
   public void fill_building_filters() {
       
       if(!buildings.isEmpty())
       {
           //clear the drop lists
           field_add_room_building.removeAll();
           field_add_comp_building.removeAll();
           FilterRoomBuildingSelect.removeAll();
           FilterComputerBuildingSelect.removeAll();
           field_choose_comp_building.removeAll();
           field_choose_building.removeAll();
           field_choose_room_building.removeAll();
           field_move_to_building.removeAll();
           
           //fill the drop lists using the array that contain each building
            for (int i = 0; i < buildings.size(); i++) { 
                
                field_add_room_building.add(buildings.get(i).getName());
                field_add_room_building.repaint();
                
                field_add_comp_building.add(buildings.get(i).getName());
                field_add_comp_building.repaint();
               
                FilterRoomBuildingSelect.add(buildings.get(i).getName());
                FilterRoomBuildingSelect.repaint();
                
                FilterComputerBuildingSelect.add(buildings.get(i).getName());
                FilterComputerBuildingSelect.repaint();
                
                field_choose_building.add(buildings.get(i).getName());
                field_choose_building.repaint();
                
                field_choose_comp_building.add(buildings.get(i).getName());
                field_choose_comp_building.repaint();
                
                field_choose_room_building.add(buildings.get(i).getName());
                field_choose_room_building.repaint();
                
                field_move_to_building.add(buildings.get(i).getName());
                field_move_to_building.repaint();
            }  
       }
        

   }
   
    /**
     * Fills the table of rooms
     */  
   public void fill_room_table() {
       //empty the table at each time we use this function to avoid repetition
       DefaultTableModel model = (DefaultTableModel) RoomListTable.getModel();
       for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
       //fill the table by rooms
       if(!buildings.isEmpty()){
            for (int i = 0; i < buildings.size(); i++) { 
                for (int j = 0; j < buildings.get(i).getRooms().size(); j++) {
                    model.addRow(new Object[]{buildings.get(i).getRooms().get(j).getName(), buildings.get(i).getName(), buildings.get(i).getRooms().get(j).getCapacity()});
                }
            }
       }  
   }
   
    /**
     * Fills the table of computers
     */  
   public void fill_computer_table() {
       //empty the table at each time we use this function to avoid repetition
       DefaultTableModel model = (DefaultTableModel) ComputerListTable.getModel();
       for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
       //fills the table of computers
       if(!buildings.isEmpty()){
            for (int i = 0; i < buildings.size(); i++) { 
                for (int j = 0; j < buildings.get(i).getRooms().size(); j++) {
                    for (int k = 0; k < buildings.get(i).getRooms().get(j).getComputers().size(); k++) {
                        model.addRow(new Object[]{buildings.get(i).getRooms().get(j).getComputers().get(k).getName(), 
                                                  buildings.get(i).getRooms().get(j).getComputers().get(k).getStatus(),
                                                  buildings.get(i).getRooms().get(j).getComputers().get(k).getManufacturerName(),
                                                  buildings.get(i).getRooms().get(j).getComputers().get(k).getSerialNumber(),
                                                  buildings.get(i).getRooms().get(j).getComputers().get(k).getModel(),
                                                  buildings.get(i).getRooms().get(j).getComputers().get(k).getOsInstallationDate(),
                                                  buildings.get(i).getRooms().get(j).getComputers().get(k).getOs().getName(),
                                                  buildings.get(i).getRooms().get(j).getComputers().get(k).getOs().getVersionNumber(),
                                                  buildings.get(i).getRooms().get(j).getComputers().get(k).getOsInstallationDate(),
                                                  buildings.get(i).getRooms().get(j).getName(),
                                                  buildings.get(i).getName()});
                    }
                }
            }
       }  
   }

   // Getters for search boxes
   
    /**
     * Get the search word in the building search
     */  
    public String getSearchBuildingField() {
        return SearchBuildingField.getText();
    }

    /**
     * Get the search word in the room search
     */  
    public String getSearchRoomField() {
        return SearchRoomField.getText();
    }

    /**
     * Get the search word in the computer search
     */  
    public String getSearchComputerField() {
        return SearchComputerField.getText();
    } 
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FirstTabbedPane = new javax.swing.JTabbedPane();
        ComputerList = new javax.swing.JPanel();
        ComputerListHeader = new javax.swing.JPanel();
        SearchComputerField = new javax.swing.JTextField();
        SearchComputerLabel = new javax.swing.JLabel();
        SearchComputerButton = new javax.swing.JButton();
        FilterComputerBuildingLabel = new javax.swing.JLabel();
        FilterComputerRoomLabel = new javax.swing.JLabel();
        FilterComputerBuildingSelect = new java.awt.Choice();
        FilterComputerRoomSelect = new java.awt.Choice();
        ComputerListBody = new javax.swing.JPanel();
        ComputerListTableHolder = new javax.swing.JScrollPane();
        ComputerListTable = new javax.swing.JTable();
        RoomList = new javax.swing.JPanel();
        RoomListHeader = new javax.swing.JPanel();
        SearchRoomField = new javax.swing.JTextField();
        SearchRoomLabel = new javax.swing.JLabel();
        SearchRoomButton = new javax.swing.JButton();
        FilterRoomBuildingLabel = new javax.swing.JLabel();
        FilterRoomBuildingSelect = new java.awt.Choice();
        RoomListBody = new javax.swing.JPanel();
        RoomListTableHolder = new javax.swing.JScrollPane();
        RoomListTable = new javax.swing.JTable();
        BuildingList = new javax.swing.JPanel();
        BuildingListHeader = new javax.swing.JPanel();
        SearchBuildingField = new javax.swing.JTextField();
        SearchBuildingLabel = new javax.swing.JLabel();
        SearchBuildingButton = new javax.swing.JButton();
        BuildingListBody = new javax.swing.JPanel();
        BuildingListTableHolder = new javax.swing.JScrollPane();
        BuildingListTable = new javax.swing.JTable();
        AddStuff = new javax.swing.JPanel();
        AddStuffTabbedPane = new javax.swing.JTabbedPane();
        AddComputer = new javax.swing.JPanel();
        label_comp_name = new javax.swing.JLabel();
        label_comps_status = new javax.swing.JLabel();
        field_comp_name = new javax.swing.JTextField();
        field_comp_status_holder = new javax.swing.JScrollPane();
        field_comp_status = new javax.swing.JList();
        field_comp_model = new javax.swing.JTextField();
        label_comp_model = new javax.swing.JLabel();
        label_comp_SN = new javax.swing.JLabel();
        field_comp_SN = new javax.swing.JTextField();
        field_comp_manufacturer = new javax.swing.JTextField();
        label_comp_manufacturer = new javax.swing.JLabel();
        field_comp_OS_holder = new javax.swing.JScrollPane();
        field_comp_OS = new javax.swing.JList();
        label_comp_OS = new javax.swing.JLabel();
        label_OS_installation_date = new javax.swing.JLabel();
        field_OS_version = new javax.swing.JTextField();
        label_OS_version = new javax.swing.JLabel();
        field_add_comp_room = new java.awt.Choice();
        label_add_comp_room = new javax.swing.JLabel();
        field_add_comp_building = new java.awt.Choice();
        label_add_comp_building = new javax.swing.JLabel();
        submit_add_computer = new javax.swing.JToggleButton();
        label_installation_date1 = new javax.swing.JLabel();
        field_installation_date = new javax.swing.JFormattedTextField();
        field_OS_instDate = new javax.swing.JFormattedTextField();
        AddRoom = new javax.swing.JPanel();
        label_room_name = new javax.swing.JLabel();
        field_room_name = new javax.swing.JTextField();
        label_add_room_building = new javax.swing.JLabel();
        field_add_room_building = new java.awt.Choice();
        label_room_capacity = new javax.swing.JLabel();
        field_room_capacity = new javax.swing.JTextField();
        submit_add_room = new javax.swing.JToggleButton();
        AddBuilding = new javax.swing.JPanel();
        label_building_name = new javax.swing.JLabel();
        field_building_name = new javax.swing.JTextField();
        field_building_capacity = new javax.swing.JTextField();
        label_building_capacity = new javax.swing.JLabel();
        submit_add_building = new javax.swing.JToggleButton();
        UpdateInfo = new javax.swing.JPanel();
        UpdateInfoTabbedPane = new javax.swing.JTabbedPane();
        UpdateComputer = new javax.swing.JPanel();
        label_change_comp_name = new javax.swing.JLabel();
        label_change_comp_status = new javax.swing.JLabel();
        field_change_comp_name = new javax.swing.JTextField();
        field_change_comp_status_holder = new javax.swing.JScrollPane();
        field_change_comp_status = new javax.swing.JList();
        field_change_comp_model = new javax.swing.JTextField();
        label_change_comp_model = new javax.swing.JLabel();
        label_change_comp_SN = new javax.swing.JLabel();
        field_change_comp_SN = new javax.swing.JTextField();
        field_change_comp_manufacturer = new javax.swing.JTextField();
        label_change_comp_manufacturer = new javax.swing.JLabel();
        field_change_comp_OS_holder = new javax.swing.JScrollPane();
        field_change_comp_OS = new javax.swing.JList();
        label_change_comp_OS = new javax.swing.JLabel();
        field_change_OS_installation_date = new javax.swing.JFormattedTextField();
        label_change_OS_installation_date = new javax.swing.JLabel();
        field_change_OS_version = new javax.swing.JTextField();
        label_change_OS_version = new javax.swing.JLabel();
        field_move_to_room = new java.awt.Choice();
        label_move_to_room = new javax.swing.JLabel();
        field_move_to_building = new java.awt.Choice();
        label_move_to_building = new javax.swing.JLabel();
        label_comp_to_update = new javax.swing.JLabel();
        field_choose_comp_building = new java.awt.Choice();
        label_choose_comp_building = new javax.swing.JLabel();
        label_choose_comp_room = new javax.swing.JLabel();
        label_choose_comp = new javax.swing.JLabel();
        field_choose_comp = new java.awt.Choice();
        field_choose_comp_room = new java.awt.Choice();
        submit_update_computer = new javax.swing.JToggleButton();
        field_change_installation_date = new javax.swing.JFormattedTextField();
        label_change_installation_date = new javax.swing.JLabel();
        transfer = new javax.swing.JCheckBox();
        UpdateRoom = new javax.swing.JPanel();
        label_change_room_name = new javax.swing.JLabel();
        field_change_room_name = new javax.swing.JTextField();
        label_choose_room_building = new javax.swing.JLabel();
        field_choose_room_building = new java.awt.Choice();
        label_change_room_capacity = new javax.swing.JLabel();
        field_change_room_capacity = new javax.swing.JTextField();
        label_room_to_update = new javax.swing.JLabel();
        label_choose_room = new javax.swing.JLabel();
        field_choose_room = new java.awt.Choice();
        submit_update_room = new javax.swing.JToggleButton();
        UpdateBuilding = new javax.swing.JPanel();
        label_change_building_name = new javax.swing.JLabel();
        field_change_building_name = new javax.swing.JTextField();
        field_change_building_capacity = new javax.swing.JTextField();
        label_change_building_capacity = new javax.swing.JLabel();
        label_building_to_update = new javax.swing.JLabel();
        label_choose_building = new javax.swing.JLabel();
        field_choose_building = new java.awt.Choice();
        submit_update_building = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Main Frame");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("Main Frame"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        ComputerList.setBackground(new java.awt.Color(255, 255, 255));

        SearchComputerField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchComputerFieldActionPerformed(evt);
            }
        });

        SearchComputerLabel.setText("Enter computer's name:");

        SearchComputerButton.setText("Search");
        SearchComputerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchComputerButtonActionPerformed(evt);
            }
        });

        FilterComputerBuildingLabel.setText("Filter by building:");

        FilterComputerRoomLabel.setText("Filter by room:");

        FilterComputerBuildingSelect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FilterComputerBuildingSelectMouseClicked(evt);
            }
        });

        FilterComputerRoomSelect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FilterComputerRoomSelectMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout ComputerListHeaderLayout = new javax.swing.GroupLayout(ComputerListHeader);
        ComputerListHeader.setLayout(ComputerListHeaderLayout);
        ComputerListHeaderLayout.setHorizontalGroup(
            ComputerListHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ComputerListHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SearchComputerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SearchComputerField, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(SearchComputerButton)
                .addGap(29, 29, 29)
                .addComponent(FilterComputerBuildingLabel)
                .addGap(25, 25, 25)
                .addComponent(FilterComputerBuildingSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
                .addComponent(FilterComputerRoomLabel)
                .addGap(22, 22, 22)
                .addComponent(FilterComputerRoomSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98))
        );
        ComputerListHeaderLayout.setVerticalGroup(
            ComputerListHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ComputerListHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ComputerListHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FilterComputerRoomLabel)
                    .addComponent(FilterComputerRoomSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ComputerListHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SearchComputerLabel)
                        .addComponent(SearchComputerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(SearchComputerButton)
                        .addComponent(FilterComputerBuildingLabel))
                    .addComponent(FilterComputerBuildingSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ComputerListBody.setBackground(new java.awt.Color(255, 255, 255));

        ComputerListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Status", "Manufacturer", "Serial Number", "Model", "Installation Date", "OS", "OS Version", "Date OS Installation", "Room", "Building"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ComputerListTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        ComputerListTableHolder.setViewportView(ComputerListTable);

        javax.swing.GroupLayout ComputerListBodyLayout = new javax.swing.GroupLayout(ComputerListBody);
        ComputerListBody.setLayout(ComputerListBodyLayout);
        ComputerListBodyLayout.setHorizontalGroup(
            ComputerListBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ComputerListTableHolder)
        );
        ComputerListBodyLayout.setVerticalGroup(
            ComputerListBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ComputerListBodyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ComputerListTableHolder, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ComputerListLayout = new javax.swing.GroupLayout(ComputerList);
        ComputerList.setLayout(ComputerListLayout);
        ComputerListLayout.setHorizontalGroup(
            ComputerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ComputerListLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(ComputerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ComputerListHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ComputerListBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        ComputerListLayout.setVerticalGroup(
            ComputerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ComputerListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ComputerListHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ComputerListBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        FirstTabbedPane.addTab("Computers", ComputerList);

        RoomList.setBackground(new java.awt.Color(255, 255, 255));

        SearchRoomField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchRoomFieldActionPerformed(evt);
            }
        });

        SearchRoomLabel.setText("Enter room's name:");

        SearchRoomButton.setText("Search");
        SearchRoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchRoomButtonActionPerformed(evt);
            }
        });

        FilterRoomBuildingLabel.setText("Filter by building:");

        FilterRoomBuildingSelect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FilterRoomBuildingSelectMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout RoomListHeaderLayout = new javax.swing.GroupLayout(RoomListHeader);
        RoomListHeader.setLayout(RoomListHeaderLayout);
        RoomListHeaderLayout.setHorizontalGroup(
            RoomListHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RoomListHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SearchRoomLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SearchRoomField, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(SearchRoomButton)
                .addGap(70, 70, 70)
                .addComponent(FilterRoomBuildingLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FilterRoomBuildingSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        RoomListHeaderLayout.setVerticalGroup(
            RoomListHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RoomListHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(RoomListHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RoomListHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SearchRoomLabel)
                        .addComponent(SearchRoomField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(SearchRoomButton)
                        .addComponent(FilterRoomBuildingLabel))
                    .addGroup(RoomListHeaderLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(FilterRoomBuildingSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        RoomListBody.setBackground(new java.awt.Color(255, 255, 255));

        RoomListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Building", "Nuber of computers"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        RoomListTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        RoomListTableHolder.setViewportView(RoomListTable);

        javax.swing.GroupLayout RoomListBodyLayout = new javax.swing.GroupLayout(RoomListBody);
        RoomListBody.setLayout(RoomListBodyLayout);
        RoomListBodyLayout.setHorizontalGroup(
            RoomListBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(RoomListTableHolder, javax.swing.GroupLayout.DEFAULT_SIZE, 1086, Short.MAX_VALUE)
        );
        RoomListBodyLayout.setVerticalGroup(
            RoomListBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RoomListBodyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RoomListTableHolder, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout RoomListLayout = new javax.swing.GroupLayout(RoomList);
        RoomList.setLayout(RoomListLayout);
        RoomListLayout.setHorizontalGroup(
            RoomListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RoomListLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(RoomListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RoomListHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(RoomListBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        RoomListLayout.setVerticalGroup(
            RoomListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RoomListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RoomListHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RoomListBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        FirstTabbedPane.addTab("Rooms", RoomList);

        BuildingList.setBackground(new java.awt.Color(255, 255, 255));

        SearchBuildingField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchBuildingFieldActionPerformed(evt);
            }
        });

        SearchBuildingLabel.setText("Enter building's name:");

        SearchBuildingButton.setText("Search");
        SearchBuildingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchBuildingButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BuildingListHeaderLayout = new javax.swing.GroupLayout(BuildingListHeader);
        BuildingListHeader.setLayout(BuildingListHeaderLayout);
        BuildingListHeaderLayout.setHorizontalGroup(
            BuildingListHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BuildingListHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SearchBuildingLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SearchBuildingField, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(SearchBuildingButton)
                .addContainerGap(671, Short.MAX_VALUE))
        );
        BuildingListHeaderLayout.setVerticalGroup(
            BuildingListHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BuildingListHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BuildingListHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchBuildingLabel)
                    .addComponent(SearchBuildingField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchBuildingButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        BuildingListBody.setBackground(new java.awt.Color(255, 255, 255));

        BuildingListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Number of rooms"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        BuildingListTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        BuildingListTableHolder.setViewportView(BuildingListTable);

        javax.swing.GroupLayout BuildingListBodyLayout = new javax.swing.GroupLayout(BuildingListBody);
        BuildingListBody.setLayout(BuildingListBodyLayout);
        BuildingListBodyLayout.setHorizontalGroup(
            BuildingListBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BuildingListTableHolder, javax.swing.GroupLayout.DEFAULT_SIZE, 1086, Short.MAX_VALUE)
        );
        BuildingListBodyLayout.setVerticalGroup(
            BuildingListBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BuildingListBodyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BuildingListTableHolder, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuildingListLayout = new javax.swing.GroupLayout(BuildingList);
        BuildingList.setLayout(BuildingListLayout);
        BuildingListLayout.setHorizontalGroup(
            BuildingListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BuildingListLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(BuildingListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BuildingListHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BuildingListBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        BuildingListLayout.setVerticalGroup(
            BuildingListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BuildingListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BuildingListHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BuildingListBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        FirstTabbedPane.addTab("Building", BuildingList);

        AddStuff.setBackground(new java.awt.Color(255, 255, 255));

        revalidate();
        repaint();

        AddComputer.setBackground(new java.awt.Color(255, 255, 255));

        label_comp_name.setText("Insert computer's name:");

        label_comps_status.setText("Choose status:");

        field_comp_status.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Out of service", "In service", "In stock" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        field_comp_status_holder.setViewportView(field_comp_status);

        label_comp_model.setText("Insert computer's model:");

        label_comp_SN.setText("Computer's serial number:");

        label_comp_manufacturer.setText("Manufacturer Name:");

        field_comp_OS.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Windows", "Linux", "Mac OS" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        field_comp_OS_holder.setViewportView(field_comp_OS);

        label_comp_OS.setText("Computer's operating system:");

        label_OS_installation_date.setText("OS installation date (dd/mm/yyyy):");

        field_OS_version.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_OS_versionActionPerformed(evt);
            }
        });

        label_OS_version.setText("OS version:");

        label_add_comp_room.setText("Add to this room:");

        field_add_comp_building.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                field_add_comp_buildingMouseClicked(evt);
            }
        });

        label_add_comp_building.setText("Add to this building:");

        submit_add_computer.setText("Submit");
        submit_add_computer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submit_add_computerActionPerformed(evt);
            }
        });

        label_installation_date1.setText("Installation date (dd/mm/yyyy):");

        field_installation_date.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        field_installation_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_installation_dateActionPerformed(evt);
            }
        });

        field_OS_instDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        field_OS_instDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_OS_instDateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddComputerLayout = new javax.swing.GroupLayout(AddComputer);
        AddComputer.setLayout(AddComputerLayout);
        AddComputerLayout.setHorizontalGroup(
            AddComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddComputerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AddComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(label_OS_version)
                        .addComponent(label_OS_installation_date)
                        .addComponent(label_comp_name)
                        .addComponent(label_comp_SN))
                    .addComponent(label_installation_date1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label_comp_OS, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(AddComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddComputerLayout.createSequentialGroup()
                        .addGroup(AddComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(AddComputerLayout.createSequentialGroup()
                                .addGap(257, 257, 257)
                                .addComponent(label_comp_manufacturer))
                            .addGroup(AddComputerLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(AddComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(AddComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(field_OS_version)
                                        .addComponent(field_comp_SN)
                                        .addComponent(field_comp_name)
                                        .addComponent(field_comp_OS_holder, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE))
                                    .addComponent(field_installation_date, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(field_OS_instDate, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                                .addGroup(AddComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label_comps_status, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(label_add_comp_room, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(label_add_comp_building, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(label_comp_model, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(18, 18, 18)
                        .addGroup(AddComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(field_add_comp_room, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(field_add_comp_building, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(field_comp_manufacturer)
                            .addComponent(field_comp_status_holder, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                            .addComponent(field_comp_model)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddComputerLayout.createSequentialGroup()
                        .addGap(522, 522, 522)
                        .addComponent(submit_add_computer, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(275, 275, 275))
        );
        AddComputerLayout.setVerticalGroup(
            AddComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddComputerLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(AddComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_comp_name)
                    .addComponent(field_comp_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_comp_model, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_comp_model))
                .addGap(27, 27, 27)
                .addGroup(AddComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_comp_SN)
                    .addComponent(field_comp_SN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_comp_manufacturer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_comp_manufacturer))
                .addGap(23, 23, 23)
                .addGroup(AddComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddComputerLayout.createSequentialGroup()
                        .addGroup(AddComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddComputerLayout.createSequentialGroup()
                                .addComponent(field_comp_status_holder, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(63, 63, 63))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddComputerLayout.createSequentialGroup()
                                .addComponent(label_comp_OS)
                                .addGap(55, 55, 55)))
                        .addComponent(field_add_comp_room, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(AddComputerLayout.createSequentialGroup()
                        .addGroup(AddComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddComputerLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(label_add_comp_room)
                                .addGap(9, 9, 9))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddComputerLayout.createSequentialGroup()
                                .addGroup(AddComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(AddComputerLayout.createSequentialGroup()
                                        .addGroup(AddComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(label_comps_status)
                                            .addComponent(label_installation_date1)
                                            .addComponent(field_installation_date, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(AddComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(AddComputerLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(field_comp_OS_holder, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(AddComputerLayout.createSequentialGroup()
                                                .addGap(53, 53, 53)
                                                .addComponent(label_add_comp_building)
                                                .addGap(0, 9, Short.MAX_VALUE))))
                                    .addGroup(AddComputerLayout.createSequentialGroup()
                                        .addGap(0, 76, Short.MAX_VALUE)
                                        .addComponent(field_add_comp_building, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(16, 16, 16)
                                .addGroup(AddComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(label_OS_installation_date)
                                    .addComponent(field_OS_instDate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)))
                        .addGroup(AddComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label_OS_version)
                            .addComponent(field_OS_version, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addComponent(submit_add_computer)
                .addGap(160, 160, 160))
        );

        AddStuffTabbedPane.addTab("Add Computer", AddComputer);

        AddRoom.setBackground(new java.awt.Color(255, 255, 255));

        label_room_name.setText("Insert room  name:");

        field_room_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_room_nameActionPerformed(evt);
            }
        });

        label_add_room_building.setText("Choose building:");

        if(buildings.size()>0)
        {
            for(int i=0; i<buildings.size();i++)
            {
                field_add_room_building.add(buildings.get(i).getName());
            }
        }

        label_room_capacity.setText("Insert room  capacity:");

        field_room_capacity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_room_capacityActionPerformed(evt);
            }
        });

        submit_add_room.setText("Submit");
        submit_add_room.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submit_add_roomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddRoomLayout = new javax.swing.GroupLayout(AddRoom);
        AddRoom.setLayout(AddRoomLayout);
        AddRoomLayout.setHorizontalGroup(
            AddRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddRoomLayout.createSequentialGroup()
                .addGroup(AddRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(AddRoomLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(submit_add_room, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AddRoomLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(AddRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label_room_capacity)
                            .addComponent(label_room_name))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(AddRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(field_room_name, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                            .addComponent(field_room_capacity))
                        .addGap(62, 62, 62)
                        .addComponent(label_add_room_building)
                        .addGap(27, 27, 27)
                        .addComponent(field_add_room_building, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)))
                .addGap(42, 42, 42))
        );
        AddRoomLayout.setVerticalGroup(
            AddRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddRoomLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(AddRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(field_add_room_building, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(AddRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(label_room_name)
                        .addComponent(field_room_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_add_room_building)))
                .addGap(18, 18, 18)
                .addGroup(AddRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_room_capacity)
                    .addComponent(field_room_capacity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(submit_add_room)
                .addContainerGap(346, Short.MAX_VALUE))
        );

        AddStuffTabbedPane.addTab("Add Room", AddRoom);

        AddBuilding.setBackground(new java.awt.Color(255, 255, 255));

        label_building_name.setText("Insert building name:");

        field_building_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_building_nameActionPerformed(evt);
            }
        });

        field_building_capacity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_building_capacityActionPerformed(evt);
            }
        });

        label_building_capacity.setText("Insert building capacity:");

        submit_add_building.setText("Submit");
        submit_add_building.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submit_add_buildingActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddBuildingLayout = new javax.swing.GroupLayout(AddBuilding);
        AddBuilding.setLayout(AddBuildingLayout);
        AddBuildingLayout.setHorizontalGroup(
            AddBuildingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddBuildingLayout.createSequentialGroup()
                .addGroup(AddBuildingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(AddBuildingLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(submit_add_building, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AddBuildingLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(label_building_name)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(field_building_name, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                        .addGap(29, 29, 29)
                        .addComponent(label_building_capacity)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(field_building_capacity, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)))
                .addGap(31, 31, 31))
        );
        AddBuildingLayout.setVerticalGroup(
            AddBuildingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddBuildingLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(AddBuildingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_building_name)
                    .addComponent(field_building_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_building_capacity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_building_capacity))
                .addGap(30, 30, 30)
                .addComponent(submit_add_building)
                .addContainerGap(361, Short.MAX_VALUE))
        );

        AddStuffTabbedPane.addTab("Add Building", AddBuilding);

        javax.swing.GroupLayout AddStuffLayout = new javax.swing.GroupLayout(AddStuff);
        AddStuff.setLayout(AddStuffLayout);
        AddStuffLayout.setHorizontalGroup(
            AddStuffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddStuffLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddStuffTabbedPane)
                .addContainerGap())
        );
        AddStuffLayout.setVerticalGroup(
            AddStuffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddStuffLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddStuffTabbedPane)
                .addContainerGap())
        );

        FirstTabbedPane.addTab("Add Stuff", AddStuff);

        UpdateInfo.setBackground(new java.awt.Color(255, 255, 255));

        UpdateComputer.setBackground(new java.awt.Color(255, 255, 255));

        label_change_comp_name.setText("Change computer's name:");

        label_change_comp_status.setText("Choose status:");

        field_change_comp_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_change_comp_nameActionPerformed(evt);
            }
        });

        field_change_comp_status.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Out of service", "In service", "In stock" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        field_change_comp_status_holder.setViewportView(field_change_comp_status);

        field_change_comp_model.setEditable(false);

        label_change_comp_model.setText("Insert computer's model:");

        label_change_comp_SN.setText("Computer's serial number:");

        field_change_comp_SN.setEditable(false);

        field_change_comp_manufacturer.setEditable(false);

        label_change_comp_manufacturer.setText("Manufacturer Name");

        field_change_comp_OS.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Windows", "Linux", "Mac OS" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        field_change_comp_OS_holder.setViewportView(field_change_comp_OS);

        label_change_comp_OS.setText("Computer's operating system:");

        field_change_OS_installation_date.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        field_change_OS_installation_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_change_OS_installation_dateActionPerformed(evt);
            }
        });

        label_change_OS_installation_date.setText("OS installation date (dd/mm/yyyy):");

        field_change_OS_version.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_change_OS_versionActionPerformed(evt);
            }
        });

        label_change_OS_version.setText("OS version:");

        label_move_to_room.setText("Move to this room:");

        field_move_to_building.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                field_move_to_buildingMouseClicked(evt);
            }
        });

        label_move_to_building.setText("Move to this building:");

        label_comp_to_update.setText("Choose Computer to update");

        field_choose_comp_building.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                field_choose_comp_buildingMouseClicked(evt);
            }
        });

        label_choose_comp_building.setText("Building:");

        label_choose_comp_room.setText("Room:");

        label_choose_comp.setText("Computer:");

        field_choose_comp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                field_choose_compMouseClicked(evt);
            }
        });

        field_choose_comp_room.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                field_choose_comp_roomMouseClicked(evt);
            }
        });

        submit_update_computer.setText("Submit");
        submit_update_computer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submit_update_computerActionPerformed(evt);
            }
        });

        field_change_installation_date.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        field_change_installation_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_change_installation_dateActionPerformed(evt);
            }
        });

        label_change_installation_date.setText("Installation date (dd/mm/yyyy):");

        transfer.setText("Transfer?");
        transfer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transferActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout UpdateComputerLayout = new javax.swing.GroupLayout(UpdateComputer);
        UpdateComputer.setLayout(UpdateComputerLayout);
        UpdateComputerLayout.setHorizontalGroup(
            UpdateComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UpdateComputerLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(UpdateComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(UpdateComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(UpdateComputerLayout.createSequentialGroup()
                            .addGap(33, 33, 33)
                            .addGroup(UpdateComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(label_change_OS_version)
                                .addComponent(label_change_comp_OS)
                                .addComponent(label_change_comp_SN)
                                .addComponent(label_change_comp_name)
                                .addComponent(label_change_OS_installation_date)
                                .addComponent(label_change_installation_date))
                            .addGap(29, 29, 29)
                            .addGroup(UpdateComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(UpdateComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(field_change_comp_name, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(field_change_comp_OS_holder, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(UpdateComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(field_change_comp_SN, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(field_change_OS_version, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(field_change_OS_installation_date, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(field_change_installation_date, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(UpdateComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(label_move_to_room)
                                .addComponent(label_change_comp_manufacturer)
                                .addComponent(label_change_comp_model)
                                .addComponent(label_change_comp_status)
                                .addComponent(label_move_to_building))
                            .addGap(37, 37, 37)
                            .addGroup(UpdateComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(field_move_to_building, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(field_move_to_room, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(field_change_comp_status_holder, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(field_change_comp_manufacturer, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(field_change_comp_model, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(submit_update_computer, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(21, 21, 21)
                            .addComponent(transfer)
                            .addGap(240, 240, 240))
                        .addGroup(UpdateComputerLayout.createSequentialGroup()
                            .addComponent(label_comp_to_update)
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addGroup(UpdateComputerLayout.createSequentialGroup()
                        .addComponent(label_choose_comp_building)
                        .addGap(40, 40, 40)
                        .addComponent(field_choose_comp_building, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(label_choose_comp_room)
                        .addGap(33, 33, 33)
                        .addComponent(field_choose_comp_room, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(label_choose_comp)
                        .addGap(36, 36, 36)
                        .addComponent(field_choose_comp, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(342, 342, 342))))
        );
        UpdateComputerLayout.setVerticalGroup(
            UpdateComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UpdateComputerLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(label_comp_to_update)
                .addGap(18, 18, 18)
                .addGroup(UpdateComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(field_choose_comp_building, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_choose_comp_building)
                    .addComponent(label_choose_comp_room)
                    .addComponent(field_choose_comp_room, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_choose_comp)
                    .addComponent(field_choose_comp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addGroup(UpdateComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_change_comp_name)
                    .addComponent(field_change_comp_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_change_comp_model, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_change_comp_model))
                .addGap(27, 27, 27)
                .addGroup(UpdateComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_change_comp_SN)
                    .addComponent(field_change_comp_manufacturer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_change_comp_manufacturer)
                    .addComponent(field_change_comp_SN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(UpdateComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(UpdateComputerLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(UpdateComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_change_installation_date, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_change_installation_date))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(UpdateComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_change_comp_OS)
                            .addComponent(field_change_comp_OS_holder, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(UpdateComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(UpdateComputerLayout.createSequentialGroup()
                                .addComponent(field_move_to_room, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(submit_update_computer))
                            .addGroup(UpdateComputerLayout.createSequentialGroup()
                                .addGroup(UpdateComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(field_change_OS_installation_date, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label_change_OS_installation_date)
                                    .addComponent(label_move_to_room))
                                .addGap(18, 18, 18)
                                .addGroup(UpdateComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(field_change_OS_version, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label_change_OS_version))))
                        .addContainerGap())
                    .addGroup(UpdateComputerLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(UpdateComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(transfer)
                            .addGroup(UpdateComputerLayout.createSequentialGroup()
                                .addGroup(UpdateComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label_change_comp_status)
                                    .addComponent(field_change_comp_status_holder, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(UpdateComputerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label_move_to_building)
                                    .addComponent(field_move_to_building, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(130, 130, 130))))
        );

        UpdateInfoTabbedPane.addTab("Update Computer", UpdateComputer);

        UpdateRoom.setBackground(new java.awt.Color(255, 255, 255));

        label_change_room_name.setText("Change room  name:");

        field_change_room_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_change_room_nameActionPerformed(evt);
            }
        });

        label_choose_room_building.setText("Choose building:");

        field_choose_room_building.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                field_choose_room_buildingMouseClicked(evt);
            }
        });

        label_change_room_capacity.setText("Change room  capacity:");

        field_change_room_capacity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_change_room_capacityActionPerformed(evt);
            }
        });

        label_room_to_update.setText("Choose room to update");

        label_choose_room.setText("Choose Room:");

        field_choose_room.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                field_choose_roomMouseClicked(evt);
            }
        });

        submit_update_room.setText("Submit");
        submit_update_room.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submit_update_roomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout UpdateRoomLayout = new javax.swing.GroupLayout(UpdateRoom);
        UpdateRoom.setLayout(UpdateRoomLayout);
        UpdateRoomLayout.setHorizontalGroup(
            UpdateRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UpdateRoomLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(UpdateRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(submit_update_room, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(UpdateRoomLayout.createSequentialGroup()
                        .addComponent(label_room_to_update)
                        .addGap(647, 647, 647))
                    .addGroup(UpdateRoomLayout.createSequentialGroup()
                        .addGroup(UpdateRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label_change_room_name)
                            .addComponent(label_choose_room_building))
                        .addGap(18, 18, 18)
                        .addGroup(UpdateRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(field_change_room_name, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                            .addComponent(field_choose_room_building, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(UpdateRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_change_room_capacity, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label_choose_room, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(21, 21, 21)
                        .addGroup(UpdateRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(field_choose_room, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(field_change_room_capacity, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE))))
                .addContainerGap(298, Short.MAX_VALUE))
        );
        UpdateRoomLayout.setVerticalGroup(
            UpdateRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UpdateRoomLayout.createSequentialGroup()
                .addGroup(UpdateRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(UpdateRoomLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label_room_to_update)
                        .addGap(26, 26, 26)
                        .addComponent(label_choose_room_building))
                    .addGroup(UpdateRoomLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(field_choose_room_building, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UpdateRoomLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(UpdateRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(field_choose_room, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_choose_room, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(80, 80, 80)
                .addGroup(UpdateRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_change_room_name)
                    .addComponent(field_change_room_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_change_room_capacity)
                    .addComponent(field_change_room_capacity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addComponent(submit_update_room)
                .addContainerGap(229, Short.MAX_VALUE))
        );

        UpdateInfoTabbedPane.addTab("Update Room", UpdateRoom);

        UpdateBuilding.setBackground(new java.awt.Color(255, 255, 255));

        label_change_building_name.setText("Change building name:");

        field_change_building_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_change_building_nameActionPerformed(evt);
            }
        });

        field_change_building_capacity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_change_building_capacityActionPerformed(evt);
            }
        });

        label_change_building_capacity.setText("Change building capacity:");

        label_building_to_update.setText("Choose building to update");

        label_choose_building.setText("Choose building:");

        field_choose_building.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                field_choose_buildingMouseClicked(evt);
            }
        });

        submit_update_building.setText("Submit");
        submit_update_building.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submit_update_buildingActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout UpdateBuildingLayout = new javax.swing.GroupLayout(UpdateBuilding);
        UpdateBuilding.setLayout(UpdateBuildingLayout);
        UpdateBuildingLayout.setHorizontalGroup(
            UpdateBuildingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UpdateBuildingLayout.createSequentialGroup()
                .addGroup(UpdateBuildingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(UpdateBuildingLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(submit_update_building))
                    .addGroup(UpdateBuildingLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(UpdateBuildingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(UpdateBuildingLayout.createSequentialGroup()
                                .addComponent(label_choose_building)
                                .addGap(18, 18, 18)
                                .addComponent(field_choose_building, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(UpdateBuildingLayout.createSequentialGroup()
                                .addGroup(UpdateBuildingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label_building_to_update)
                                    .addGroup(UpdateBuildingLayout.createSequentialGroup()
                                        .addComponent(label_change_building_name)
                                        .addGap(18, 18, 18)
                                        .addComponent(field_change_building_name, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)))
                                .addGap(62, 62, 62)
                                .addComponent(label_change_building_capacity)
                                .addGap(18, 18, 18)
                                .addComponent(field_change_building_capacity, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(31, 31, 31))
        );
        UpdateBuildingLayout.setVerticalGroup(
            UpdateBuildingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UpdateBuildingLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(label_building_to_update)
                .addGap(20, 20, 20)
                .addGroup(UpdateBuildingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(UpdateBuildingLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(label_choose_building))
                    .addComponent(field_choose_building, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addGroup(UpdateBuildingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_change_building_name)
                    .addComponent(field_change_building_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_change_building_capacity)
                    .addComponent(field_change_building_capacity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addComponent(submit_update_building)
                .addContainerGap(238, Short.MAX_VALUE))
        );

        UpdateInfoTabbedPane.addTab("Update Building", UpdateBuilding);

        javax.swing.GroupLayout UpdateInfoLayout = new javax.swing.GroupLayout(UpdateInfo);
        UpdateInfo.setLayout(UpdateInfoLayout);
        UpdateInfoLayout.setHorizontalGroup(
            UpdateInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UpdateInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(UpdateInfoTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1095, Short.MAX_VALUE)
                .addContainerGap())
        );
        UpdateInfoLayout.setVerticalGroup(
            UpdateInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UpdateInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(UpdateInfoTabbedPane)
                .addContainerGap())
        );

        FirstTabbedPane.addTab("Update", UpdateInfo);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(FirstTabbedPane)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(FirstTabbedPane)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * updating building information
     */
    private void submit_update_buildingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submit_update_buildingActionPerformed

        //get the building to update
        Component frame = null;
        String buildingName = getField_change_building_name();
        int buildingCapacity = getField_change_building_capacity();
        //set its new name and capacity
        buildingInRequest.setName(buildingName);
        buildingInRequest.setCapacity(buildingCapacity);
        
        JOptionPane.showMessageDialog(frame,"The update has been successfully done");
        
        fill_building_table();
        fill_room_table();
        fill_computer_table();
        fill_building_filters();
    }//GEN-LAST:event_submit_update_buildingActionPerformed

    private void field_change_building_capacityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_change_building_capacityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_change_building_capacityActionPerformed

    private void field_change_building_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_change_building_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_change_building_nameActionPerformed

    private void field_change_room_capacityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_change_room_capacityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_change_room_capacityActionPerformed

    private void field_change_room_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_change_room_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_change_room_nameActionPerformed

    private void field_change_OS_versionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_change_OS_versionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_change_OS_versionActionPerformed

    private void field_change_OS_installation_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_change_OS_installation_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_change_OS_installation_dateActionPerformed

    private void field_building_capacityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_building_capacityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_building_capacityActionPerformed

    private void field_building_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_building_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_building_nameActionPerformed

    private void field_room_capacityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_room_capacityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_room_capacityActionPerformed

    private void field_room_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_room_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_room_nameActionPerformed

    private void field_OS_versionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_OS_versionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_OS_versionActionPerformed
    
    /**
     *  updating computer information
     */ 
    private void submit_update_computerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submit_update_computerActionPerformed
        // TODO add your handling code here:
        String computerName = getField_change_comp_name();
        String computerInstDate = getField_change_installation_date();
        String computerOS = getField_change_comp_OS();
        String computerOSInstDate = getField_change_OS_installation_date();
        String computerOSVersion = getField_change_OS_version();
        String computerStatus = getField_change_comp_status();
        
        Component frame = null;
        
        computerInRequest.setName(computerName);
        computerInRequest.setComputerInstallationDate(computerInstDate);
        computerInRequest.getOs().setName(computerOS);
        computerInRequest.setOsInstallationDate(computerOSInstDate);
        computerInRequest.getOs().setVersionNumber(computerOSVersion);
        computerInRequest.setStatus(computerStatus);
        
        if(transfer.isSelected()==true)
        {
            String selectedBuilding = field_move_to_building.getSelectedItem();
            String selectedRoom = field_move_to_room.getSelectedItem();        
            for(int i = 0; i < buildings.size(); i++)
            {
                if(buildings.get(i).getName().equals(selectedBuilding))
                {
                    for(int j = 0; j < buildings.get(i).getRooms().size(); j++)
                    {
                        if(buildings.get(i).getRooms().get(j).getName().equals(selectedRoom))
                        {
                            if(buildings.get(i).getRooms().get(j).getComputers().size()< buildings.get(i).getRooms().get(j).getCapacity())
                            {
                                buildings.get(i).getRooms().get(j).computers.add(computerInRequest);
                                roomInRequest.computers.remove(computerInRequest);
                                JOptionPane.showMessageDialog(frame,"The transfer has been successfully done");
                                break;
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(frame,
                                "This room where to transfer is already full",
                                "Capacity Error",
                                JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                        }
                    }
                }
             }
        }
        
        JOptionPane.showMessageDialog(frame,"The update has been successfully done");
        remove(BuildingListTable);
        BuildingListTable.revalidate();
        BuildingListTable.repaint();
        remove(RoomListTable);
        RoomListTable.revalidate();
        RoomListTable.repaint();
        remove(ComputerListTable);
        ComputerListTable.revalidate();
        ComputerListTable.repaint();
        fill_building_table();
        fill_room_table();
        fill_computer_table();
        fill_building_filters();
    }//GEN-LAST:event_submit_update_computerActionPerformed
    
    /**
     * adding rooms
     */ 
    private void submit_add_buildingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submit_add_buildingActionPerformed
        // get the values entered
        int buildingCapacity = getField_building_capacity();
        String buildingName = getField_building_name();
        ArrayList<Room> buildingRooms;
        buildingRooms = new ArrayList<>();
        
        //create a new building
        Building newBuilding = new Building(buildingName, buildingCapacity, buildingRooms);
        buildings.add(newBuilding);
        Component frame = null;
        
        JOptionPane.showMessageDialog(frame,"The building has been successfully added");
        
        fill_building_filters();
        
        remove(BuildingListTable);
        BuildingListTable.revalidate();
        BuildingListTable.repaint();
        fill_building_table();
    }//GEN-LAST:event_submit_add_buildingActionPerformed
   
    /**
     * adding rooms
     */ 
    private void submit_add_roomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submit_add_roomActionPerformed
        // get the values entered
        int roomCapacity = getField_room_capacity();
        String roomName = getField_room_name();
        ArrayList<Computer> roomComputers;
        roomComputers = new ArrayList<>();
        //create a new room
        Room newRoom = new Room(roomName, roomCapacity, roomComputers);

        
        Component frame = null;
        
        //get the selected building , where we want to add the room
        String selectedBuilding = field_add_room_building.getSelectedItem();
        for(int i = 0; i < buildings.size(); i++)
        {
            if(buildings.get(i).getName().equals(selectedBuilding))
            {
                //do not dd if the capacity of the building already reached
                if(buildings.get(i).getRooms().size() < buildings.get(i).getCapacity())
                {   
                    rooms.add(newRoom);
                    buildings.get(i).addRoom(newRoom);
                    JOptionPane.showMessageDialog(frame,"The room has been successfully added");
                    break;
                }
                else
                {
                    JOptionPane.showMessageDialog(frame,
                    "This building is already totally allocated",
                    "Capacity Error",
                    JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
        }
        fill_room_table();
        RoomListTable.repaint();
    }//GEN-LAST:event_submit_add_roomActionPerformed
    
    /**
     * adding computers
     */ 
    private void submit_add_computerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submit_add_computerActionPerformed
        // getting the entered values
        
        String compSN = getField_comp_SN();
        String compManuf = getField_comp_manufacturer();
        String compModel = getField_comp_model();
        String compName = getField_comp_name();
        String compOS = getField_comp_OS();
        String compStatus = getField_comp_status();
        String compOSVersion = getField_OS_version();
        String compOSInstDate = getField_installation_date();
        String compInstDate = getField_OS_instDate();
        
        //creating the computer
        Operating_System newOS = new Operating_System(compOS, compOSVersion);
        Computer newComputer = new Computer(compName, compModel, compSN, compManuf, compInstDate, newOS, compOSInstDate, compStatus);
        
        //get the selected building and room, where we want to add the computer
        String selectedBuilding = field_add_comp_building.getSelectedItem();
        String selectedRoom = field_add_comp_room.getSelectedItem();        
        Component frame = null;
        for(int i = 0; i < buildings.size(); i++)
        {
            if(buildings.get(i).getName().equals(selectedBuilding))
            {
                for(int j = 0; j < buildings.get(i).getRooms().size(); j++)
                {
                    if(buildings.get(i).getRooms().get(j).getName().equals(selectedRoom))
                    {
                        //do not dd if the capacity of the room already reached
                        if(buildings.get(i).getRooms().get(j).getComputers().size()< buildings.get(i).getRooms().get(j).getCapacity())
                        {
                            buildings.get(i).getRooms().get(j).computers.add(newComputer);
                            computers.add(newComputer);
                            JOptionPane.showMessageDialog(frame,"The computer has been successfully added");
                            break;
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(frame,
                            "This room is already full",
                            "Capacity Error",
                            JOptionPane.ERROR_MESSAGE);
                            break;
                        }
                    }
                }
            }
        }
        fill_computer_table();
        ComputerListTable.repaint();
    }//GEN-LAST:event_submit_add_computerActionPerformed
    /**
     * Fills the room filter depending on building chosen value while updating room
     */ 
private void field_choose_room_buildingActionPerformed(java.awt.event.MouseEvent evt){
        String selectedBuilding;
        ArrayList<Room> buildingRooms;
        buildingRooms = new ArrayList<>();

        field_choose_room.removeAll();
        field_choose_room.repaint();

        if(field_choose_room_building.getItemCount()>0)
        {
            selectedBuilding = field_choose_room_building.getSelectedItem();
            for(int i=0; i<buildings.size(); i++)
            {
                if((buildings.get(i).getName()).equals(selectedBuilding))
                {
                    buildingRooms = buildings.get(i).getRooms();
                    break;
                }
            }

            if(buildingRooms.size()>0)
            {
                for(int j=0; j<buildingRooms.size();j++)
                {
                    field_choose_room.add(buildingRooms.get(j).getName());
                }
            }

            field_choose_room.repaint();
        }
     }
    
    /**
     * Fills the room filter depending on building chosen value while updating computer
     */ 
private void field_choose_comp_buildingActionPerformed(java.awt.event.MouseEvent evt){
        String selectedBuilding;
        ArrayList<Room> buildingRooms;
        buildingRooms = new ArrayList<>();

        field_choose_comp_room.removeAll();
        field_choose_comp_room.repaint();
        field_choose_comp.removeAll();
        field_choose_comp.repaint();

        if(field_choose_comp_building.getItemCount()>0)
        {
            selectedBuilding = field_choose_comp_building.getSelectedItem();
            for(int i=0; i<buildings.size(); i++)
            {
                if((buildings.get(i).getName()).equals(selectedBuilding))
                {
                    //search the selected building and get its rooms list
                    buildingRooms = buildings.get(i).getRooms();
                    break;
                }
            }

            if(buildingRooms.size()>0)
            {
                for(int j=0; j<buildingRooms.size();j++)
                {
                    //for every room found: add it to the filter
                    field_choose_comp_room.add(buildingRooms.get(j).getName());
                }
            }

            field_choose_comp_room.repaint();
        }
    }
    
    /**
     * Fills the room chooser depending on building chosen value while transfering
     */  
private void field_move_to_buildingActionPerformed(java.awt.event.MouseEvent evt){
        String selectedBuilding;
        ArrayList<Room> buildingRooms;
        buildingRooms = new ArrayList<>();

        field_move_to_room.removeAll();
        field_move_to_room.repaint();

        if(field_move_to_building.getItemCount()>0)
        {
            selectedBuilding = field_move_to_building.getSelectedItem();
            for(int i=0; i<buildings.size(); i++)
            {
                if((buildings.get(i).getName()).equals(selectedBuilding))
                {
                    //search the selected building and get its rooms list
                    buildingRooms = buildings.get(i).getRooms();
                    break;
                }
            }

            if(buildingRooms.size()>0)
            {
                for(int j=0; j<buildingRooms.size();j++)
                {
                    //for every room found: add it to the filter
                    field_move_to_room.add(buildingRooms.get(j).getName());
                }
            }

            field_move_to_room.repaint();
        }
     }
     
    /**
     * Fills the computer chooser depending on building and room chosen values
     */  
private void field_choose_comp_roomActionPerformed(java.awt.event.MouseEvent evt){
        String selectedBuilding;
        String selectedRoom;
        ArrayList<Room> buildingRooms;
        buildingRooms = new ArrayList<>();
        ArrayList<Computer> roomComputers;
        roomComputers = new ArrayList<>();
        int i,n;

        field_choose_comp.removeAll();
        field_choose_comp.repaint();
        
        if(field_choose_comp_room.getItemCount()>0)
        {
            selectedBuilding = field_choose_comp_building.getSelectedItem();
            selectedRoom = field_choose_comp_room.getSelectedItem();
            for( i=0; i<buildings.size(); i++)
            {
                if((buildings.get(i).getName()).equals(selectedBuilding))
                {
                    //search the selected building and get its rooms list
                    buildingRooms = buildings.get(i).getRooms();
                    break;
                }
            }
            for( n=0; n<buildingRooms.size(); n++)
            {
                if((buildingRooms.get(n).getName()).equals(selectedRoom))
                {
                    //search the selected room and get its computers list
                    roomComputers = buildingRooms.get(n).getComputers();
                    break;
                }
            }

            if(roomComputers.size()>0)
            {
                for(int j=0; j<roomComputers.size();j++)
                {
                    //for every computer found: add it to the chooser
                        field_choose_comp.add(roomComputers.get(j).getName());
                }
            }
      
            field_choose_comp.repaint();
        }
    }
    
    /**
     * Fills the room filter depending on building chosen value while adding computers
     */
private void field_add_comp_roomActionPerformed(java.awt.event.MouseEvent evt) { 
        String selectedBuilding;
        ArrayList<Room> buildingRooms;
        buildingRooms = new ArrayList<>();

        field_add_comp_room.removeAll();
        field_add_comp_room.repaint();

        if(field_add_comp_building.getItemCount()>0)
        {
            selectedBuilding = field_add_comp_building.getSelectedItem();
            for(int i=0; i<buildings.size(); i++)
            {
                if((buildings.get(i).getName()).equals(selectedBuilding))
                {
                    //search the selected building and get its rooms list
                    buildingRooms = buildings.get(i).getRooms();
                    break;
                }
            }

            if(buildingRooms.size()>0)
            {
                for(int j=0; j<buildingRooms.size();j++)
                {
                    //for every room found: add it to the filter
                    field_add_comp_room.add(buildingRooms.get(j).getName());
                }
            }

            field_add_comp_room.repaint();
        }
    }
 
    private void SearchBuildingFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchBuildingFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchBuildingFieldActionPerformed

    /**
     * searching a building
     */  
    private void SearchBuildingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchBuildingButtonActionPerformed
        //get the string of search
        String requestedBuilding = getSearchBuildingField();
        for(int row = 0; row < BuildingListTable.getRowCount(); row++)  
 
            {  
                String next = (String)BuildingListTable.getValueAt(row, 0);  
                if(next.equals(requestedBuilding))  
                {  
                    //if a result found: scroll to the result and select it
                    Rectangle rect = BuildingListTable.getCellRect(row, 0, true);
                    BuildingListTable.scrollRectToVisible(rect);
                    BuildingListTable.setRowSelectionInterval(row, row);
                    return;  
                }  
            } 
        fill_building_table();
        BuildingListTable.repaint();
    }//GEN-LAST:event_SearchBuildingButtonActionPerformed

    private void SearchRoomFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchRoomFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchRoomFieldActionPerformed

    /**
     * searching a room
     */
    private void SearchRoomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchRoomButtonActionPerformed
        //get the string of search
        String requestedRoom = getSearchRoomField();
        for(int row = 0; row < RoomListTable.getRowCount(); row++)

        {
            String next = (String)RoomListTable.getValueAt(row, 0);
            if(next.equals(requestedRoom))
            {
                //if a result found: scroll to the result and select it
                Rectangle rect = RoomListTable.getCellRect(row, 0, true);
                RoomListTable.scrollRectToVisible(rect);
                RoomListTable.setRowSelectionInterval(row, row);
                return;
            }
        }
        fill_room_table();
        RoomListTable.repaint();
    }//GEN-LAST:event_SearchRoomButtonActionPerformed

    /**
     * searching a computer
     */
    private void SearchComputerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchComputerButtonActionPerformed

        //get the string of search
        String requestedComputer = getSearchComputerField();
        for(int row = 0; row < ComputerListTable.getRowCount(); row++)
            for(int col = 0; col < ComputerListTable.getColumnCount(); col++)
            {
                String next = (String)ComputerListTable.getValueAt(row, col);
                if(next.equals(requestedComputer))
                {
                    //if a result found: scroll to the result and select it
                    Rectangle rect = ComputerListTable.getCellRect(row, col, true);
                    ComputerListTable.scrollRectToVisible(rect);
                    ComputerListTable.setRowSelectionInterval(row, row);
                    return;
                }
            }
        fill_computer_table();
        ComputerListTable.repaint();
    }//GEN-LAST:event_SearchComputerButtonActionPerformed

    /**
     * filtering the rooms table by building
     */
    private void FilterRoomBuildingSelectActionPerformed(java.awt.event.MouseEvent evt){
        String selectedBuilding;
        ArrayList<Room> buildingRooms;
        buildingRooms = new ArrayList<>();
        int i;

        if(FilterRoomBuildingSelect.getItemCount()>0)
        {
            selectedBuilding = FilterRoomBuildingSelect.getSelectedItem();
            for( i=0; i<buildings.size(); i++)
            {
                if((buildings.get(i).getName()).equals(selectedBuilding))
                {   
                    //search the selected building and get its room list
                    buildingRooms = buildings.get(i).getRooms();
                    break;
                }
            }

            if(buildingRooms.size()>0)
            {
                //empty the table of rooms
                DefaultTableModel model = (DefaultTableModel) RoomListTable.getModel();
                for (int k = model.getRowCount() - 1; k >= 0; k--) {
                    model.removeRow(k);
                }
                //for every room in that building: add it to the table
                for(int j=0; j<buildingRooms.size();j++)
                {
                    model.addRow(new Object[]{buildingRooms.get(j).getName(), buildings.get(i).getName(), buildingRooms.get(j).getCapacity()});
                }
            }
        }
}

    /**
     * filtering the computer table by room
     */
private void FilterComputerRoomSelectActionPerformed(java.awt.event.MouseEvent evt) {
        String selectedBuilding;
        String selectedRoom;
        ArrayList<Room> buildingRooms;
        buildingRooms = new ArrayList<>();
        ArrayList<Computer> roomComputers;
        roomComputers = new ArrayList<>();
        int i,n;

        if(FilterComputerRoomSelect.getItemCount()>0)
        {
            selectedBuilding = FilterComputerBuildingSelect.getSelectedItem();
            selectedRoom = FilterComputerRoomSelect.getSelectedItem();
            for( i=0; i<rooms.size(); i++)
            {
                if((buildings.get(i).getName()).equals(selectedBuilding))
                {
                    //for the selected building: get the rooms
                    buildingRooms = buildings.get(i).getRooms();
                    break;
                }
            }
            for( n=0; n<buildingRooms.size(); n++)
            {
                if((buildingRooms.get(n).getName()).equals(selectedRoom))
                {
                    //for the selected room: get the computers
                    roomComputers = buildingRooms.get(n).getComputers();
                    break;
                }
            }

            if(roomComputers.size()>0)
            {
                //empty the table
                DefaultTableModel model = (DefaultTableModel) ComputerListTable.getModel();
                for (int k = model.getRowCount() - 1; k >= 0; k--) {
                    model.removeRow(k);
                }
                for(int j=0; j<roomComputers.size();j++)
                {
                    //for every computer in the list: add it to the table 
                        model.addRow(new Object[]{
                              roomComputers.get(j).getName(), 
                              roomComputers.get(j).getStatus(),
                              roomComputers.get(j).getManufacturerName(),
                              roomComputers.get(j).getSerialNumber(),
                              roomComputers.get(j).getModel(),
                              roomComputers.get(j).getOsInstallationDate(),
                              roomComputers.get(j).getOs().getName(),
                              roomComputers.get(j).getOs().getVersionNumber(),
                              roomComputers.get(j).getOsInstallationDate(),
                              buildingRooms.get(n).getName(),
                              buildings.get(i).getName()});
                }
            }
        }
}

    /**
     * filtering the computer table by building
     */
private void FilterComputerBuildingSelectActionPerformed(java.awt.event.MouseEvent evt) {
    String selectedBuilding;
        ArrayList<Room> buildingRooms;
        buildingRooms = new ArrayList<>();
        int i;
        //empty the room filter
        FilterComputerRoomSelect.removeAll();
        FilterComputerRoomSelect.repaint();
        
        if(FilterComputerBuildingSelect.getItemCount()>0)
        {
            //search the selected building and get the list of its rooms
            selectedBuilding = FilterComputerBuildingSelect.getSelectedItem();
            for( i=0; i<buildings.size(); i++)
            {
                if((buildings.get(i).getName()).equals(selectedBuilding))
                {
                    buildingRooms = buildings.get(i).getRooms();
                    break;
                }
            }

            if(buildingRooms.size()>0)
            {
                DefaultTableModel model = (DefaultTableModel) ComputerListTable.getModel();
                for (int k = model.getRowCount() - 1; k >= 0; k--) {
                    model.removeRow(k);
                }
                for(int j=0; j<buildingRooms.size();j++)
                {   //for every room in the building: add it's name to the room filter
                    FilterComputerRoomSelect.add(buildingRooms.get(j).getName());
                    //and for every computer in that room add the computer to the table
                    for(int m=0; m<buildingRooms.get(j).getComputers().size();m++)
                    {
                        model.addRow(new Object[]{
                              buildingRooms.get(j).getComputers().get(m).getName(), 
                              buildingRooms.get(j).getComputers().get(m).getStatus(),
                              buildingRooms.get(j).getComputers().get(m).getManufacturerName(),
                              buildingRooms.get(j).getComputers().get(m).getSerialNumber(),
                              buildingRooms.get(j).getComputers().get(m).getModel(),
                              buildingRooms.get(j).getComputers().get(m).getOsInstallationDate(),
                              buildingRooms.get(j).getComputers().get(m).getOs().getName(),
                              buildingRooms.get(j).getComputers().get(m).getOs().getVersionNumber(),
                              buildingRooms.get(j).getComputers().get(m).getOsInstallationDate(),
                              buildingRooms.get(j).getName(),
                              buildings.get(i).getName()});
                    }
                }
            }
           FilterComputerRoomSelect.repaint();
        }
}
    private void SearchComputerFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchComputerFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchComputerFieldActionPerformed

    private void field_change_comp_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_change_comp_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_change_comp_nameActionPerformed

    private void field_change_installation_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_change_installation_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_change_installation_dateActionPerformed

    private void field_installation_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_installation_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_installation_dateActionPerformed

    private void field_OS_instDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_OS_instDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_OS_instDateActionPerformed

    
    /**
     * updating room information
     */
    private void submit_update_roomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submit_update_roomActionPerformed
        // select the room to update
        Component frame = null;
        String roomName = getField_change_room_name();
        int roomCapacity = getField_change_room_capacity();
        
        //set the updated information
        roomInRequest.setName(roomName);
        roomInRequest.setCapacity(roomCapacity);
        JOptionPane.showMessageDialog(frame,"The update has been successfully done");

        fill_room_table();
        fill_computer_table();
    }//GEN-LAST:event_submit_update_roomActionPerformed

    private void transferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transferActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_transferActionPerformed

    private void field_choose_compMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_field_choose_compMouseClicked
        // TODO add your handling code here:
        computerInformation(evt);
    }//GEN-LAST:event_field_choose_compMouseClicked

    private void field_choose_comp_roomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_field_choose_comp_roomMouseClicked
        // TODO add your handling code here:
        field_choose_comp_roomActionPerformed(evt);
    }//GEN-LAST:event_field_choose_comp_roomMouseClicked

    private void field_choose_comp_buildingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_field_choose_comp_buildingMouseClicked
        // TODO add your handling code here:
        field_choose_comp_buildingActionPerformed(evt);
    }//GEN-LAST:event_field_choose_comp_buildingMouseClicked

    private void field_choose_room_buildingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_field_choose_room_buildingMouseClicked
        // TODO add your handling code here:
        field_choose_room_buildingActionPerformed(evt);
    }//GEN-LAST:event_field_choose_room_buildingMouseClicked

    private void field_choose_roomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_field_choose_roomMouseClicked
        // TODO add your handling code here:
        roomInformation(evt);
    }//GEN-LAST:event_field_choose_roomMouseClicked

    private void field_choose_buildingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_field_choose_buildingMouseClicked
        // TODO add your handling code here:
        buildingInformation(evt);
    }//GEN-LAST:event_field_choose_buildingMouseClicked

    private void field_add_comp_buildingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_field_add_comp_buildingMouseClicked
        // TODO add your handling code here:
        field_add_comp_roomActionPerformed(evt);
    }//GEN-LAST:event_field_add_comp_buildingMouseClicked

    private void FilterRoomBuildingSelectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FilterRoomBuildingSelectMouseClicked
        // TODO add your handling code here:
        FilterRoomBuildingSelectActionPerformed(evt);
    }//GEN-LAST:event_FilterRoomBuildingSelectMouseClicked

    private void FilterComputerBuildingSelectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FilterComputerBuildingSelectMouseClicked
        // TODO add your handling code here:
        FilterComputerBuildingSelectActionPerformed(evt);
    }//GEN-LAST:event_FilterComputerBuildingSelectMouseClicked

    private void FilterComputerRoomSelectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FilterComputerRoomSelectMouseClicked
        // TODO add your handling code here:
        FilterComputerRoomSelectActionPerformed(evt);
    }//GEN-LAST:event_FilterComputerRoomSelectMouseClicked

    private void field_move_to_buildingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_field_move_to_buildingMouseClicked
        // TODO add your handling code here:
        field_move_to_buildingActionPerformed(evt);
    }//GEN-LAST:event_field_move_to_buildingMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    /**
     * Application: in close save data
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // save data to our file
        saveData("save.dat");
    }//GEN-LAST:event_formWindowClosing
    
    /**
     * Application: in open load saved data and fill the tables
     */
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // load the data file
        loadData();
        //fill the tables and the filters
        fill_building_table();
        fill_room_table();
        fill_computer_table();
        fill_building_filters();
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MyInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MyInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MyInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MyInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MyInterface().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddBuilding;
    private javax.swing.JPanel AddComputer;
    private javax.swing.JPanel AddRoom;
    private javax.swing.JPanel AddStuff;
    private javax.swing.JTabbedPane AddStuffTabbedPane;
    private javax.swing.JPanel BuildingList;
    private javax.swing.JPanel BuildingListBody;
    private javax.swing.JPanel BuildingListHeader;
    private javax.swing.JTable BuildingListTable;
    private javax.swing.JScrollPane BuildingListTableHolder;
    private javax.swing.JPanel ComputerList;
    private javax.swing.JPanel ComputerListBody;
    private javax.swing.JPanel ComputerListHeader;
    private javax.swing.JTable ComputerListTable;
    private javax.swing.JScrollPane ComputerListTableHolder;
    private javax.swing.JLabel FilterComputerBuildingLabel;
    private java.awt.Choice FilterComputerBuildingSelect;
    private javax.swing.JLabel FilterComputerRoomLabel;
    private java.awt.Choice FilterComputerRoomSelect;
    private javax.swing.JLabel FilterRoomBuildingLabel;
    private java.awt.Choice FilterRoomBuildingSelect;
    private javax.swing.JTabbedPane FirstTabbedPane;
    private javax.swing.JPanel RoomList;
    private javax.swing.JPanel RoomListBody;
    private javax.swing.JPanel RoomListHeader;
    private javax.swing.JTable RoomListTable;
    private javax.swing.JScrollPane RoomListTableHolder;
    private javax.swing.JButton SearchBuildingButton;
    private javax.swing.JTextField SearchBuildingField;
    private javax.swing.JLabel SearchBuildingLabel;
    private javax.swing.JButton SearchComputerButton;
    private javax.swing.JTextField SearchComputerField;
    private javax.swing.JLabel SearchComputerLabel;
    private javax.swing.JButton SearchRoomButton;
    private javax.swing.JTextField SearchRoomField;
    private javax.swing.JLabel SearchRoomLabel;
    private javax.swing.JPanel UpdateBuilding;
    private javax.swing.JPanel UpdateComputer;
    private javax.swing.JPanel UpdateInfo;
    private javax.swing.JTabbedPane UpdateInfoTabbedPane;
    private javax.swing.JPanel UpdateRoom;
    private javax.swing.JFormattedTextField field_OS_instDate;
    private javax.swing.JTextField field_OS_version;
    private java.awt.Choice field_add_comp_building;
    private java.awt.Choice field_add_comp_room;
    private java.awt.Choice field_add_room_building;
    private javax.swing.JTextField field_building_capacity;
    private javax.swing.JTextField field_building_name;
    private javax.swing.JFormattedTextField field_change_OS_installation_date;
    private javax.swing.JTextField field_change_OS_version;
    private javax.swing.JTextField field_change_building_capacity;
    private javax.swing.JTextField field_change_building_name;
    private javax.swing.JList field_change_comp_OS;
    private javax.swing.JScrollPane field_change_comp_OS_holder;
    private javax.swing.JTextField field_change_comp_SN;
    private javax.swing.JTextField field_change_comp_manufacturer;
    private javax.swing.JTextField field_change_comp_model;
    private javax.swing.JTextField field_change_comp_name;
    private javax.swing.JList field_change_comp_status;
    private javax.swing.JScrollPane field_change_comp_status_holder;
    private javax.swing.JFormattedTextField field_change_installation_date;
    private javax.swing.JTextField field_change_room_capacity;
    private javax.swing.JTextField field_change_room_name;
    private java.awt.Choice field_choose_building;
    private java.awt.Choice field_choose_comp;
    private java.awt.Choice field_choose_comp_building;
    private java.awt.Choice field_choose_comp_room;
    private java.awt.Choice field_choose_room;
    private java.awt.Choice field_choose_room_building;
    private javax.swing.JList field_comp_OS;
    private javax.swing.JScrollPane field_comp_OS_holder;
    private javax.swing.JTextField field_comp_SN;
    private javax.swing.JTextField field_comp_manufacturer;
    private javax.swing.JTextField field_comp_model;
    private javax.swing.JTextField field_comp_name;
    private javax.swing.JList field_comp_status;
    private javax.swing.JScrollPane field_comp_status_holder;
    private javax.swing.JFormattedTextField field_installation_date;
    private java.awt.Choice field_move_to_building;
    private java.awt.Choice field_move_to_room;
    private javax.swing.JTextField field_room_capacity;
    private javax.swing.JTextField field_room_name;
    private javax.swing.JLabel label_OS_installation_date;
    private javax.swing.JLabel label_OS_version;
    private javax.swing.JLabel label_add_comp_building;
    private javax.swing.JLabel label_add_comp_room;
    private javax.swing.JLabel label_add_room_building;
    private javax.swing.JLabel label_building_capacity;
    private javax.swing.JLabel label_building_name;
    private javax.swing.JLabel label_building_to_update;
    private javax.swing.JLabel label_change_OS_installation_date;
    private javax.swing.JLabel label_change_OS_version;
    private javax.swing.JLabel label_change_building_capacity;
    private javax.swing.JLabel label_change_building_name;
    private javax.swing.JLabel label_change_comp_OS;
    private javax.swing.JLabel label_change_comp_SN;
    private javax.swing.JLabel label_change_comp_manufacturer;
    private javax.swing.JLabel label_change_comp_model;
    private javax.swing.JLabel label_change_comp_name;
    private javax.swing.JLabel label_change_comp_status;
    private javax.swing.JLabel label_change_installation_date;
    private javax.swing.JLabel label_change_room_capacity;
    private javax.swing.JLabel label_change_room_name;
    private javax.swing.JLabel label_choose_building;
    private javax.swing.JLabel label_choose_comp;
    private javax.swing.JLabel label_choose_comp_building;
    private javax.swing.JLabel label_choose_comp_room;
    private javax.swing.JLabel label_choose_room;
    private javax.swing.JLabel label_choose_room_building;
    private javax.swing.JLabel label_comp_OS;
    private javax.swing.JLabel label_comp_SN;
    private javax.swing.JLabel label_comp_manufacturer;
    private javax.swing.JLabel label_comp_model;
    private javax.swing.JLabel label_comp_name;
    private javax.swing.JLabel label_comp_to_update;
    private javax.swing.JLabel label_comps_status;
    private javax.swing.JLabel label_installation_date1;
    private javax.swing.JLabel label_move_to_building;
    private javax.swing.JLabel label_move_to_room;
    private javax.swing.JLabel label_room_capacity;
    private javax.swing.JLabel label_room_name;
    private javax.swing.JLabel label_room_to_update;
    private javax.swing.JToggleButton submit_add_building;
    private javax.swing.JToggleButton submit_add_computer;
    private javax.swing.JToggleButton submit_add_room;
    private javax.swing.JToggleButton submit_update_building;
    private javax.swing.JToggleButton submit_update_computer;
    private javax.swing.JToggleButton submit_update_room;
    private javax.swing.JCheckBox transfer;
    // End of variables declaration//GEN-END:variables
}
