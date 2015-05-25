package GMAssignment1;

import GMAssignment1.analysis.ModelAnalysis;
import GMAssignment1.analysis.ShapeRegularity;
import GMAssignment1.analysis.TriangleAnalysis;
import jv.geom.PgEdgeStar;
import jv.geom.PgElementSet;
import jv.geom.PgPointSet;
import jv.object.PsMainFrame;
import jv.project.PgJvxSrc;
import jv.vecmath.PdVector;
import jv.vecmath.PiVector;
import jv.viewer.PvDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;
import java.util.Arrays;
/**
 * Created by admin on 23/5/15.
 */
public class MainFrame extends PsMainFrame implements ActionListener, ModelLoadedListener {

    private JPanel buttonPanel;
    private JPanel statButtonPanel;
    private JButton loadFileButton;

    // statistic buttons
    private JButton valancesButton;
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

        valancesButton = new JButton("Valances");
        statButtonPanel.add(valancesButton);

        List<ModelAnalysis> meshAnalyses = new LinkedList<>();
        meshAnalyses.add(new ShapeRegularity());
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

    public void analyzeModel(PgJvxSrc model){
        PiVector[] triangles = model.getElements();
        double[] shapeRegularityArray = new double[triangles.length];
        for (int j = 0; j < triangles.length;j++) {

            PdVector[] triangleVertices = new PdVector[3];
            for (int i = 0; i < triangles[j].m_data.length; i++) {
                triangleVertices[i] = model.getVertex(triangles[j].m_data[i]);
            }
            shapeRegularityArray[j]= shapeRegularity(triangleVertices);
        }
        double[] statisticsArray = getStatistics(shapeRegularityArray);
        System.out.println(Arrays.toString(statisticsArray));
    }
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
    public double[] getDistance(PiVector triangle, PgElementSet geom){
        double dist1, dist2, dist3;
        double[] triangleEdgeLengths = new double[3];
        dist1 = geom.getVertex(triangle.getEntry(0)).dist(geom.getVertex(triangle.getEntry(1)));
        dist2 = geom.getVertex(triangle.getEntry(0)).dist(geom.getVertex(triangle.getEntry(2)));
        dist3 = geom.getVertex(triangle.getEntry(1)).dist(geom.getVertex(triangle.getEntry(2)));
        triangleEdgeLengths[0] = dist1;
        triangleEdgeLengths[1] = dist2;
        triangleEdgeLengths[2] = dist3;
        return triangleEdgeLengths;
    }

    public double[] valences(PgElementSet geom){

        PiVector  valenceGeom = PgElementSet.getVertexValence(geom);
        double[] valenceArray = new double[valenceGeom.length()];
        for (int i =0; i<valenceGeom.length(); i++){
            valenceArray[i] = valenceGeom.getEntry(i);
        }
        return valenceArray;
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
    public static double shapeRegularity(PdVector [] triangle){
        double distAB = triangle[0].dist(triangle[1]);
        double distAC = triangle[0].dist(triangle[2]);
        double distBC = triangle[1].dist(triangle[2]);

        double p = perimeter( triangle[0],  triangle[1],  triangle[2]) / 2;
        //return shape regularity
        return distAB * distAC * distBC / (4 * (p-distAB) * (p-distAC) * (p-distBC));
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
        analyzeModel(model);
        display.addGeometry(geom);
        display.selectGeometry(geom);


        //viewer.getDisplay().addGeometry(geom);
        //viewer.getDisplay().selectGeometry(geom);
        pack();
    }

    public static double perimeter(PdVector a, PdVector b, PdVector c) {
        double distAB = a.dist(b);
        double distAC = a.dist(c);
        double distBC = b.dist(c);

        return distAB + distAC + distBC;
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

