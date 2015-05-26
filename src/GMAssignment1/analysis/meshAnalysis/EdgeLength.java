package GMAssignment1.analysis.meshAnalysis;

import GMAssignment1.analysis.TriangleAnalysis;
import jv.project.PgJvxSrc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 26/5/15.
 */
public class EdgeLength extends TriangleAnalysis<Double> {
    @Override
    public List<Double> analyzeTriangle(PgJvxSrc model, int a, int b, int c){
        double dist1, dist2, dist3;
        List<Double> edgeLengths = new ArrayList<>(3);
        dist1 = model.getVertex(a).dist(model.getVertex(b));
        dist2 = model.getVertex(a).dist(model.getVertex(c));
        dist3 = model.getVertex(b).dist(model.getVertex(c));

        return edgeLengths;
    }

    @Override
    public String getName() {
        return "Length of edges";
    }
}
