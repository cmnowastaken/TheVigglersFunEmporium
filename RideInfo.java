
/**
 * Helper class to make the info for the rides more clean 
 * 
 * Elliott Bell
 * 5/8/25
 */
public class RideInfo {
    String name;
    String iconPath;
    int maxWait;

    RideInfo(String name, String iconPath, int maxWait) {
        this.name = name;
        this.iconPath = iconPath;
        this.maxWait = maxWait;
    }
}
