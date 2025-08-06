
/**
 * Class with constructors for the rides
 *
 * Elliott Bell
 * 6/8/25
 */

import java.util.*;

public class Ride {
    String rideName;
    int waitTime;
    double lineGrowth;
    int staffMembers;
    int maxWait;

    int staffEfficacy = 5; // the amount of time that each staff member clears out of the line by being present
    double setGrowth = 0.9; // the minimum set amount that the line can grow/shrink
    double growthMultiplier = 0.2; // the amount that the line growth is multiplied by; this makes a 10% fluctuation from normal
    
    Random rand = new Random();
    
    public Ride(int waitTime, // setters to access the ride object from anywhere
                double lineGrowth,
                int staffMembers,
                String rideName,
                int maxWait) {
                this.waitTime = waitTime; 
                this.lineGrowth = lineGrowth;
                this.staffMembers = staffMembers;
                this.rideName = rideName;
                this.maxWait = maxWait;
    }
    
    public void waitUpdate() {
        double growthChange = setGrowth + (rand.nextDouble() * growthMultiplier); // changes the growth of the line by 10% in either direction
        lineGrowth *= growthChange;
        lineGrowth = Math.round(lineGrowth * 10.0) / 10.0; // one decimal

        int change = (int) Math.round(lineGrowth); // effectively makes it so that we don't have decimals anywhere in this process
        
        waitTime += change - staffMembers * staffEfficacy; // calculates the increase in wait time minus the decrease due to staff members
        if (waitTime < 0) waitTime = 0; // wait time cannot go below 0 minutes
        
        if (waitTime > maxWait) { // wait time cannot go above maximum wait time
            waitTime = maxWait;
        }
    }
    
    public void resetForNewDay() {
        waitTime = 0;
        staffMembers = 1; 
        lineGrowth = 1 + rand.nextDouble() * 10; // rerolls the growth on each new day so that it doesn't stay the same
    }
    
    public void assignStaff(int n) { // adds staff to a ride
        staffMembers += n;
    }

    public boolean removeStaff(int n) {
        if (staffMembers - n > 0) { // does not allow staff members on any given ride to go below 1
            staffMembers -= n;
            return true;
        }
        return false;
    }
    
    public boolean isAtMaxWait() { // enforces the maximum wait time for any specific ride
        return waitTime >= maxWait;
    }
           
    public int getWait() { // getters
        return waitTime;
    }
    public double getGrowth() {
         return lineGrowth;
    }
    public int getStaff() {
        return staffMembers;
    }
    public String getName() {
         return rideName;
    }
    public int getMaxWait() {
        return maxWait;
    }
    
}
