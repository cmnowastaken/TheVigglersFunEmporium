/* 
 * Theme Park Dashboard
 * 
 * Implementing a GUI menu
 * 
 * Elliott Bell
 * 
 * 24/07/25
*/

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.Dimension;
import java.awt.event.*;

public class DashboardMain extends JFrame implements ActionListener {
    JFrame mainWindow;
    String windowName;
    
    private ArrayList<JButton> rideButtons;
    
    String[] rideNames = {"Corkscrew",
                          "Bumper Cars",
                          "Go Karts",
                          "The Viggler's Fun Wheel",
                          "Wild Mouse Coaster",
                          "The Long Drop",
                          "Doom Flume",
                          "Hades' Inferno"};
    
    public DashboardMain() {        
        mainWindow = new JFrame("The Viggler's Fun Emporium");
        
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        
        rideButtons = new ArrayList<>();

        for (String rideName : rideNames) {
            JButton rideButton = new JButton(rideName);
            rideButtons.add(rideButton);
        }
        
        for (JButton ride : rideButtons) {
            ride.setIcon(new ImageIcon("Icons/Corkscrew_Art.png"));
            ride.setHorizontalTextPosition(JButton.CENTER);
            ride.setVerticalTextPosition(JButton.BOTTOM);
            ride.setFocusable(false);
            panel.add(ride);
        }
    
        mainWindow.add(panel);
        mainWindow.setSize(1000, 1000);
        mainWindow.setVisible(true);
        
        /* Corkscrew = new JButton();
        Corkscrew.setIcon(new ImageIcon("Icons/Corkscrew_Art.png"));
        Corkscrew.setBounds(40, 40, 160, 160);
        Corkscrew.setFocusable(false);
        Corkscrew.addActionListener(this);
        this.add(Corkscrew); 

        setTitle("The Viggler's Fun Emporium"); // set the window name for the user input
        this.getContentPane().setPreferredSize(new Dimension(600, 400)); // set the dimensions of the window off the user inputs
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); // close the window when the program closes.
        
        this.pack(); // makes contents above preferred size
        this.toFront(); // states that the window should be at the front
        this.setVisible(true); // makes the window visible
                                
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
        */
    }
    public void actionPerformed(ActionEvent e) {
        Object buttonClicked = e.getSource();
        
        for (int i = 0; i < rideButtons.size(); i++) {
            if (buttonClicked == rideButtons.get(i)) {
                System.out.println(rideNames[i]);
                
            }
        }
    }
    // private void RideHandler()
}
