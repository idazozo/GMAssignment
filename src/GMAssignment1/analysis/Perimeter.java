package GMAssignment1.analysis;

import jv.project.PgJvxSrc;

import java.util.List;

/**
 * Created by admin on 25/5/15.
 */
public class Perimeter extends TriangleAnalysis<Double> {
    @Override
    public String getName() {
        return "Perimeter";
    }

    @Override
    public double analyzeTriangle(PgJvxSrc model, int a, int b, int c) {
        return perimeter(model, a, b, c);
    }
}
