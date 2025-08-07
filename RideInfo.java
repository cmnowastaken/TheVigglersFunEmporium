/**
 * The Viggler's Fun Emporium
 * 
 * Dashboard for a simulated theme park; move staff to mitigate lines
 * 
 * Helper class to make the info for the rides more clean 
 * 
 * Elliott Bell
 * 8/8/25
 */

public class RideInfo { // stores the format of the information for the rides to more effectively declare the rides in DashboardMain
    String name;
    String iconPath;
    int maxWait;

    RideInfo(String name, String iconPath, int maxWait) {
        this.name = name;
        this.iconPath = iconPath;
        this.maxWait = maxWait;
    }
}
