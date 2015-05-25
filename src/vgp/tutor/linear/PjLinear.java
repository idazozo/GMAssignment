package vgp.tutor.linear;

import java.awt.Color;

import jv.object.PsDebug;
import jv.geom.PgPolygon;
import jv.geom.PgPolygonSet;
import jv.project.PvCameraIf;
import jv.project.PvDisplayIf;
import jv.project.PjProject;
import jv.vecmath.PdVector;
import jv.vecmath.PiVector;

/**
 * Simple interactive linear algebra applet performs vector calculations
 * on two vectors and shows the result vector.
 * <p>
 * The result vector has 3D coordinates because of the possible cross product mode.
 * Nevertheless, the dimension of the two argument vectors has been chosen 2D
 * since then the vectors always lie in the xy-plane in the display, even
 * if the camera is in perspective mode. This makes the dragging of the argument
 * vectors in cross product mode more intuitive. Note, the method computeResult()
 * may be simplified a little if one chooses 3D coordinates for all vectors.
 *
 * @author		Konrad Polthier
 * @version		05.11.02, 1.30 revised (ep) Use perspective camera for cross product.<br>
 *					04.03.01, 1.20 revised (kp) Converted into a tutorial, and moved to vgp.tutor.<br>
 *					26.11.00, 1.10 revised (kp) Use global instead of local polygon colors.<br>
 *					22.10.00, 1.00 created (kp)
 */
public class PjLinear extends PjProject {
    /** Summation mode, result is sum of two argument vectors. */
    public		static final int		MODE_ADD			= 0;
    /** Difference mode, result is difference of two argument vectors. */
    public		static final int		MODE_SUB			= 1;
    /** Cross product mode, result is cross product of two argument vectors. */
    public		static final int		MODE_CROSS		= 2;
    /**
     * Two argument vectors are represented as two lines of a polygon set
     * determined by their endpoints. The base point of both vectors is
     * identical, namely, the first vertex of the polygon set.
     * The second and third vertex of the polygon set are the endpoints
     * of both vertices.
     */
    protected	PgPolygonSet			m_vectors;
    /**
     * The result vector is a single polygon. The base point of this vector
     * is kept equal to the base point of the two argument vectors, and
     * its endpoint is computed from the two argument vectors depending
     * on the current mode, i.e. either the sum, difference or cross product.
     */
    protected	PgPolygon				m_result;
    /** Computation mode, possibly values are MODE_... constants. */
    protected	int						m_mode;

