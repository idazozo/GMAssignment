package GMAssignment1.analysis.meshAnalysis;

import GMAssignment1.analysis.TriangleAnalysis;
import jv.geom.PgElementSet;
import jv.project.PgJvxSrc;
import jv.vecmath.PdVector;
import jv.vecmath.PiVector;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 25/5/15.
 */
public class Angle extends TriangleAnalysis<Double> {

    @Override
    public List<Double> analyzeTriangle(PgElementSet geom, int a, int b, int c) {
        List<Double> angles = new LinkedList<>();

        PdVector vA = geom.getVertex(a);
        PdVector vB = geom.getVertex(b);
        PdVector vC = geom.getVertex(c);

        angles.add(PdVector.angle(vA, vB, vC));
        angles.add(PdVector.angle(vB, vA, vC));
        angles.add(PdVector.angle(vC, vA, vB));

        return angles;
    }

    @Override
    public String getName()
    {
        return "Angles";
    }
}
