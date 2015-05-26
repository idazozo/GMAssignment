package GMAssignment1;

import GMAssignment1.analysis.ModelAnalysis;
import GMAssignment1.analysis.Perimeter;
import GMAssignment1.analysis.meshAnalysis.ShapeRegularity;
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
        meshAnalyses.add(new Perimeter());
        meshAnalysisButton = new JButton("MeshAnalysis");
        meshAnalysisButton.addActionListener(new AnalysisButtonListener(meshAnalyses, this));
        statButtonPanel.add(meshAnalysisButton);

        add(statButtonPanel, BorderLayout.WEST);

        // setup display
        //viewer = new PvViewer(null, this);
        //add((Component)viewer.getDisplay(), BorderLayout.CENTER);
        display = new PvDisplay();
        add(display, BorderLayout.CENTER);
        setVisible(true);
        pack();
    }
    
    //Surface Analysis
    public int getGenus(PgElementSet geom){
        int faceCount, edgeCount, verticesCount, genusCount;
        faceCount = geom.getElements().length;
        verticesCount = geom.getVertices().length;
        edgeCount = geom.getNumEdges();
        genusCount = (2-verticesCount+edgeCount-faceCount) / 2;
        return genusCount;

    }

    public double getArea(PgElementSet geom){
        return geom.getArea();
    }

    //Mesh Analysis
    public ArrayList<Double> getEdgeLengths(PgElementSet geom){
        PiVector[] triangles = geom.getElements();
        ArrayList<Double> tempEdges = new ArrayList<Double>();
        for (int i =0;i<triangles.length;i++){
            for (int j = 0; j<3; j++) {
                tempEdges.add(getDistance(triangles[i], geom)[j]);
            }

        }
        Collections.sort(tempEdges);
        for (int j = 0; j<tempEdges.size();j++) if (j % 2 != 0) {
            tempEdges.remove(j);
        }
        return  tempEdges;

    }


    public void setColors(double[] srArray, PgElementSet geom){
        Color[] colors = new Color[srArray.length];
        double[] statistics = getStatistics(srArray);
        double min = statistics[0];
        double max = statistics[1];
        double[] newSRArray = new double[srArray.length];
        for (int i=0;i<srArray.length;i++){
            newSRArray[i] = (srArray[i] - min) / (max - min);
            colors[i] = Color.getHSBColor(1.0f, (float)newSRArray[i], 1.0f);
        }
        geom.setElementColors(colors);
        geom.showElementColors(true);
    }
    public double[][] getAngles(PgElementSet geom){
        PiVector[] triangles = geom.getElements();
        double[][] angleArray= new double[triangles.length][3];

        for (int i =0; i<triangles.length;i++){
            for (int j = 0;j<triangles[i].m_data.length;j++)
            angleArray[i][j] = geom.getVertexAngle(i,j);
        }
        return angleArray;

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
        PgPointSet geom = new PgElementSet();
        Color[] colors = IntStream
                .rangeClosed(0, model.getElements().length)
                .mapToObj(i -> Color.RED)
                .toArray(Color[]::new);

        model.setElementColors(colors);

        // Add the graph from the model
        geom.setJvx(model);
        display.addGeometry(geom);
        display.selectGeometry(geom);


        //viewer.getDisplay().addGeometry(geom);
        //viewer.getDisplay().selectGeometry(geom);
        pack();
    }

    public double[] getStatistics(double[] array) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        double sum = 0;
        double sumsq = 0;

        for(int i = 0; i < array.length; i++){
            if(array[i] < min) {
                min = array[i];
            }
            if(array[i] > max) {
                max = array[i];
            }
            sum += array[i];
        }

        double mean = sum / array.length;


        for(int i = 0; i < array.length; i++){
            sumsq += Math.pow(array[i] - mean, 2);
        }
        double std = Math.sqrt(sumsq / (array.length - 1));

        return new double[]{min, max, mean, std};
    }

    public PgJvxSrc getModel() {
        return model;
    }

    public void setModel(PgJvxSrc model) {
        this.model = model;
    }
}

