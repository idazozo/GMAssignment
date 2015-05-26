package GMAssignment1.analysis.meshAnalysis;

import GMAssignment1.analysis.TriangleAnalysis;
import jv.geom.PgElementSet;
import jv.project.PgJvxSrc;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 25/5/15.
 */
public class ShapeRegularity extends TriangleAnalysis<Double> {

    @Override
    public List<Double> analyzeTriangle(PgElementSet geom, int a, int b, int c) {
        double distAB = geom.getVertex(a).dist(geom.getVertex(b));
        double distAC = geom.getVertex(a).dist(geom.getVertex(c));
        double distBC = geom.getVertex(b).dist(geom.getVertex(c));

        double p = perimeter(geom, a, b, c) / 2;
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
