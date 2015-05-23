package GMAssignment1;

import jv.loader.PgObjLoader;
import jv.project.PgJvxSrc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * Created by admin on 23/5/15.
 */
public class LoadModelFrame extends JFrame implements ActionListener {
    private JTextField fileNameField;
    private JButton fileLoadButton;
    private LinkedList<ModelLoadedListener> loadedListeners = new LinkedList<>();

    public LoadModelFrame () {
        setTitle("Load Model"); // Set the title
        setSize(300, 75); // Set size to the frame
        setLayout(new GridLayout(2, 1)); // Set the layout
        setLocationRelativeTo(null);  // Center the frame
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Close the program when the frame is closed

        fileNameField = new JTextField("FileName");
        fileLoadButton = new JButton("LoadModel");
        fileLoadButton.addActionListener(this);

        add(fileNameField);
        add(fileLoadButton);
        setVisible(true);
    }

    public void addModelLoadedListener(ModelLoadedListener listener)
    {
        loadedListeners.add(listener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(fileLoadButton))
        {
            String fileName = this.fileNameField.getText();
            PgObjLoader modelLoader = new PgObjLoader();

            PgJvxSrc[] models = modelLoader.read(fileName);

            ModelLoadedEvent modelLoadedEvent = new ModelLoadedEvent(this, models);

            for(ModelLoadedListener listener : loadedListeners)
            {
                listener.modelLoaded(modelLoadedEvent);
            }

            this.dispose();
        }
    }
}
