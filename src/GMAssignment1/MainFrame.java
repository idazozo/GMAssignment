package GMAssignment1;

import jv.geom.PgElementSet;
import jv.geom.PgPointSet;
import jv.object.PsMainFrame;
import jv.project.PgJvxSrc;
import jv.vecmath.PdVector;
import jv.vecmath.PiVector;
import jv.viewer.PvDisplay;
import jv.viewer.PvViewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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

        meshAnalysisButton = new JButton("MeshAnalysis");
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
//        public void analyzeModel(PgJvxSrc model){
//        PiVector[] polygons = model.getElements();
//
//        HashMap<Integer, PiVector> triangles = new HashMap<Integer, PiVector>(polygons.length);
//
//        for(int i = 0; i < polygons.length; i++)
//        {
//            if(polygons[i].length() == 3)
//            {
//                triangles.put(i, polygons[i]);
//            }
//        }
//
//        Map<Integer, Double> shapeRegularities = triangles
//            .entrySet()
//            .stream()
//            .map(entry -> {
//                PiVector t = entry.getValue();
//                PdVector a = model.getVertex(t.getEntry(0));
//                PdVector b = model.getVertex(t.getEntry(1));
//                PdVector c = model.getVertex(t.getEntry(2));
//                return new HashMap.SimpleImmutableEntry<Integer, Double>(entry.getKey(), shapeRegularity(a, b, c));
//            }).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
//
//                        // calculate the smallest shape regularity
//        Optional < Map.Entry<Integer, Double> > smallestRegularityIndex = shapeRegularities
//                .entrySet()
//                .stream()
//                .min((a, b) -> a.getValue().compareTo(b.getValue()));
//        smallestRegularityIndex.isPresent(); // is there any?
//        //smallestRegularityIndex.get(); // the value, if it's present. Otherwise throws null pointer
//        //smallestRegularityIndex.get().getKey(); // the index in triangles if it exists. use triangles.get(i)
//
//        Map<Integer, Integer> valences = IntStream.rangeClosed(0, model.getVertices().length)
//                .mapToObj(us -> {
//                    return new HashMap.SimpleImmutableEntry<Integer, Integer>(
//                            us,
//                            model.getNeighbours()[us].getSize());
//                })
//        .collect(Collectors.toMap(
//                Map.Entry::getKey,
//                Map.Entry::getValue));
//
//
//        System.out.println(valences);
//    }
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

        return new double[]{mean, min, max, std};
    }
}

