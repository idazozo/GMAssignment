package GMAssignment1;

import javax.swing.*;
import java.awt.*;

/**
 * Created by admin on 23/5/15.
 */
public class LoadModelFrame extends JFrame {
    private JTextField fileNameField;
    private JButton fileLoadButton;

    public LoadModelFrame () {
        setTitle("Load Model"); // Set the title
        setSize(400, 400); // Set size to the frame
        setLayout(new FlowLayout()); // Set the layout
        setLocationRelativeTo(null);  // Center the frame
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Close the program when the frame is closed

        fileNameField = new JTextField("FileName");
        fileLoadButton = new JButton("LoadModel");

        add(fileNameField);
        add(fileLoadButton);
        setVisible(true);
    }

    public static void main (String[] args) {
        new LoadModelFrame();
    }
}
