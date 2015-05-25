package GMAssignment1;

import GMAssignment1.analysis.ModelAnalysis;
import jv.project.PgJvxSrc;

import javax.swing.*;
import java.awt.*;

/**
 * Created by admin on 25/5/15.
 */
public class AnalysisPanel<T extends Number> extends JFrame {
    private ModelAnalysis<T> analysis;
    private PgJvxSrc model;

    public AnalysisPanel(ModelAnalysis<T> analysis, PgJvxSrc model) {
        this.analysis = analysis;
        this.model = model;
    }
}
