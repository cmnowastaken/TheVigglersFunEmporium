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
import javax.swing.Timer;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
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
    private ArrayList<JButton> rideButtons = new ArrayList<>();
    
    private int totalStaff = 8;
    private int freeStaff = totalStaff;
    private int updateTime = 50;
    private int horizontalTopPanelSpacing = 50;
    private int verticalTopPanelSpacing = 8;
    private int panelWidth = 740;
    private int defaultPanelHeight = 530;
    private int dayEndPanelHeight = 560;
    private int kiddieMaxWaitTime = 60;
    private int regularMaxWaitTime = 120;
    private int extremeMaxWaitTime = 180;
    private int closingHour = 22;
    private int closingMinute = 30;
    private int hour = 9; // assign the hour, it is assumed that when the program is started it is at the start of the day/9:00 AM
    private int minute = 0;
    
    private boolean parkClosed = false;
    
    Random rand = new Random();
    
    private JLabel clockLabel;
    private JLabel totalFreeStaffCounter;
    
    private JButton backButton;
    private JButton newDayButton;
    
    private Timer timer;

    RideInfo[] ridesInfo = {
        new RideInfo("Corkscrew", "Icons/Corkscrew_Art.png", 180),
        new RideInfo("Bumper Cars", "Icons/Bumper_Art.png", 60),
        new RideInfo("Go Karts", "Icons/GoKart_Art.png", 60),
        new RideInfo("The Viggler's Fun Wheel", "Icons/FunWheel_Art.png", 90),
        new RideInfo("Tiger Coaster", "Icons/Tiger_Art.png", 90),
        new RideInfo("The Long Drop", "Icons/LongDrop_Art.png", 120),
        new RideInfo("Doom Flume", "Icons/DoomFlume_Art.png", 120),
        new RideInfo("Hades' Inferno", "Icons/Inferno_Art.png", 240)
    };  
                                                    
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
       
        for (int i = 0; i < ridesInfo.length; i++) {
            RideInfo info = ridesInfo[i];
            
            JButton rideButton = new JButton(info.name); // create a JButton with the name and icon corresponding to the array
            rideButton.setIcon(new ImageIcon(info.iconPath));
            rideButton.setHorizontalTextPosition(SwingConstants.CENTER); // set position of the button
            rideButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            rideButton.setFocusable(false); // make sure the button cannot be focused on; confirms that focus is always on the window for usability
            rideButtons.add(rideButton);

            Ride rideObject = new Ride(rand.nextInt(15), rand.nextInt(10), 1, info.name, info.maxWait); // make a new object for the ride, assign a random popularity and line growth, allows for changeability between days. also assign no staff to start and give it its name.
            rides.add(rideObject); 
            
            JLabel waitLabel = new JLabel("Wait: " + rideObject.getWait() + " min", SwingConstants.CENTER); // create the labels for both the wait and staff assigned to be seen on the home screen
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
        
        JPanel topPanel = new JPanel(new BorderLayout());
        
        backButton = new JButton();
        backButton.setIcon(new ImageIcon("Icons/Menu_Button.png"));

        backButton.addActionListener(e -> showMenu());
        topPanel.add(backButton, BorderLayout.WEST);
        
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        clockLabel = new JLabel("Time: 09:00");
        centerPanel.add(clockLabel);
        topPanel.add(centerPanel, BorderLayout.CENTER);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 100));
        totalFreeStaffCounter = new JLabel("Available staff: " + freeStaff);
        rightPanel.add(totalFreeStaffCounter);
        topPanel.add(totalFreeStaffCounter, BorderLayout.EAST);
        
        newDayButton = new JButton("Start New Day");
        newDayButton.setVisible(false);
        newDayButton.addActionListener(e -> startNewDay());
        topPanel.add(newDayButton, BorderLayout.SOUTH);
        
        mainWindow.add(topPanel, BorderLayout.NORTH);
        mainWindow.add(rideCards);
        mainWindow.add(topPanel, BorderLayout.NORTH);
        mainWindow.setSize(panelWidth, defaultPanelHeight);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
        
        timer = new Timer(updateTime, e -> {
            for (int i = 0; i < rides.size(); i++) {
                rides.get(i).waitUpdate();
                waitLabels.get(i).setText("Wait time: " + rides.get(i).getWait() + " min");
                rideWindows.get(i).updateLabels(rides.get(i), this);
                
                if (rides.get(i).isAtMaxWait()) {
                    waitLabels.get(i).setText("Wait: " + rides.get(i).getWait() + " min (MAX)");
                    waitLabels.get(i).setForeground(Color.RED);
                } else {
                    waitLabels.get(i).setText("Wait: " + rides.get(i).getWait() + " min");
                    waitLabels.get(i).setForeground(Color.BLACK);
                }
            }
            
            minute += 10;
            if (minute >= 60) {
                minute = 0;
                hour++;
                if (hour >= 24) hour = 0;
            } 
                
            String timeString = String.format("Time: %02d:%02d", hour, minute);
            clockLabel.setText(timeString);
            if (!parkClosed && hour == closingHour && minute == closingMinute) {
                parkClosing();
            }
        });
        
        timer.start();
        
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
    private void parkClosing() {
        parkClosed = true;
        timer.stop();
        mainWindow.setSize(panelWidth, dayEndPanelHeight);
        
        for (JButton button : rideButtons) {
            button.setEnabled(false);
        }
        
        JOptionPane.showMessageDialog(mainWindow, 
                                      "The park is now closed. Come back tomorrow!", 
                                      "Park Closing", 
                                      JOptionPane.INFORMATION_MESSAGE);
        newDayButton.setVisible(true);
    }
    private void startNewDay() {
        hour = 9;
        minute = 0;
        parkClosed = false;
        mainWindow.setSize(panelWidth, defaultPanelHeight);
        
        freeStaff = totalStaff;
    
        for (Ride ride : rides) {
            ride.resetForNewDay(); 
        }
        
        for (JButton button : rideButtons) {
            button.setEnabled(true);
        }
    
        updateStaffCounter();
        for (int i = 0; i < rides.size(); i++) {
            waitLabels.get(i).setText("Wait time: " + rides.get(i).getWait() + " minutes");
            rideWindows.get(i).updateLabels(rides.get(i), this);
        }
        
        newDayButton.setVisible(false);
        timer.start();
    }
}
