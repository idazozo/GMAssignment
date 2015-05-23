package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by admin on 23/5/15.
 */
public class ShumpiFrame extends JFrame implements ActionListener {
    JPanel panel;
    JButton b1,b2;

    public ShumpiFrame () {
        // Set frame properties
        setTitle("AWT Panel"); // Set the title
        setSize(400,400); // Set size to the frame
        setLayout(new FlowLayout()); // Set the layout
        setLocationRelativeTo(null);  // Center the frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Close the program when the frame is closed

        // Create the panel
        panel = new JPanel();

        // Set panel background
        panel.setBackground(Color.gray);

        // Create buttons
        b1 = new JButton("I am button 1"); // Create a button with default constructor
        b1.addActionListener(this);

        b2 = new JButton("Button 2"); // Create a button with sample text
        b2.addActionListener(this);

        // Add the buttons to the panel
        panel.add(b1);
        panel.add(b2);

        // Add the panel to the frame
        add(panel);
        setVisible(true); // Make the frame visible
    }

    public static void main(String args[]) {
        new ShumpiFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton)
        {
            JButton button = (JButton) e.getSource();

            button.setText("I have been clicked!");
            repaint();
        }
    }
}
