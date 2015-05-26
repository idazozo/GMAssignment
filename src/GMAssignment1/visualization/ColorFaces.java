package GMAssignment1.visualization;

import GMAssignment1.analysis.Statistics;
import GMAssignment1.analysis.meshAnalysis.ShapeRegularity;
import jv.geom.PgElementSet;
import jv.project.PgJvxSrc;

import java.awt.*;

/**
 * Created by admin on 26/5/15.
 */
public class ColorFaces implements Visualisation {
    @Override
    public void visualize(PgJvxSrc model) {
        PgElementSet geom = new PgElementSet();
        geom.setJvx(model);

        Statistics<Double> statistics = new ShapeRegularity().getStatistics(geom);
        int n = statistics.getValues().size();
        float mean = new Double(statistics.getMean()).floatValue();
        float s = new Double(statistics.getStandardDeviation()).floatValue();
        float min = mean - s;
        float max = mean + s;
        Color[] colors = new Color[n];

        for (int i = 0; i < n; i++){
            float value = statistics.getValues().get(i).get(0).floatValue();
            float normailzedColor = Math.min(1, Math.max(0, (value - min) / (max - min)));
            colors[i] = new Color(normailzedColor, 0, 1 - normailzedColor);
            //colors[i] = Color.getHSBColor(normailzedColor, 1.0f, 1.0f);
        }

        model.showElementColors(true);
        model.setElementColors(colors);
    }
}