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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by admin on 23/5/15.
 */
public class MainFrame extends PsMainFrame implements ActionListener, ModelLoadedListener {

    private JPanel buttonPanel;
    private JButton loadFileButton;
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

    public void analyzeModel(PgJvxSrc model){
        PiVector[] polygons = model.getElements();

        HashMap<Integer, PiVector> triangles = new HashMap<Integer, PiVector>(polygons.length);

        for(int i = 0; i < polygons.length; i++)
        {
            if(polygons[i].length() == 3)
            {
                triangles.put(i, polygons[i]);
            }
        }

        Map<Integer, Double> shapeRegularities = triangles
            .entrySet()
            .stream()
            .map(entry -> {
                PiVector t = entry.getValue();
                PdVector a = model.getVertex(t.getEntry(0));
                PdVector b = model.getVertex(t.getEntry(1));
                PdVector c = model.getVertex(t.getEntry(2));
                return new HashMap.SimpleImmutableEntry<Integer, Double>(entry.getKey(), shapeRegularity(a, b, c));
            }).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

                        // calculate the smallest shape regularity
        Optional < Map.Entry<Integer, Double> > smallestRegularityIndex = shapeRegularities
                .entrySet()
                .stream()
                .min((a, b) -> a.getValue().compareTo(b.getValue()));
        smallestRegularityIndex.isPresent(); // is there any?
        //smallestRegularityIndex.get(); // the value, if it's present. Otherwise throws null pointer
        //smallestRegularityIndex.get().getKey(); // the index in triangles if it exists. use triangles.get(i)

    }

    public static double shapeRegularity(PdVector a, PdVector b, PdVector c){
        double distAB = a.dist(b);
        double distAC = a.dist(c);
        double distBC = b.dist(c);

        double p = perimeter(a, b, c) / 2;
        double shapeReg = distAB * distAC * distBC / (4 * (p-distAB) * (p-distAC) * (p-distBC));

        return shapeReg;
    }

    public static double perimeter(PdVector a, PdVector b, PdVector c) {
        double distAB = a.dist(b);
        double distAC = a.dist(c);
        double distBC = b.dist(c);

        return distAB + distAC + distBC;
    }
}
