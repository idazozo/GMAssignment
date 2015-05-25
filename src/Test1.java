import java.awt.*;
import java.awt.print.PageFormat;

import jv.vecmath.PdVector;
import jv.vecmath.PiVector;

public class Test1 {
    double[] m_data;

    void PdVector(int dim) {
        m_data = new double[dim];
    }

    void PdVector(double x, double y) {
        m_data = new double[]{x, y};
    }

    double getEntry(int ind) {
        return m_data[ind];
    }

    public static void main(String[] args) {
        PdVector v = new PdVector(1., 0.);
        PdVector w = new PdVector(0., 1.);
        v.add(w);
        double norm = v.length();
        System.out.println(norm);
    }
}