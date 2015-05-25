package GMAssignment1;

import GMAssignment1.analysis.ModelAnalysis;
import jv.project.PgJvxSrc;

import javax.swing.*;
import java.awt.*;

/**
 * Created by admin on 25/5/15.
 */
public class AnalysisPanel<T extends Number> extends JPanel {
    private ModelAnalysis<T> analysis;
    private PgJvxSrc model;
    private JPanel overviewPanel;
    private JLabel minKey;
    private JLabel minValue;
    private JLabel maxKey;
    private JLabel maxValue;
    private JLabel meanKey;
    private JLabel meanValue;
    private JLabel standardDeviationKey;
    private JLabel standardDeviationValue;

    public AnalysisPanel(ModelAnalysis<T> analysis, PgJvxSrc model) {
        this.analysis = analysis;
        this.model = model;
        setLayout(new BorderLayout());

        overviewPanel = new JPanel();
        overviewPanel.setLayout(new GridLayout(4, 1));
        minKey = new JLabel("Min:");
        minValue = new JLabel("" + analysis.getStatistics(model).getMin());
        maxKey = new JLabel("Max:");
        maxValue = new JLabel("" + analysis.getStatistics(model).getMax());
        meanKey = new JLabel("\u03BC:");
        meanValue = new JLabel("" + analysis.getStatistics(model).getMean());
        standardDeviationKey = new JLabel("\u03C3^2");
        standardDeviationValue = new JLabel("" + analysis.getStatistics(model).getStandardDeviation());

        overviewPanel.add(minKey);
        overviewPanel.add(minValue);
        overviewPanel.add(maxKey);
        overviewPanel.add(maxValue);
        overviewPanel.add(meanKey);
        overviewPanel.add(meanValue);
        overviewPanel.add(standardDeviationKey);
        overviewPanel.add(standardDeviationValue);

        add(overviewPanel, BorderLayout.NORTH);

        setVisible(true);
    }
}
