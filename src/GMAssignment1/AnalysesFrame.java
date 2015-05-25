package GMAssignment1;

import GMAssignment1.analysis.ModelAnalysis;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by admin on 25/5/15.
 */
public class AnalysesFrame extends JFrame {
    private List<ModelAnalysis> analyses;
    private List<JButton> analysesButtons;
    private List<JButton> analysesPanel;

    private JPanel tabPanel;

    public AnalysesFrame(List<ModelAnalysis> analyses) {
        this.analyses = analyses;

        // setup frame
        setLayout(new BorderLayout());
        setSize(300, 300);

        //setup tab buttons
        tabPanel = new JPanel();
        tabPanel.setLayout(new FlowLayout());

        for (ModelAnalysis<Number> analysis : analyses)
        {
            JButton button = new JButton(analysis.getName());
            tabPanel.add(button);
        }

        add(tabPanel, BorderLayout.NORTH);
        setVisible(true);
    }
}
