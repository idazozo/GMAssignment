package GMAssignment1;

import jv.project.PgJvxSrc;

import javax.swing.*;

/**
 * Created by admin on 23/5/15.
 */
public class ModelLoadedEvent {
    private JComponent source;

    private PgJvxSrc[] model;

    public ModelLoadedEvent(JComponent source, PgJvxSrc[] model) {
        this.source = source;
        this.model = model;
    }

    public PgJvxSrc[] getModel() {
        return model;
    }

    public JComponent getSource() {
        return source;
    }
}
