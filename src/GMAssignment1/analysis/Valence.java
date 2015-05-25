package GMAssignment1.analysis;

import jv.geom.PgElementSet;
import jv.project.PgJvxSrc;
import jv.vecmath.PiVector;

import java.util.HashMap;

/**
 * Created by admin on 25/5/15.
 */
public class Valence implements ModelAnalysis<Integer> {

    @Override
    public Statistics<Integer> getStatistics(PgJvxSrc model){
        int n = model.getVertices().length;
        HashMap<Integer, Integer> valences = new HashMap<>(n);

        for (int i = 0; i < n; i++){
            valences.put(i, model.getNeighbours()[i].getSize());
        }
        return new Statistics<Integer>(valences);
    }

    public String getName()
    {
        return "Valences";
    }
}
