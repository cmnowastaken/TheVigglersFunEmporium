/**
 * Theme Park Dashboard
 * 
 * Finishing touches, mainly usability.
 * At this point this is nearly MVP, just have to write the code for the shut down and the maximum line lengths
 * 
 * Elliott Bell
 * 
 * 4/8/25
*/

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.Dimension;
import java.awt.event.*;

public class DashboardMain extends JFrame {
    JFrame mainWindow; // declare main window, different cards for windows, and the layout
    private JPanel rideCards;
    private CardLayout rideCardLayout;
    
    private ArrayList<Ride> rides = new ArrayList<>(); // declare various arrayLists to keep track of objects and labels
    private ArrayList<JLabel> waitLabels = new ArrayList<>();
    private ArrayList<JLabel> staffLabels = new ArrayList<>();
    private ArrayList<RideWindow> rideWindows = new ArrayList<>();
    
    private int totalStaff = 15;
    private int freeStaff = totalStaff;
    private int updateTime = 10000;
    private int horizontalTopPanelSpacing = 50;
    private int verticalTopPanelSpacing = 8;
    private int panelWidth = 740;
    private int panelHeight = 530;
    
    Random rand = new Random();
    
    private JLabel clockLabel;
    private JLabel totalFreeStaffCounter;
    private JButton backButton;
    
    private int hour = 9; // assign the hour, it is assumed that when the program is started it is at the start of the day/9:00 AM
    private int minute = 0;

    String[] rideNames = {"Corkscrew", // arrays of ride names and ride art to be referenced
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
                          
    public int getFreeStaff() { // setters and getters for the staff reassigning methods
        return freeStaff;
    }
    public void assignStaff(int n) {
        freeStaff -= n;
    }
    public void unassignStaff(int n) {
        freeStaff += n;
    }
    public JLabel getClockLabel() {
        return clockLabel;
    }
    public JLabel getStaffCounter() {
        return totalFreeStaffCounter;
    }

    
    public DashboardMain() {        
        mainWindow = new JFrame("The Viggler's Fun Emporium");
        
        rideCardLayout = new CardLayout(); // the layout for the different ride "cards"/windows
        rideCards = new JPanel(rideCardLayout);
        
        JPanel mainMenu = new JPanel(new FlowLayout()); // the layout for the main dashboard
       
        for (int i = 0; i < rideNames.length; i++) {
            JButton rideButton = new JButton(rideNames[i]); // create a JButton with the name and icon corresponding to the array
            rideButton.setIcon(new ImageIcon(rideIcons[i]));
            rideButton.setHorizontalTextPosition(SwingConstants.CENTER); // set position of the button
            rideButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            rideButton.setFocusable(false); // make sure the button cannot be focused on; confirms that focus is always on the window for usability
            Ride rideObject = new Ride(rand.nextInt(15), rand.nextInt(10), 0, rideNames[i]); // make a new object for the ride, assign a random popularity and line growth, allows for changeability between days. also assign no staff to start and give it its name.
            rides.add(rideObject); 
            
            JLabel waitLabel = new JLabel("Wait time: " + rideObject.getWait() + " min", SwingConstants.CENTER); // create the labels for both the wait and staff assigned to be seen on the home screen
            waitLabels.add(waitLabel);
            
            JLabel staffLabel = new JLabel(rideObject.getStaff() + " staff assigned", SwingConstants.CENTER);
            staffLabels.add(staffLabel);
            
            JPanel ridePanel = new JPanel(new BorderLayout()); // make a panel to add the button to 
            ridePanel.add(rideButton, BorderLayout.CENTER);
            
            JPanel rideInfoPanel = new JPanel(new GridLayout(2, 1)); // make a panel to add the wait time and staff assigned to
            rideInfoPanel.add(waitLabel);
            rideInfoPanel.add(staffLabel);
            
            ridePanel.add(rideInfoPanel, BorderLayout.SOUTH); // align the info to the bottom

            int currentIndex = i; 
            rideButton.addActionListener(e -> RideHandler(currentIndex)); // adds an action listener based on the ride, allows them to be accessed without using the ride names instead giving them a number between 0 and 7.

            mainMenu.add(ridePanel); // add this whole thing to the main menu
        }
        
        rideCards.add(mainMenu, "menu"); // create a card for the menu to allow return from the different ride cards.
        
        for (int i = 0; i < rides.size(); i++) { // add each ride's window and its number (ride0, ride1, etc.) to allow it to be accessed by the action listener
            RideWindow window = new RideWindow(this, rides.get(i));
            rideWindows.add(window);
            rideCards.add(window, "ride" + i);
        }
        
        backButton = new JButton();
        backButton.setIcon(new ImageIcon("Icons/Menu_Button.png"));
        backButton.addActionListener(e -> showMenu());

        clockLabel = new JLabel("Time: 09:00", SwingConstants.CENTER);
        totalFreeStaffCounter = new JLabel("Available staff: " + freeStaff, SwingConstants.RIGHT);

        JPanel topPanel = new JPanel(new BorderLayout());
        backButton.addActionListener(e -> showMenu());
        topPanel.add(backButton, BorderLayout.WEST);
        
        clockLabel = new JLabel("Time: 09:00", SwingConstants.CENTER);
        totalFreeStaffCounter = new JLabel("Available staff: " + freeStaff, SwingConstants.RIGHT);
        
        topPanel.add(clockLabel, BorderLayout.CENTER);
        topPanel.add(totalFreeStaffCounter, BorderLayout.EAST);

        mainWindow.add(topPanel, BorderLayout.NORTH);
    
        mainWindow.add(rideCards);
        mainWindow.add(topPanel, BorderLayout.NORTH);
        mainWindow.setSize(panelWidth, panelHeight);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
        
        new javax.swing.Timer(updateTime, e -> {
            for (int i = 0; i < rides.size(); i++) {
                rides.get(i).waitUpdate();
                waitLabels.get(i).setText("Wait time: " + rides.get(i).getWait() + " minutes");
                rideWindows.get(i).updateLabels(rides.get(i), this);
            }
            
            minute += 10;
            if (minute >= 60) {
                minute = 0;
                hour++;
                if (hour >= 24) hour = 0;
            } 
                
            String timeString = String.format("Time: %02d:%02d", hour, minute);
            clockLabel.setText(timeString);
        }).start();
        
        showMenu();
    }
    private void RideHandler(int index) {
        rideCardLayout.show(rideCards, "ride" + index);
        setBackButtonVisible(true);
    }
    public void showMenu() {
        rideCardLayout.show(rideCards, "menu");
        setBackButtonVisible(false);
    }
    public void updateStaffCounter() {
        totalFreeStaffCounter.setText("Available staff: " + freeStaff);
    }
    public void updateStaffLabel(Ride ride) {
        int index = rides.indexOf(ride);
        if (index >= 0) {
            staffLabels.get(index).setText(ride.getStaff() + " staff assigned");
        }
    }
    public void setBackButtonVisible(boolean visible) {
        backButton.setVisible(visible);
    }
}
