package GMAssignment1.analysis;

import jv.project.PgJvxSrc;

import java.util.Map;

/**
 * Created by admin on 25/5/15.
 */
public interface ModelAnalysis<T extends Number> {
    Statistics<T> getStatistics(PgJvxSrc model);
    String getName();
}
