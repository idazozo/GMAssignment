package GMAssignment1;

import jv.project.PgJvxSrc;

import javax.swing.*;

/**
 * Created by admin on 23/5/15.
 */
public class ModelLoadedEvent {
    private Object source;

    private PgJvxSrc[] models;

    public ModelLoadedEvent(Object source, PgJvxSrc[] models) {
        this.source = source;
        this.models = models;
    }

    public PgJvxSrc[] getModels() {
        return models;
    }

    public Object getSource() {
        return source;
    }
}
