package GMAssignment1.analysis;

import jv.geom.PgElementSet;
import jv.project.PgJvxSrc;
import jv.vecmath.PdVector;
import jv.vecmath.PiVector;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 25/5/15.
 */
public abstract class TriangleAnalysis<T extends Number> implements ModelAnalysis<T> {

    @Override
    public Statistics<T> getStatistics(PgElementSet geom)
    {
        PiVector[] triangles = getTriangles(geom);
        Map<Integer, List<T>> statistics = new HashMap<Integer, List<T>>(triangles.length);

        for(int i = 0; i<triangles.length; i++)
        {
            PiVector triangle = triangles[i];
            if(triangle != null) statistics.put(i, analyzeTriangle(geom, triangle.getEntry(0), triangle.getEntry(1), triangle.getEntry(2)));
        }

        return new Statistics<>(statistics);
    }

    public abstract List<T> analyzeTriangle(PgElementSet geom, int a, int b, int c);

    public double perimeter(PgElementSet geom, int a, int b, int c)
    {
        PdVector vertexA = geom.getVertex(a);
        PdVector vertexB = geom.getVertex(b);
        PdVector vertexC = geom.getVertex(c);

        return perimeter(vertexA, vertexB, vertexC);
    }

    public double perimeter(PdVector a, PdVector b, PdVector c) {
        double distAB = a.dist(b);
        double distAC = a.dist(c);
        double distBC = b.dist(c);

        return distAB + distAC + distBC;
    }

    public PiVector[] getTriangles(PgElementSet model)
    {
        PiVector[] elements = model.getElements();
        PiVector[] triangles = new PiVector[elements.length];

        for( int i = 0; i < elements.length; i++)
        {
            if(elements[i].getEntries().length == 3)
            {
                triangles[i] = elements[i];
            }
        }

        return triangles;
    }

}
