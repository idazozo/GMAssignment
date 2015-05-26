package GMAssignment1.analysis.meshAnalysis;

import GMAssignment1.analysis.TriangleAnalysis;
import jv.project.PgJvxSrc;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 25/5/15.
 */
public class ShapeRegularity extends TriangleAnalysis<Double> {

    @Override
    public List<Double> analyzeTriangle(PgJvxSrc model, int a, int b, int c) {
        double distAB = model.getVertex(a).dist(model.getVertex(b));
        double distAC = model.getVertex(a).dist(model.getVertex(c));
        double distBC = model.getVertex(b).dist(model.getVertex(c));

        double p = perimeter(model, a, b, c) / 2;
        //return shape regularity
        List<Double> shapeRegularity = new LinkedList<>();
        shapeRegularity.add(distAB * distAC * distBC / (4 * (p-distAB) * (p-distAC) * (p-distBC)));

        return shapeRegularity;
    }

    @Override
    public String getName()
    {
        return "Shape Regularities";
    }
}
