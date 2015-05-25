package GMAssignment1.analysis;

import jv.project.PgJvxSrc;

/**
 * Created by admin on 25/5/15.
 */
public class ShapeRegularity extends TriangleAnalysis<Double> {

    @Override
    public Double analyzeTriangle(PgJvxSrc model, int a, int b, int c) {
        double distAB = model.getVertex(a).dist(model.getVertex(b));
        double distAC = model.getVertex(a).dist(model.getVertex(c));
        double distBC = model.getVertex(b).dist(model.getVertex(c));

        double p = perimeter(model, a, b, c) / 2;
        //return shape regularity
        return distAB * distAC * distBC / (4 * (p-distAB) * (p-distAC) * (p-distBC));
    }
}
