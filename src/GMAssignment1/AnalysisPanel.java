package GMAssignment1;

import GMAssignment1.analysis.ModelAnalysis;
import GMAssignment1.analysis.Statistics;
import jv.geom.PgElementSet;
import jv.project.PgJvxSrc;

import javax.swing.*;
import java.awt.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by admin on 25/5/15.
 */
public class AnalysisPanel<T extends Number> extends JPanel {
    private ModelAnalysis<T> analysis;
    private PgElementSet geom;
    private JPanel overviewPanel;
    private JLabel minKey;
    private JLabel minValue;
    private JLabel maxKey;
    private JLabel maxValue;
    private JLabel meanKey;
    private JLabel meanValue;
    private JLabel standardDeviationKey;
    private JLabel standardDeviationValue;

    private JTextArea all;

    public AnalysisPanel(ModelAnalysis<T> analysis, PgElementSet geom) {
        this.analysis = analysis;
        this.geom = geom;
        setLayout(new BorderLayout());
        
        Statistics<T> stats = analysis.getStatistics(geom);

        overviewPanel = new JPanel();
        overviewPanel.setLayout(new GridLayout(4, 1));
        minKey = new JLabel("Min:");
        minValue = new JLabel("" + stats.getMin());
        maxKey = new JLabel("Max:");
        maxValue = new JLabel("" + stats.getMax());
        meanKey = new JLabel("\u03BC:");
        meanValue = new JLabel("" + stats.getMean());
        standardDeviationKey = new JLabel("\u03C3^2");
        standardDeviationValue = new JLabel("" + stats.getStandardDeviation());

        overviewPanel.add(minKey);
        overviewPanel.add(minValue);
        overviewPanel.add(maxKey);
        overviewPanel.add(maxValue);
        overviewPanel.add(meanKey);
        overviewPanel.add(meanValue);
        overviewPanel.add(standardDeviationKey);
        overviewPanel.add(standardDeviationValue);

        add(overviewPanel, BorderLayout.NORTH);

        File oldFile = new File(analysis.getName() + ".txt");

        oldFile.delete();

        try {
            PrintWriter writer = new PrintWriter(new File(analysis.getName() + ".txt"));

            for(List<T> rs : stats.getValues().values())
            {
                boolean first = true;
                for(T r : rs)
                {
                    if (!first) writer.write(", ");

                    writer.write("" + r);
                    first = false;
                }

                writer.write("\n");
            }

            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }
}
