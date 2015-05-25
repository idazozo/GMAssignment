package GMAssignment1;

import GMAssignment1.analysis.ModelAnalysis;
import jv.project.PgJvxSrc;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by admin on 25/5/15.
 */
public class AnalysesFrame extends JFrame {
    private List<ModelAnalysis> analyses;
    private List<AnalysisButton> analysesButtons;
    private List<JButton> analysesPanel;

    private JPanel mainPanel;

    private JPanel tabPanel;

    public AnalysesFrame(List<ModelAnalysis> analyses, PgJvxSrc model) {
        this.analyses = analyses;

        // setup frame
        setLayout(new BorderLayout());
        setSize(300, 300);

        //setup tab buttons
        tabPanel = new JPanel();
        tabPanel.setLayout(new FlowLayout());

        for (ModelAnalysis<Number> analysis : analyses)
        {
            AnalysisButton button = new AnalysisButton(analysis.getName(), new AnalysisPanel(analysis, model), this);
            tabPanel.add(button);
        }

        mainPanel = new JPanel();

        add(tabPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void replaceCenter(Component component)
    {
        for(Component c : mainPanel.getComponents())
        {
            mainPanel.remove(c);
        }

        mainPanel.add(component);
        repaint();
    }
}
