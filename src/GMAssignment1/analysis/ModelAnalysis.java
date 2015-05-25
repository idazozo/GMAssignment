package GMAssignment1.analysis;

import jv.project.PgJvxSrc;

/**
 * Created by admin on 25/5/15.
 */
public interface ModelAnalysis<T extends Number> {
    T[] getStatistics(PgJvxSrc model);
}
