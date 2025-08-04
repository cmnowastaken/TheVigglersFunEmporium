
/**
 * Class with constructors for the rides
 *
 * Elliott Bell
 * 4/8/25
 */

import java.util.*;

public class Ride {
    String rideName;
    int waitTime;
    double lineGrowth = 11;
    int staffMembers = 1;

    int staffEfficacy = 5; // the amount of time that each staff member clears out of the line by being present
    double setGrowth = 0.9; // the minimum set amount that the line can grow/shrink
    double growthMultiplier = 0.2; // the amount that the line growth is multiplied by; this makes a 10% fluctuation from normal
    
    Random rand = new Random();
    
    public Ride(int waitTime,
                double lineGrowth,
                int staffMembers,
                String rideName) {
                this.waitTime = waitTime; 
                this.lineGrowth = lineGrowth;
                this.staffMembers = staffMembers;
                this.rideName = rideName;
    }
    
    public void waitUpdate() {
        double growthChange = setGrowth + (rand.nextDouble() * growthMultiplier);
        lineGrowth *= growthChange;
        lineGrowth = Math.round(lineGrowth * 10.0) / 10.0; // one decimal

        int change = (int) Math.round(lineGrowth);
        
        waitTime += change - staffMembers * staffEfficacy;
        if (waitTime < 0) waitTime = 0;    
    }
    
    public void assignStaff(int n) {
        staffMembers += n;
    }

    public boolean removeStaff(int n) {
        if (staffMembers - n > 0) {
            staffMembers -= n;
            return true;
        }
        return false;
    }
           
    public int getWait() {
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
}
