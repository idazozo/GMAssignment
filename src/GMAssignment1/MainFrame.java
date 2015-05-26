package GMAssignment1;

import GMAssignment1.analysis.ModelAnalysis;
import GMAssignment1.analysis.meshAnalysis.Angle;
import GMAssignment1.analysis.meshAnalysis.EdgeLength;
import GMAssignment1.analysis.meshAnalysis.ShapeRegularity;
import GMAssignment1.analysis.meshAnalysis.Valence;
import GMAssignment1.analysis.surfaceAnalysis.Genus;
import GMAssignment1.analysis.surfaceAnalysis.Surface;
import GMAssignment1.visualization.ColorFaces;
import jv.geom.PgElementSet;
import jv.geom.PgPointSet;
import jv.project.PgJvxSrc;
import jv.vecmath.PiVector;
import jv.viewer.PvDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by admin on 23/5/15.
 */
public class MainFrame extends JFrame implements ActionListener, ModelLoadedListener {

    private JPanel buttonPanel;
    private JPanel statButtonPanel;
    private JButton loadFileButton;

    // statistic buttons
    private JButton meshAnalysisButton;
    private JButton surfaceAnalysisButton;

    //private PvViewer viewer;
    private PvDisplay display;
    private PgJvxSrc model;

    public MainFrame()
    {
        super("Application"); // Set the title
        setSize(400, 400); // Set size to the frame
        setLayout(new BorderLayout()); // Set the layout
        setLocationRelativeTo(null);  // Center the frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // setup loading ui part
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        loadFileButton = new JButton("Load Model");
        loadFileButton.addActionListener(this);
        buttonPanel.add(loadFileButton);

        add(buttonPanel, BorderLayout.NORTH);

        // setup stat button
        statButtonPanel = new JPanel();
        statButtonPanel.setLayout(new GridLayout(6, 1));

        List<ModelAnalysis> meshAnalyses = new LinkedList<>();
        meshAnalyses.add(new ShapeRegularity());
        meshAnalyses.add(new Valence());
        meshAnalyses.add(new Angle());
        meshAnalyses.add(new EdgeLength());

        meshAnalysisButton = new JButton("MeshAnalysis");
        meshAnalysisButton.addActionListener(new AnalysisButtonListener(meshAnalyses, this));
        statButtonPanel.add(meshAnalysisButton);

        List<ModelAnalysis> surfaceAnalyses = new LinkedList<>();
        surfaceAnalyses.add(new Genus());
        surfaceAnalyses.add(new Surface());

        surfaceAnalysisButton = new JButton("SurfaceAnalysis");
        surfaceAnalysisButton.addActionListener(new AnalysisButtonListener(surfaceAnalyses, this));
        statButtonPanel.add(surfaceAnalysisButton);

        add(statButtonPanel, BorderLayout.WEST);

        // setup display
        //viewer = new PvViewer(null, this);
        //add((Component)viewer.getDisplay(), BorderLayout.CENTER);
        display = new PvDisplay();
        add(display, BorderLayout.CENTER);
        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(loadFileButton))
        {
            LoadModelFrame loadModelFrame = new LoadModelFrame();
            loadModelFrame.addModelLoadedListener(this);
        }
    }

    @Override
    public void modelLoaded(ModelLoadedEvent e)
    {
        PgJvxSrc model = e.getModels()[0];
        setModel(model);
        PgElementSet geom = new PgElementSet();

        // Add the graph from the model
        new ColorFaces().visualize(model);

        geom.setJvx(model);
        display.addGeometry(geom);
        display.selectGeometry(geom);

        display.repaint();
    }

    public PgJvxSrc getModel() {
        return model;
    }

    public void setModel(PgJvxSrc model) {
        this.model = model;
    }
}

