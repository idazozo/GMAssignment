package GMAssignment1.analysis;

import java.util.Map;

/**
 * Created by admin on 25/5/15.
 */
public class Statisticts<T extends Number> {
    private Map<Integer, T> values;

    private double mean;

    private double min;

    private double max;

    private double standardDeviation;

    public Statisticts(Map<Integer, T> values) {
        this.values = values;

        min = Double.MAX_VALUE;
        max = Double.MIN_VALUE;
        double sum = 0;
        double sumsq = 0;

        for(int i = 0; i < values.size(); i++){
            if(values.get(i).doubleValue() < min) {
                min = values.get(i).doubleValue();
            }
            if(values.get(i).doubleValue() > max) {
                max = values.get(i).doubleValue();
            }
            sum += values.get(i).doubleValue();
        }

        mean = sum / values.size();


        for(int i = 0; i < values.size(); i++){
            sumsq += Math.pow(values.get(i).doubleValue() - mean, 2);
        }
        standardDeviation = Math.sqrt(sumsq / (values.size() - 1));
    }

    public Map<Integer, T> getValues() {
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
