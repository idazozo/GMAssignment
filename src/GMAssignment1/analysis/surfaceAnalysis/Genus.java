package GMAssignment1.analysis.surfaceAnalysis;

import GMAssignment1.analysis.ModelAnalysis;
import GMAssignment1.analysis.Statistics;
import jv.geom.PgElementSet;
import jv.project.PgJvxSrc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 26/5/15.
 */
public class Genus implements ModelAnalysis<Integer>{

    @Override
    public Statistics<Integer> getStatistics(PgElementSet geom) {

        int faceCount, edgeCount, verticesCount, genusCount;
        faceCount = geom.getElements().length;
        verticesCount = geom.getVertices().length;
        edgeCount = geom.getNumEdges();
        genusCount = (2-verticesCount+edgeCount-faceCount) / 2;

        Map<Integer, List<Integer>> genus = new HashMap<>(1);
        List<Integer> genusList = new LinkedList<>();
        genusList.add(genusCount);
        genus.put(0, genusList);

        return new Statistics<> (genus);
    }

    @Override
    public String getName() {
        return "Genus";
    }
}
