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
public class Surface implements ModelAnalysis<Double>{

    @Override
    public Statistics<Double> getStatistics(PgElementSet geom) {
        Map<Integer, List<Double>> surface = new HashMap<>(1);
        List<Double> surfaceList = new LinkedList<>();
        surfaceList.add(geom.getArea());

        return new Statistics<> (surface);
    }

    @Override
    public String getName() {
        return "Surface Area";
    }
}
