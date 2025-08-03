/**
 * Window which each ride will be handled in
 *
 * Elliott Bell
 * 
 * 3/08/25
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
    
    public RideWindow(DashboardMain mainMenu, Ride ride) {
        setLayout(new FlowLayout());
        
        JPanel statsPanel = new JPanel(new GridLayout(3, 1));
        
        waitLabel = new JLabel("The line is currently " + ride.getWait() + " minutes long.");
        staffLabel = new JLabel("There are " + ride.getStaff() + " staff assigned.");

        statsPanel.add(staffLabel);
        statsPanel.add(waitLabel);
        add(statsPanel, BorderLayout.EAST);
        
        JPanel staffControls = new JPanel(new FlowLayout());
        JButton addStaff = new JButton();
        addStaff.setIcon(new ImageIcon("Icons/Add_Button.png"));
        JButton removeStaff = new JButton();
        removeStaff.setIcon(new ImageIcon("Icons/Remove_Button.png"));
        errorLabel = new JLabel("");
        
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
            if (ride.getStaff() > 0) {
                ride.removeStaff(1);
                mainMenu.unassignStaff(1);
                errorLabel.setText("");
            } else {
                errorLabel.setText("Cannot go below 0 staff.");
            }
            updateLabels(ride, mainMenu);
        });
        
        staffControls.add(addStaff);
        staffControls.add(removeStaff);
        staffControls.add(errorLabel);
        
        add(staffControls, BorderLayout.NORTH);
        
        JButton returnToMenu = new JButton("Return to menu");
        returnToMenu.addActionListener(e -> mainMenu.showMenu());
        add(returnToMenu, BorderLayout.SOUTH);
    }
    private void updateLabels(Ride ride, DashboardMain mainMenu) {
        waitLabel.setText("The line is currently " + ride.getWait() + " minutes long.");
        staffLabel.setText("There are " + ride.getStaff() + " staff assigned.");
    }
}
