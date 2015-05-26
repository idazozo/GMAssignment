package GMAssignment1.analysis;

import jv.geom.PgElementSet;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 25/5/15.
 */
public class Statistics<T extends Number> {
    private Map<Integer, List<T>> values;

    private double mean;

    private double min;

    private double max;

    private double standardDeviation;

    public Statistics(Map<Integer, List<T>> values) {

        List<T> flatValues = flatValues();
        min = flatValues.stream().mapToDouble(n -> n.doubleValue()).min().orElseGet(() -> 0.0);
        max = flatValues.stream().mapToDouble(n -> n.doubleValue()).min().orElseGet(() -> 0.0);
        mean = flatValues.stream().mapToDouble(n -> n.doubleValue()).average().orElseGet(() -> 0.0);

        standardDeviation = flatValues
                .stream()
                .mapToDouble(v -> Math.pow(v.doubleValue() - mean, 2))
                .average().orElseGet(() -> 0);
    }

    public void setColors(double[] srArray, PgElementSet geom){

        Color[] colors = new Color[srArray.length];
        double[] newSRArray = new double[srArray.length];
        for (int i=0;i<srArray.length;i++){
            newSRArray[i] = (srArray[i] - min) / (max - min);
            colors[i] = Color.getHSBColor(1.0f, (float)newSRArray[i], 1.0f);
        }
        geom.setElementColors(colors);
        geom.showElementColors(true);
    }


    private List<T> flatValues()
    {
        List<T> values = new LinkedList<>();

        for(List<T> valuesPerKey : this.values.values())
        {
            values.addAll(valuesPerKey);
        }

        return values;
    }

    public Map<Integer, List<T>> getValues() {
        return values;
    }

    public double getMean() {
        return mean;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }
}
