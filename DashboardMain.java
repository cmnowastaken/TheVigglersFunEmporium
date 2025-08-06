/**
 * Theme Park Dashboard
 * 
 * Final product, just adding comments
 * 
 * Elliott Bell
 * 
 * 6/8/25
*/

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class DashboardMain extends JFrame {
    JFrame mainWindow; // declare main window, different cards for windows, and the layout
    private JPanel rideCards;
    private CardLayout rideCardLayout;
    
    private ArrayList<Ride> rides = new ArrayList<>(); // declare various arrayLists to keep track of objects and labels
    private ArrayList<JLabel> waitLabels = new ArrayList<>();
    private ArrayList<JLabel> staffLabels = new ArrayList<>();
    private ArrayList<RideWindow> rideWindows = new ArrayList<>();
    private ArrayList<JButton> rideButtons = new ArrayList<>();
    
    private final int freeStaffReference = 8; // reference variable for the amount of free staff, mainly used in the reset at 22:30
    private int freeStaff = freeStaffReference; 
    private int staffPerRide = 1;
    private int updateTime = 1500; // time between ticks in milliseconds
    private int horizontalTopPanelSpacing = 20; // spacing for the assigned staff label
    private int panelWidth = 740; 
    private int defaultPanelHeight = 530;
    private int dayEndPanelHeight = 560; // panel height to allow for the start new day button
    private int closingHour = 22; // assign the hour and minute that the park is meant to close
    private int closingMinute = 30;
    private int hour = 9; // assign the hour and minute, it is assumed that when the program is started it is at the start of the day/9:00 AM
    private int minute = 0;
    
    private boolean parkClosed = false;
    
    Random rand = new Random();
    
    private JLabel clockLabel; // labels that go at the top of the screen
    private JLabel totalFreeStaffCounter;
    
    private JButton backButton; // the buttons that go at the top of the screen
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

            Ride rideObject = new Ride(rand.nextInt(15), rand.nextInt(10), staffPerRide, info.name, info.maxWait); // make a new object for the ride, assign a random popularity and line growth, allows for changeability between days. also assign no staff to start and give it its name.
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
            rideCards.add(window, "ride" + i); // add the ride based off of its position in the for loop; each ride gets a value assigned to it between ride0 and ride7 to make them easily accessible here
        }
        
        JPanel topPanel = new JPanel(new BorderLayout());
        
        backButton = new JButton();
        backButton.setIcon(new ImageIcon("Icons/Menu_Button.png"));

        backButton.addActionListener(e -> showMenu()); // action listener to allow the back button to return the user to the menu
        topPanel.add(backButton, BorderLayout.WEST);
        
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        clockLabel = new JLabel("Time: 09:00");
        clockLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, horizontalTopPanelSpacing)); // pad out the right hand side of the available staff label 
        clockLabel.setFont(new Font("Arial", Font.BOLD, 14));
        centerPanel.add(clockLabel); // panel inside of a panel to follow convention
        topPanel.add(centerPanel, BorderLayout.CENTER); // add the panel that has been created to the main top panel and format to center
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalFreeStaffCounter = new JLabel("Available staff: " + freeStaff);
        rightPanel.add(totalFreeStaffCounter); // panel inside of a panel to follow convention
        topPanel.add(totalFreeStaffCounter, BorderLayout.EAST); // add the panel that has been created to the main top panel and format to east
        
        newDayButton = new JButton("Start New Day");
        newDayButton.setVisible(false); // make this button invisible until it is needed at the end of the day
        newDayButton.addActionListener(e -> startNewDay()); // make this button call the startNewDay method
        topPanel.add(newDayButton, BorderLayout.SOUTH);
        
        mainWindow.add(topPanel, BorderLayout.NORTH); // add all of the cards and panels to the window, and then resize it.
        mainWindow.add(rideCards);
        mainWindow.setSize(panelWidth, defaultPanelHeight);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
        
        timer = new Timer(updateTime, e -> { // run this code every updateTime ms
            for (int i = 0; i < rides.size(); i++) { // update all of the rides
                rides.get(i).waitUpdate(); // update the wait time for the rides on the tick
                waitLabels.get(i).setText("Wait: " + rides.get(i).getWait() + " min");
                rideWindows.get(i).updateLabels(rides.get(i), this);
                
                if (rides.get(i).isAtMaxWait()) {
                    waitLabels.get(i).setText("Wait: " + rides.get(i).getWait() + " min (MAX)"); // show that a ride is at max wait time if it is at max wait time
                    waitLabels.get(i).setForeground(Color.RED);
                } else {
                    waitLabels.get(i).setText("Wait: " + rides.get(i).getWait() + " min");
                    waitLabels.get(i).setForeground(Color.BLACK);
                }
            }
            
            minute += 10; // add 10 minutes to the clock
            if (minute >= 60) { // add 1 to the hour and reset the clock if the minutes is at 60
                minute = 0;
                hour++;
                if (hour >= 24) hour = 0;
            } 
                
            String timeString = String.format("Time: %02d:%02d", hour, minute); // format the string to pad with zeroes until the time is two digits long, substitute in the hour and the minute
            clockLabel.setText(timeString);
            if (!parkClosed && hour == closingHour && minute == closingMinute) { // if the park is not already closed and it is the closing hour and minute, close the park
                parkClosing();
            }
        });
        
        timer.start();
        
        showMenu();
    }
    private void RideHandler(int index) { // handles showing the ride cards when the buttons are pressed
        rideCardLayout.show(rideCards, "ride" + index);
        setBackButtonVisible(true);
    }
    public void showMenu() { // allow the user to go back to the menu from the ride cards
        rideCardLayout.show(rideCards, "menu");
        setBackButtonVisible(false);
    }
    public void updateStaffCounter() { // allows me to update the staff counter without relying on the timer; does it instantly
        totalFreeStaffCounter.setText("Available staff: " + freeStaff);
        totalFreeStaffCounter.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, horizontalTopPanelSpacing)); // pads out the border
    }
    public void updateStaffLabel(Ride ride) {
        int index = rides.indexOf(ride); // get the ride
        if (index >= 0) {
            staffLabels.get(index).setText(ride.getStaff() + " staff assigned"); // reestablish the amount of staff assigned to the ride
        }
    }
    public void setBackButtonVisible(boolean visible) { // make the back button visible (only used in rideWindow)
        backButton.setVisible(visible);
    }
    private void parkClosing() { // method for closing the park
        parkClosed = true; // boolean so that the tick does not fire multiple times
        timer.stop();
        mainWindow.setSize(panelWidth, dayEndPanelHeight); // change the height of the panel to account for the start new day button
        
        for (JButton button : rideButtons) { // disable buttons so that they cannot be clicked
            button.setEnabled(false);
        }
        
        for (RideWindow window : rideWindows) { // disable staff controls so that staff cannot be moved around
            window.setStaffControlsEnabled(false);
        }
        
        JOptionPane.showMessageDialog(mainWindow, // show a popup to let the user know that the park is closing
                                      "The park is now closed. Come back tomorrow!", 
                                      "Park Closing", 
                                      JOptionPane.INFORMATION_MESSAGE);
        newDayButton.setVisible(true); // add the back button
    }
    private void startNewDay() { // method for when the startNewDay button is pressed
        hour = 9; // reset the hour and minute to what they are at the start of the day, as well as the boolean
        minute = 0;
        parkClosed = false;
        mainWindow.setSize(panelWidth, defaultPanelHeight); // no more startNewDay button so we can use the regular panel height
        
        String timeString = String.format("Time: %02d:%02d", hour, minute); // reformat the clock
        clockLabel.setText(timeString); // set the clock
        
        freeStaff = freeStaffReference; // use the final to reassign the free staff
        
        for (Ride ride : rides) { // reset the ride staff and wait time
            ride.resetForNewDay(); 
        }
        
        for (JButton button : rideButtons) { // reenable the buttons
            button.setEnabled(true);
        }
    
        updateStaffCounter();
        for (int i = 0; i < rides.size(); i++) { // reset all of the labels and controls to the start of day values
            waitLabels.get(i).setText("Wait time: " + rides.get(i).getWait() + " minutes");
            rideWindows.get(i).updateLabels(rides.get(i), this);
            rideWindows.get(i).setStaffControlsEnabled(true);
        }
        
        newDayButton.setVisible(false); // get ride of the new day button
        timer.start(); // restart the timer
    }
}
