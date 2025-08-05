/**
 * Window which each ride will be handled in
 * Has now been reformatted according to my trialling
 *
 * Elliott Bell
 * 
 * 4/8/25
*/

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.Dimension;
import java.awt.event.*;;

public class RideWindow extends JPanel {
    private JLabel staffLabel;
    private JLabel errorLabel;
    private JLabel waitLabel;
    private JLabel maxWaitLabel;
    
    public RideWindow(DashboardMain mainMenu, Ride ride) {
        setLayout(new GridLayout(5, 1, 10, 10));
        
        JLabel rideNameLabel = new JLabel(ride.getName(), SwingConstants.CENTER);
        rideNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        rideNameLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(rideNameLabel);
        
        waitLabel = new JLabel("Wait: " + ride.getWait() + " min", SwingConstants.CENTER);
        waitLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(waitLabel);
            
        maxWaitLabel = new JLabel("Maximum wait: " + ride.getMaxWait() + " min", SwingConstants.CENTER);
        add(maxWaitLabel);
        
        JPanel staffPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        
        staffLabel = new JLabel(ride.getStaff() + " staff assigned.");
        staffLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        JButton addStaff = new JButton();
        addStaff.setIcon(new ImageIcon("Icons/Add_Button.png"));
        
        JButton removeStaff = new JButton();
        removeStaff.setIcon(new ImageIcon("Icons/Remove_Button.png"));
        
        addStaff.addActionListener(e -> {
            if (mainMenu.getFreeStaff() > 0) {
                ride.assignStaff(1);
                mainMenu.assignStaff(1);
                errorLabel.setText("");
            } else {
                errorLabel.setText("No staff available. Free some staff first.");
            }
            updateLabels(ride, mainMenu);
        });
        
        removeStaff.addActionListener(e -> {
            if (ride.removeStaff(1)) {
                mainMenu.unassignStaff(1);
                errorLabel.setText("");
            } else {
                errorLabel.setText("Cannot go below 1 staff member.");
            }
            updateLabels(ride, mainMenu);
        });
        
        staffPanel.add(staffLabel);
        staffPanel.add(addStaff);
        staffPanel.add(removeStaff);
        add(staffPanel);
    
        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);
        add(errorLabel);
    }
    public void updateLabels(Ride ride, DashboardMain mainMenu) {
        waitLabel.setText("The line is currently " + ride.getWait() + " minutes long.");
        staffLabel.setText(ride.getStaff() + " staff assigned.");
        mainMenu.updateStaffCounter();
        mainMenu.updateStaffLabel(ride);
        if (ride.isAtMaxWait()) {
            waitLabel.setText("Wait: " + ride.getWait() + " min (MAX)");
            waitLabel.setForeground(Color.RED);
        } else {
            waitLabel.setText("Wait: " + ride.getWait() + " min");
            waitLabel.setForeground(Color.BLACK);
        }
    }
}
