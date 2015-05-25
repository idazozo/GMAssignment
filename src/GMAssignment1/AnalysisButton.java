package GMAssignment1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by admin on 25/5/15.
 */
public class AnalysisButton extends JButton implements ActionListener {
    private JPanel analysisPanel;
    private AnalysesFrame frame;

    public AnalysisButton(String name, JPanel panel, AnalysesFrame frame) {
        super(name);

        this.analysisPanel = panel;
        this.frame = frame;
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.replaceCenter(analysisPanel);
    }
}
