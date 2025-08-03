/**
 * Theme Park Dashboard
 * 
 * Implementing cardLayout for windows
 * 
 * Elliott Bell
 * 
 * 3/8/25
*/

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.Dimension;
import java.awt.event.*;

public class DashboardMain extends JFrame {
    JFrame mainWindow;
    private JPanel rideCards;
    private CardLayout rideCardLayout;
    
    private ArrayList<Ride> rides = new ArrayList<>();
    private ArrayList<JLabel> waitLabels = new ArrayList<>();
    
    private int totalStaff = 50;
    private int freeStaff = totalStaff;

    String[] rideNames = {"Corkscrew",
                          "Bumper Cars",
                          "Go Karts",
                          "The Viggler's Fun Wheel",
                          "Tiger Coaster",
                          "The Long Drop",
                          "Doom Flume",
                          "Hades' Inferno"};
                          
    String[] rideIcons = {"Icons/Corkscrew_Art.png",
                          "Icons/Bumper_Art.png",
                          "Icons/GoKart_Art.png",
                          "Icons/FunWheel_Art.png",
                          "Icons/Tiger_Art.png",
                          "Icons/LongDrop_Art.png",
                          "Icons/DoomFlume_Art.png",
                          "Icons/Inferno_Art.png"};
                          
    public int getFreeStaff() {
        return freeStaff;
    }
    public void assignStaff(int n) {
        freeStaff -= n;
    }
    public void unassignStaff(int n) {
        freeStaff += n;
    }
    
    public DashboardMain() {        
        mainWindow = new JFrame("The Viggler's Fun Emporium");
        
        rideCardLayout = new CardLayout();
        rideCards = new JPanel(rideCardLayout);
        
        JPanel mainMenu = new JPanel(new FlowLayout());
       
        for (int i = 0; i < rideNames.length; i++) {
            JButton rideButton = new JButton(rideNames[i]);
            rideButton.setIcon(new ImageIcon(rideIcons[i]));
            rideButton.setHorizontalTextPosition(SwingConstants.CENTER);
            rideButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            rideButton.setFocusable(false);
            Ride rideObject = new Ride(10, 15, 4, rideNames[i]);
            rides.add(rideObject); 
            
            JLabel waitLabel = new JLabel("Wait: " + rideObject.getWait() + " min", SwingConstants.CENTER);
            waitLabels.add(waitLabel);
            
            JPanel ridePanel = new JPanel(new BorderLayout());
            ridePanel.add(rideButton, BorderLayout.CENTER);
            ridePanel.add(waitLabel, BorderLayout.SOUTH);

            int currentIndex = i;
            rideButton.addActionListener(e -> RideHandler(currentIndex));

            mainMenu.add(ridePanel);
            
            final int index = i; 
            
            rideButton.addActionListener(e -> RideHandler(index));
        }
        
        rideCards.add(mainMenu, "menu");
        
        for (int i = 0; i < rides.size(); i++) {
            rideCards.add(new RideWindow(this, rides.get(i)), "ride" + i);
        }
    
        mainWindow.add(rideCards);
        mainWindow.setSize(740, 480);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
        
        new javax.swing.Timer(5000, e -> {
            for (int i = 0; i < rides.size(); i++) {
                rides.get(i).waitUpdate();
                waitLabels.get(i).setText("Wait time: " + rides.get(i).getWait() + " minutes");
            }
        }).start();
        
        showMenu();
    }
    private void RideHandler(int index) {
        rideCardLayout.show(rideCards, "ride" + index);
    }
    public void showMenu() {
        rideCardLayout.show(rideCards, "menu");
    }
}
