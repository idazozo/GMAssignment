package GMAssignment1.analysis;

import jv.project.PgJvxSrc;

/**
 * Created by admin on 25/5/15.
 */
public class Perimeter extends TriangleAnalysis<Double> {
    @Override
    public String getName() {
        return "Perimeter";
    }

    @Override
    public Double analyzeTriangle(PgJvxSrc model, int a, int b, int c) {
        return perimeter(model, a, b, c);
    }
}