    /**
     * Constructors creates two argument vectors and a result vector. Initializations
     * of instance variables should be performed within the init() method
     * to allow resetting the project later by simply calling the init() method.
     */
    public PjLinear() {
        super("Interactive Linear Algebra");					// Super class constructors assigns name of this project.
        // Create a new polygon set in 2D space to store the two argument vectors.
        m_vectors	= new PgPolygonSet(2);
        m_vectors.setName("2 Vectors");							// Set unique name of polygon set.
        // Create a new polygon in 3D space to store the result vector.
        m_result		= new PgPolygon(3);
        m_result.setName("Result");								// Set unique name of polygon
        if (getClass() == PjLinear.class)						// Invoke the init() method. This construct refuses to call
            init();														// the init method if this constructor is invoked by a subclass.
    }
    /**
     * Initialize all instance variables of this project. This method
     * is invoked during the construction of this project and whenever
     * this project is resetted. Therefore, allocations of instance variables
     * should be performed in the constructor of the project.
     */
    public void init() {
        super.init();
        m_vectors.init();												// Initialize the argument vectors.
        m_vectors.setNumVertices(3);								// Allocate three vertices for basepoint and two endpoints
        m_vectors.setVertex(0, 0., 0.);							// Base point (index = 0) of both vectors
        m_vectors.setVertex(1, .5, 1.);							// Endpoint (index = 1) of first vector
        m_vectors.setVertex(2, -2., 2.);							// Endpoint (index = 2) of second vector
        m_vectors.setNumPolygons(2);								// Allocate two lines being the two argument vectors
        // Determine the index of the basepoint (0) and endpoint (1) of the first vector
        m_vectors.setPolygon(0, new PiVector(0, 1));
        // Determine the index of the basepoint (0) and endpoint (2) of the second vector
        m_vectors.setPolygon(1, new PiVector(0, 2));
        m_vectors.getPolygon(0).setName("v");					// Set name of first argument vector
        m_vectors.getPolygon(1).setName("w");					// Set name of second argument vector
        m_vectors.showPolygonLabels(true);						// Show name of argument vectors at tip
        m_vectors.setGlobalPolygonColor(Color.blue);			// Set color of argument vectors
        m_vectors.setGlobalPolygonSize(2.);						// Set thickness of argument vectors
        m_vectors.showPolygonEndArrow(true);
        m_vectors.setParent(this);									// Register the argument vectors as children
        // allows to catch their update events in update(Object)
        // and initiate recomputation of the result vector.
        m_result.setNumVertices(2);								// Allocate place for both endpoints of result vector.
        m_result.setGlobalEdgeColor(Color.red);				// Set color of result vector
        m_result.setGlobalEdgeSize(2.);							// Set thickness of result vector
        m_result.showPolygonEndArrow(true);						// Enable drawing of arrow of result vector

        setMode(MODE_ADD);											// Set initial computation mode.
    }
    public void start() {
        computeResult(m_mode);										// Compute result vector
        addGeometry(m_result);										// Register result vector in display
        addGeometry(m_vectors);										// Register two argument vectors in display
        selectGeometry(m_vectors);									// Set two vectors as the active geometry in display
        // to be able to pick their vertices.
        PvDisplayIf disp = getDisplay();							// Get the display from this project
        if (disp != null) {											// and set some display options.
            disp.showGrid(true);										// Show grid in the background
            disp.selectCamera(PvCameraIf.CAMERA_ORTHO_XY);	// Initially, project onto xy-plane
            disp.setMajorMode(PvDisplayIf.MODE_PICK);			// Force picking of polygon vertices
        }
        super.start();
    }
    /** Get the current computation mode, possibly values are MODE_... constants. */
    public int getMode() { return m_mode; }
    /**
     * Set the current computation mode, possibly values are MODE_... constants.
     * Method also recomputes the result vector and initiates a redraw.
     */
    public int setMode(int mode) {
        m_mode = mode;

        PvDisplayIf disp = getDisplay();							// Get the display from this project
        if (m_mode == MODE_CROSS && disp != null)				// and if we are showing cross product
            disp.selectCamera(PvCameraIf.CAMERA_PERSPECTIVE);// then set camera to perspective mode
        else if (disp != null)																// otherwise
            disp.selectCamera(PvCameraIf.CAMERA_ORTHO_XY);  // we want xy-projection mode

        computeResult(m_mode);
        m_result.update(m_result);
        return m_mode;
    }
    /**
     * Perform vector arithmetic on two argument vectors to compute the result vector.
     * This method could be made simpler if one uses 3D coordinates for
     * argument vectors too.
     */
    public void computeResult(int mode) {
        // Get base point of all vectors (creating a copy allows to resize base below)
        PdVector base = PdVector.copyNew(m_vectors.getVertex(0));
        // Extract first argument vector from vertices of polygon set
        PdVector v = PdVector.subNew(m_vectors.getVertex(1), base);
        // Extract second argument vector from vertices of polygon set
        PdVector w = PdVector.subNew(m_vectors.getVertex(2), base);

        // Resize base and argument vectors to have same dimension 3 as result vector
        // since vector arithmetic methods often require same length of arguments.
        base.setSize(3);
        v.setSize(3);
        w.setSize(3);
        PdVector vec = new PdVector(3);
        switch (m_mode) {
            case MODE_ADD:
                vec.add(v, w);
                break;
            case MODE_SUB:
                vec.sub(v, w);
                break;
            case MODE_CROSS:
                vec.cross(v, w);
                break;
            default:
                if (PsDebug.WARNING) PsDebug.warning("unknown mode = "+m_mode);
                return;
        }
        // Set base point of result vector
        m_result.setVertex(0, base);
        // Set endpoint of result vector as sum of base and computed vector
        vec.add(base);
        m_result.setVertex(1, vec);
    }
    /**
     * Perform recomputations whenever a child has changed. Here the two argument
     * vectors have registered this class as parent, therefore, we are able to catch
     * their update events and recompute the result vectors if they have changed.
     * Method is usually invoked from the children.
     */
    public boolean update(Object event) {
        if (event == m_vectors) {									// If the two argument vector have changed...
            computeResult(m_mode);									// Recompute the result vector based on the current mode.
            m_result.update(m_result);								// Redraw result vector in display.
            return true;												// Event successfully handled, just return.
        }
        // If we do not know about the event then just forward it to the superclass.
        return super.update(event);
    }
}