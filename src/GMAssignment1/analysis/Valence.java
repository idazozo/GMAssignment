package GMAssignment1.analysis;

import jv.geom.PgElementSet;
import jv.project.PgJvxSrc;
import jv.vecmath.PiVector;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 25/5/15.
 */
public class Valence implements ModelAnalysis<Integer> {

    @Override
    public Statistics<Integer> getStatistics(PgJvxSrc model){

        int n = model.getVertices().length;
        HashMap<Integer, List<Integer>> valences = new HashMap<>(n);

        for (int i = 0; i < n; i++){
            List<Integer> valence = new LinkedList<>();
            valence.add(model.getNeighbours()[i].getSize());
            valences.put(i, valence);
        }

        return new Statistics<Integer>(valences);
    }

    public String getName()
    {
        return "Valences";
    }
}
