/**
 * Window which each ride will be handled in
 *
 * Elliott Bell
 * 
 * 6/8/25
*/

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class RideWindow extends JPanel {
    private JLabel staffLabel;
    private JLabel errorLabel;
    private JLabel waitLabel;
    
    private JButton addStaff;
    private JButton removeStaff;
    
    public RideWindow(DashboardMain mainMenu, Ride ride) {
        setLayout(new GridLayout(5, 1, 10, 10)); // layout for all of the info in the window
        
        JLabel rideNameLabel = new JLabel(ride.getName(), SwingConstants.CENTER); // put the ride name at the top
        rideNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(rideNameLabel);
        
        waitLabel = new JLabel("Wait: " + ride.getWait() + " min", SwingConstants.CENTER); // followed by the wait time of the ride
        waitLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(waitLabel);
        
        JPanel staffPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5)); // separate panel because it has 3 separate pieces
        
        staffLabel = new JLabel(ride.getStaff() + " staff assigned."); // add the staff who are assigned to the ride
        staffLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        addStaff = new JButton();
        addStaff.setIcon(new ImageIcon("Icons/Add_Button.png"));
        
         removeStaff = new JButton();
        removeStaff.setIcon(new ImageIcon("Icons/Remove_Button.png"));
        
        addStaff.addActionListener(e -> { // action listener for the button
            if (mainMenu.getFreeStaff() > 0) { // if there are any free staff
                ride.assignStaff(1); // assign one staff
                mainMenu.assignStaff(1);
                errorLabel.setText(""); // do not throw an error
            } else {
                errorLabel.setText("No staff available. Free some staff first."); // throw an error
            }
            updateLabels(ride, mainMenu); // update the labels in both parts of the program
        });
        
        removeStaff.addActionListener(e -> {
            if (ride.removeStaff(1)) { // if it is possible to remove staff
                mainMenu.unassignStaff(1); // remove staff
                errorLabel.setText(""); // do not throw an error
            } else {
                errorLabel.setText("Cannot go below 1 staff member."); // throw an error
            }
            updateLabels(ride, mainMenu); // update the labels in the menu and the ride window
        });
        
        staffPanel.add(staffLabel);// add the various labels to the staff panel
        staffPanel.add(addStaff);
        staffPanel.add(removeStaff);
        add(staffPanel);
    
        errorLabel = new JLabel("", SwingConstants.CENTER); //add an error label for usability and UX
        errorLabel.setForeground(Color.RED);
        add(errorLabel);
    }
    public void updateLabels(Ride ride, DashboardMain mainMenu) {
        waitLabel.setText("Wait: " + ride.getWait() + " min"); // set the labels when they update
        staffLabel.setText(ride.getStaff() + " staff assigned.");
        mainMenu.updateStaffCounter();
        mainMenu.updateStaffLabel(ride);
        if (ride.isAtMaxWait()) { // if the ride is at the maximum wait time, let the user know for user experience
            waitLabel.setText("Wait: " + ride.getWait() + " min (MAX)");
            waitLabel.setForeground(Color.RED);
        } else {
            waitLabel.setText("Wait: " + ride.getWait() + " min");
            waitLabel.setForeground(Color.BLACK);
        }
    }
    public void setStaffControlsEnabled(boolean enabled) { // turn the staff controls back on once the day has started again
        addStaff.setEnabled(enabled);
        removeStaff.setEnabled(enabled);
    }
}
