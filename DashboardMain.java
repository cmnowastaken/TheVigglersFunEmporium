/* 
 * Theme Park Dashboard
 * 
 * Implementing the very basic menu
 * 
 * Elliott Bell
 * 
 * 26/06/25
*/

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.Dimension;
import java.awt.event.*;

public class DashboardMain extends JFrame implements ActionListener {
    
    JMenuBar mainMenuBar;
    JMenu mainMenu;
    JMenuItem mainMenuItem;
    JButton Corkscrew;
    
    String windowName;
    
    private ArrayList<JButton> rideButtons;
    
    public DashboardMain() {        
        String[] rideNames = {"Corkscrew",
                          "Bumper Cars",
                          "Go Karts",
                          "The Viggler's Fun Wheel",
                          "Wild Mouse Coaster",
                          "The Long Drop",
                          "Doom Flume",
                          "Hades' Inferno"};
                        
        rideButtons = new ArrayList<>();
        
        mainMenuBar = new JMenuBar();
        this.setJMenuBar(mainMenuBar);
        
        mainMenu = new JMenu("Rides");
        mainMenuBar.add(mainMenu);
        
        mainMenuItem = new JMenuItem("Corkscrew");
        mainMenu.add(mainMenuItem);
        
        mainMenu = new JMenu("Management");
        mainMenuBar.add(mainMenu);
        
        mainMenuItem = new JMenuItem("Staff");
        mainMenu.add(mainMenuItem);
        
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        setTitle("The Viggler's Fun Emporium"); // set the window name for the user input
        this.getContentPane().setPreferredSize(new Dimension(600, 400)); // set the dimensions of the window off the user inputs
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); // close the window when the program closes.
        
        this.pack(); // makes contents above preferred size
        this.toFront(); // states that the window should be at the front
        this.setVisible(true); // makes the window visible
        
        for (String rideName : rideNames) {
            JButton rideButton = new JButton(rideName);
            rideButtons.add(rideButton);
        }
        
        for (JButton ride : rideButtons) {
            ride.setIcon(new ImageIcon("Icons/Corkscrew_Art.png"));
            ride.setBounds(40, 40, 160, 160);
            ride.setFocusable(false);
            panel.add(ride);
        }
        
        /* Corkscrew = new JButton();
        Corkscrew.setIcon(new ImageIcon("Icons/Corkscrew_Art.png"));
        Corkscrew.setBounds(40, 40, 160, 160);
        Corkscrew.setFocusable(false);
        Corkscrew.addActionListener(this);
        this.add(Corkscrew); 
        */
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Corkscrew) {
            System.out.println("What the freak");
        // } else if (e.getSource() == BumperCars) {
            
        } else if (e.getSource() == Corkscrew) {
            
        } else if (e.getSource() == Corkscrew) {
            
        } else if (e.getSource() == Corkscrew) {
            
        } else if (e.getSource() == Corkscrew) {
            
        } else if (e.getSource() == Corkscrew) {
            
        } else if (e.getSource() == Corkscrew) {
            
        }
    }
}
