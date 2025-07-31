/**
 * Theme Park Dashboard
 * 
 * Implementing cardLayout for windows
 * 
 * Elliott Bell
 * 
 * 1/08/25
*/

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.Dimension;
import java.awt.event.*;

public class DashboardMain extends JFrame implements ActionListener {
    JFrame mainWindow;
    private JPanel rideCards;
    private CardLayout rideCardLayout;
    
    private ArrayList<JButton> rideButtons;
    
    String[] rideNames = {"Corkscrew",
                          "Bumper Cars",
                          "Go Karts",
                          "The Viggler's Fun Wheel",
                          "Wild Mouse Coaster",
                          "The Long Drop",
                          "Doom Flume",
                          "Hades' Inferno"};
                          
    String[] rideIcons = {"Icons/Corkscrew_Art.png",
                          "Icons/Bumper_Art.png",
        
                         };
    
    public DashboardMain() {        
        mainWindow = new JFrame("The Viggler's Fun Emporium");
        
        rideCardLayout = new CardLayout();
        rideCards = new JPanel(rideCardLayout);
        
        rideButtons = new ArrayList<>();

        int index = 0;
        
        JPanel mainMenu = new JPanel(new FlowLayout());
        
        for (String rideName : rideNames) {
            JButton rideButton = new JButton(rideName);
            rideButtons.add(rideButton);
        }
        
        for (JButton ride : rideButtons) {
            int currentIndex = index;
            ride.setIcon(new ImageIcon("Icons/Corkscrew_Art.png"));
            ride.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    RideHandler(currentIndex);
                }
            });
            ride.setHorizontalTextPosition(JButton.CENTER);
            ride.setVerticalTextPosition(JButton.BOTTOM);
            ride.setFocusable(false);
            mainMenu.add(ride);
            index++;
        }
        
        rideCards.add(mainMenu, "menu");
        
        rideCards.add(new RideWindow(this, rideNames[0], 12, 4, 24), "ride0");
        rideCards.add(new RideWindow(this, rideNames[1], 8, 2, 16), "ride1");
        rideCards.add(new RideWindow(this, rideNames[2], 5, 3, 30), "ride2");
        rideCards.add(new RideWindow(this, rideNames[3], 16, 6, 40), "ride3");
        rideCards.add(new RideWindow(this, rideNames[4], 9, 3, 20), "ride4");
        rideCards.add(new RideWindow(this, rideNames[5], 3, 1, 6), "ride5");
        rideCards.add(new RideWindow(this, rideNames[6], 4, 2, 9), "ride6");
        rideCards.add(new RideWindow(this, rideNames[7], 60, 9, 80), "ride7");
    
        mainWindow.add(rideCards);
        mainWindow.setSize(1000, 1000);
        mainWindow.setVisible(true);
        
        showMenu();
    }
    public void actionPerformed(ActionEvent e) {
        Object buttonClicked = e.getSource();
        
        for (int i = 0; i <= rideButtons.size(); i++) {
            if (buttonClicked == rideButtons.get(i)) {
                break;
            }
        }
    }
    private void RideHandler(int index) {
        rideCardLayout.show(rideCards, "ride" + index);
    }
    public void showMenu() {
        rideCardLayout.show(rideCards, "menu");
    }
}
