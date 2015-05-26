package GMAssignment1;

import GMAssignment1.analysis.ModelAnalysis;
import jv.geom.PgElementSet;
import jv.project.PgJvxSrc;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 25/5/15.
 */
public class AnalysesFrame extends JFrame {
    private List<ModelAnalysis> analyses;

    private List<AnalysisPanel> analysisPanels = new LinkedList<>();

    private JPanel mainPanel;

    private JPanel tabPanel;

    public AnalysesFrame(List<ModelAnalysis> analyses, PgJvxSrc model) {
        this.analyses = analyses;

        // setup frame
        setLayout(new BorderLayout());
        setSize(300, 300);
        mainPanel = new JPanel();
        add(mainPanel, BorderLayout.CENTER);

        //setup tab buttons
        tabPanel = new JPanel();
        tabPanel.setLayout(new FlowLayout());
        PgElementSet geom = new PgElementSet();
        geom.setJvx(model);

        for (ModelAnalysis<Number> analysis : analyses)
        {
            AnalysisPanel panel = new AnalysisPanel(analysis, geom);
            AnalysisButton button = new AnalysisButton(analysis.getName(), panel, this);
            analysisPanels.add(panel);

            tabPanel.add(button);
        }

        if(analysisPanels.size() > 0)
        {
            replaceCenter(analysisPanels.get(0));
        }

        add(tabPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    public void replaceCenter(Component component)
    {
        for(Component c : mainPanel.getComponents())
        {
            mainPanel.remove(c);
        }

        mainPanel.add(component);
        mainPanel.repaint();
        component.repaint();
        repaint();
    }
}
