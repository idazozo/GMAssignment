package GMAssignment1;

import GMAssignment1.analysis.ModelAnalysis;
import GMAssignment1.analysis.TriangleAnalysis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by admin on 25/5/15.
 */
public class AnalysisButtonListener implements ActionListener {
    private List<ModelAnalysis> analyses;
    private MainFrame frame;

    public AnalysisButtonListener(List<ModelAnalysis> analyses, MainFrame frame) {
        this.analyses = analyses;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new AnalysesFrame(analyses);
    }
}
