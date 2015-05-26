package GMAssignment1.analysis.meshAnalysis;

import GMAssignment1.analysis.TriangleAnalysis;
import jv.geom.PgElementSet;
import jv.project.PgJvxSrc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 26/5/15.
 */
public class EdgeLength extends TriangleAnalysis<Double> {

    @Override
    public List<Double> analyzeTriangle(PgElementSet geom, int a, int b, int c) {
        List<Double> edgeLengths = new ArrayList<>(3);
        double dist1 = geom.getVertex(a).dist(geom.getVertex(b));
        double dist2 = geom.getVertex(a).dist(geom.getVertex(c));
        double dist3 = geom.getVertex(b).dist(geom.getVertex(c));

        edgeLengths.add(dist1);
        edgeLengths.add(dist2);
        edgeLengths.add(dist3);

        return edgeLengths;
    }

    @Override
    public String getName() {
        return "Length of edges";
    }
}
