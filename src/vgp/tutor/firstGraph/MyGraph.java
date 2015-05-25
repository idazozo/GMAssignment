package vgp.tutor.firstGraph;

import java.applet.Applet;
import java.awt.*;

import jv.geom.PgElementSet;
import jv.object.PsConfig;
import jv.viewer.PvDisplay;
import vgp.object.PsApplet;

/**
 * Demo applet shows own data as a graph over the x-y plane.
 * Own data is given as an array of z-values which determine
 * the height of each vertex.
 *
 * @author		Konrad Polthier
 * @version		29.12.01, 1.00 revised (kp)<br>
 *					29.12.01, 1.00 created (kp)
 */

public class MyGraph extends Applet {
    /**
     * Applet support. Configure and initialize the viewer,
     * load geometry and add display.
     */
    public void init() {
        // JavaView's configuration class needs to know if running as applet or application.
        PsConfig.init(this, null);

        // Create viewer for viewing 3d geometries, and register applet.
        PvDisplay disp = new PvDisplay();

        // Create a simple geometry. PgElementSet is the base class
        // for surfaces. See jv.geom.* for more geometry classes.
        PgElementSet geom = new PgElementSet(3);
        geom.setName("Sin-Cos Graph");
        // Compute coordinates a graph over the x-y plane.
        // Set number of lines in x and y directions.
        int numXLines = 8;
        int numYLines = 5;

        // Allocate space for vertices.
        geom.setNumVertices(numXLines*numYLines);
        // Compute the vertices.
        int ind = 0;
        for (int i=0; i<numXLines; i++) {
            double x = Math.PI*i/(numXLines-1);
            for (int j=0; j<numYLines; j++) {
                double y = Math.PI*j/(numYLines-1);
                double z = Math.sin(x)*Math.cos(y);
                geom.setVertex(ind++, x, y, z);
            }
        }

        // Compute connectivity of a rectangular mesh.
        geom.makeQuadrConn(numXLines, numYLines);

        // Why not, just assign some colors.
        geom.makeElementColorsFromXYZ();
        geom.showElementColors(true);

        // Register the geometry in the display, and make it active.
        disp.addGeometry(geom);
        // The selected geometry receives pick events. Usually one needs
        // to select a geometry only if more geometries are registered
        // in a display.
        disp.selectGeometry(geom);

        // Standard Java technique to add a component to an applet.
        setLayout(new BorderLayout());
        add(disp.getCanvas(), BorderLayout.CENTER);
    }
}