/**
 * Window which each ride will be handled in
 *
 * Elliott Bell
 * 
 * 1/08/25
*/

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.Dimension;
import java.awt.event.*;;

public class RideWindow extends JPanel {
    public RideWindow(DashboardMain mainMenu, String rideName, int lineLength, int staffMembers, int lineGrowth) {
        setLayout(new FlowLayout());
        
        JPanel statsPanel = new JPanel(new GridLayout(3, 1));
        statsPanel.add(new JLabel("Line is " + lineLength + " customers long."));
        statsPanel.add(new JLabel("There are " + staffMembers + " staff assigned."));
        statsPanel.add(new JLabel("The line is growing at a rate of " + lineLength + " customers per minute"));
        add(statsPanel, BorderLayout.EAST);
        
        JButton returnToMenu = new JButton("Return to menu");
        returnToMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenu.showMenu();
            }
        });
        add(returnToMenu, BorderLayout.SOUTH);

    }
}
