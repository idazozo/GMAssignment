package GMAssignment1;

import GMAssignment1.analysis.ModelAnalysis;
import jv.project.PgJvxSrc;

import javax.swing.*;
import java.awt.*;

/**
 * Created by admin on 25/5/15.
 */
public class AnalysisFrame<T extends Number> extends JFrame {
    private ModelAnalysis<T> analysis;
    private PgJvxSrc model;

    public AnalysisFrame(ModelAnalysis<T> analysis, PgJvxSrc model) {
        this.analysis = analysis;
        this.model = model;

        setVisible(true);
    }
}
