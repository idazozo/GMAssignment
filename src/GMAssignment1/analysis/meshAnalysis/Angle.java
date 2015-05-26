package GMAssignment1.analysis.meshAnalysis;

import GMAssignment1.analysis.TriangleAnalysis;
import jv.geom.PgElementSet;
import jv.project.PgJvxSrc;
import jv.vecmath.PiVector;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 25/5/15.
 */
public class Angle extends TriangleAnalysis<Double> {

    @Override
    public List<Double> analyzeTriangle(PgJvxSrc model, int a, int b, int c) {
        List<Double> angles = new LinkedList<>();

        PgElementSet geom = new PgElementSet();
        angles.add(geom.getVertexAngle(a, b));
        angles.add(geom.getVertexAngle(a, c));
        angles.add(geom.getVertexAngle(b, c));

        return angles;
    }

    @Override
    public String getName()
    {
        return "Angles";
    }
}
