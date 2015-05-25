package analysis;

import com.sun.media.sound.ModelTransform;
import jv.project.PgJvxSrc;
import jv.vecmath.PdVector;
import jv.vecmath.PiVector;

import java.util.Arrays;

/**
 * Created by admin on 25/5/15.
 */
public abstract class TriangleAnalysis<T extends Number> implements ModelAnalysis<T> {

    private PgJvxSrc model;

    public TriangleAnalysis(PgJvxSrc model) {
        this.model = model;
    }

    public PgJvxSrc getModel() {
        return model;
    }

    @Override
    public T[] getStatistics()
    {
        PiVector[] triangles = getTriangles();
        T[] statistics = (T[])new Object[triangles.length];

        for(int i = 0; i<triangles.length; i++)
        {
            PiVector triangle = triangles[i];
            if(triangle != null) statistics[i] = analyzeTriangle(triangle.getEntry(0), triangle.getEntry(1), triangle.getEntry(2));
        }

        return statistics;
    }

    public abstract T analyzeTriangle(int a, int b, int c);

    public double perimeter(int a, int b, int c)
    {
        PdVector vertexA = getModel().getVertex(a);
        PdVector vertexB = getModel().getVertex(b);
        PdVector vertexC = getModel().getVertex(c);

        return perimeter(vertexA, vertexB, vertexC);
    }

    public double perimeter(PdVector a, PdVector b, PdVector c) {
        double distAB = a.dist(b);
        double distAC = a.dist(c);
        double distBC = b.dist(c);

        return distAB + distAC + distBC;
    }

    public PiVector[] getTriangles()
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
