package frame;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * Created by admin on 23/5/15.
 */
public class ShumpiFrame extends JFrame {
    private LinkedList<ShumpiFrame> children = new LinkedList<>();

    private JPanel panel;

    private JButton addChildButton;

    private JTextField newNameField;

    private JButton killChildrenButton;

    public ShumpiFrame(String name) {
        // Set frame properties
        setTitle(name); // Set the title
        setSize(400, 400); // Set size to the frame
        setLayout(new FlowLayout()); // Set the layout
        setLocationRelativeTo(null);  // Center the frame
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Close the program when the frame is closed

        // Create the panel
        panel = new JPanel();
        panel.setSize(400, 400);
        // Set panel background
        panel.setBackground(Color.gray);

        // Create buttons
        addChildButton = new JButton("Add"); // Create a button with default constructor
        addChildButton.addActionListener(new AddChildListener(this));

        newNameField = new JTextField("New Child Window Name");
        newNameField.setSize(200, newNameField.getHeight());

        killChildrenButton = new JButton("Close children"); // Create a button with sample text
        killChildrenButton.addActionListener(new KillChildrenListener(this));

        // Add the buttons to the panel
        panel.add(addChildButton);
        panel.add(newNameField);
        panel.add(killChildrenButton);

        // Add the panel to the frame
        getContentPane().add(panel);
        setVisible(true); // Make the frame visible
    }

    public void addChild(ShumpiFrame child) {
        children.add(child);
    }

    public String getNewChildName()
    {
        return newNameField.getText();
    }

    public void closeChildren() {
        for ( ShumpiFrame child : children)
        {
            child.dispose();
        }
    }

    @Override
    public void dispose()
    {
        closeChildren();
        super.dispose();
    }

    public static void main(String args[]) {
        new ShumpiFrame("root");
    }
}
