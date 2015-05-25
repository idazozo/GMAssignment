package GMAssignment1;

import GMAssignment1.analysis.ModelAnalysis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by admin on 25/5/15.
 */
public class AnalysisButtonListener<T extends Number> implements ActionListener {
    private ModelAnalysis<T> analysis;
    private MainFrame frame;

    public AnalysisButtonListener(ModelAnalysis<T> analysis, MainFrame frame) {
        this.analysis = analysis;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new AnalysisFrame<T>(analysis, frame.getModel());
    }
}
