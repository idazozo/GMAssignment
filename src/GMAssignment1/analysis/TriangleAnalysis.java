package GMAssignment1.analysis;

import jv.geom.PgPointSet;
import jv.project.PgJvxSrc;
import jv.vecmath.PdVector;
import jv.vecmath.PiVector;

/**
 * Created by admin on 25/5/15.
 */
public abstract class TriangleAnalysis<T extends Number> implements ModelAnalysis<T> {

    @Override
    public T[] getStatistics(PgJvxSrc model)
    {
        PiVector[] triangles = getTriangles(model);
        T[] statistics = (T[])new Object[triangles.length];

        for(int i = 0; i<triangles.length; i++)
        {
            PiVector triangle = triangles[i];
            if(triangle != null) statistics[i] = analyzeTriangle(model, triangle.getEntry(0), triangle.getEntry(1), triangle.getEntry(2));
        }

        return statistics;
    }

    public abstract T analyzeTriangle(PgJvxSrc model, int a, int b, int c);

    public double perimeter(PgJvxSrc model, int a, int b, int c)
    {
        PdVector vertexA = model.getVertex(a);
        PdVector vertexB = model.getVertex(b);
        PdVector vertexC = model.getVertex(c);

        return perimeter(vertexA, vertexB, vertexC);
    }

    public double perimeter(PdVector a, PdVector b, PdVector c) {
        double distAB = a.dist(b);
        double distAC = a.dist(c);
        double distBC = b.dist(c);

        return distAB + distAC + distBC;
    }

    public PiVector[] getTriangles(PgJvxSrc model)
    {
        PiVector[] elements = model.getElements();
        PiVector[] triangles = new PiVector[elements.length];

        for( int i = 0; i < elements.length; i++)
        {
            if(elements[i].length() == 3)
            {
                triangles[i] = elements[i];
            }
        }

        return triangles;
    }

}
