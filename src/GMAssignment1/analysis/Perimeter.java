package GMAssignment1.analysis;

import jv.project.PgJvxSrc;

import java.util.LinkedList;
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
    public List<Double> analyzeTriangle(PgJvxSrc model, int a, int b, int c) {

        List<Double> perimeter = new LinkedList<>();
        perimeter.add(perimeter(model, a, b, c));

        return perimeter;
    }
}
