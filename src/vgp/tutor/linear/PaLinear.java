package vgp.tutor.linear;

import java.applet.Applet;
import java.awt.*;

import jv.object.*;
import jv.project.PvDisplayIf;
import jv.viewer.PvViewer;

/**
 * Applet shows simple interactive linear algebra.
 *
 * @see			jv.viewer.PvViewer
 * @author		Konrad Polthier
 * @version		04.03.01, 1.10 revised (kp) Package moved to vgp.tutor.linear from dev.app.linear.<br>
 *					26.05.00, 1.00 created (kp) 
 */
public class PaLinear extends Applet implements Runnable {
    /** frame if run standalone, null if run as applet */
    public		Frame				m_frame			= null;
    /** 3D-viewer window for graphics output and which is embedded into the applet */
    protected	PvViewer			m_viewer;

    /** Message string drawn in applet while loading. Modify string with drawMessage(). */
    private		String			m_drawString	= "Initializing ...";

    /** Interface of applet to inform about author, version, and copyright */
    public String getAppletInfo() {
        return "Name: "		+ this.getClass().getName()+ "\r\n" +
                "Author: "		+ "Konrad Polthier" + "\r\n" +
                "Version: "	+ "1.00" + "\r\n" +
                "Applet shows simple interactive linear algebra" + "\r\n";
    }

    /**
     * Configure and initialize the viewer, load system and user projects.
     * One of the user projects must be selected here.
     */
    public void init() {
        drawMessage("Loading viewer ...");
        Thread thread = new Thread(this, "inititialize applet");
        thread.setPriority(Thread.NORM_PRIORITY);
        thread.start();
    }
    public void run() {
        drawMessage("Loading display ...");
        m_viewer = new PvViewer(this, m_frame);

        drawMessage("Loading geometry ...");
        // Create and load a project
        PjLinear prj = new PjLinear();
        m_viewer.addProject(prj);
        m_viewer.selectProject(prj);

        // Get 3d display from viewer and add it to applet
        setLayout(new BorderLayout());
        PvDisplayIf disp = m_viewer.getDisplay();

        add((Component)disp, BorderLayout.CENTER);
        if ("show".equalsIgnoreCase(m_viewer.getParameter("ProjectPanel")))
            add(m_viewer.getPanel(PsViewerIf.PROJECT), BorderLayout.EAST);
        validate();

        // Choose initial panel in control window (must press F1 inside the applet)
        m_viewer.showPanel(PsViewerIf.CAMERA);

        startFromThread();
    }
    /**
     * Standalone application support. The main() method acts as the applet's
     * entry point when it is run as a standalone application. It is ignored
     * if the applet is run from within an HTML page.
     */
    public static void main(String args[]) {
        PaLinear va	= new PaLinear();
        // Create top level window of application containing the applet
        Frame frame	= new jv.object.PsMainFrame(va, args);
        frame.pack();
        frame.setBounds(new Rectangle(420, 5, 640, 550));
        va.m_frame = frame;
        va.init();
        frame.setVisible(true);
    }

    /** Set message string to be drawn in apllet while loading. */
    private void drawMessage(String message) {
        m_drawString = message;
        repaint();
    }
    /** Print info while initializing applet and viewer. */
    public void paint(Graphics g) {
        g.setColor(Color.blue);
        g.drawString(PsConfig.getProgramAndVersion(), 20, 40);
        g.drawString(m_drawString, 20, 60);
    }

    /**
     * Does clean-up when applet is destroyed by the browser.
     * Here we just close and dispose all our control windows.
     */
    public void destroy()	{ m_viewer.destroy(); }
    /** Stop viewer, e.g. stop animation if requested */
    public void stop()		{ m_viewer.stop(); }
    /**
     * Start viewer, e.g. start animation if requested.
     * Necessary, if initialization is done in a separate thread. In this case the original
     * applet.start() has no effect.
     */
    public void startFromThread()	{ if (m_viewer!=null) m_viewer.start(); }
}