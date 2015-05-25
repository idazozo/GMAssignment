package GMAssignment1.analysis;

import jv.project.PgJvxSrc;

/**
 * Created by admin on 25/5/15.
 */
public class ShapeRegularity extends TriangleAnalysis<Double> {

    public ShapeRegularity(PgJvxSrc model) {
        super(model);
    }

    @Override
    public Double analyzeTriangle(int a, int b, int c) {
        double distAB = getModel().getVertex(a).dist(getModel().getVertex(b));
        double distAC = getModel().getVertex(a).dist(getModel().getVertex(c));
        double distBC = getModel().getVertex(b).dist(getModel().getVertex(c));

        double p = perimeter(a, b, c) / 2;
        //return shape regularity
        return distAB * distAC * distBC / (4 * (p-distAB) * (p-distAC) * (p-distBC));
    }
}
