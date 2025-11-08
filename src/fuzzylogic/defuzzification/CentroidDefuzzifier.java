package fuzzylogic.defuzzification;

import fuzzylogic.variables.FuzzySet;

import java.util.Map;

public class CentroidDefuzzifier implements Defuzzifier {
    @Override
    public double defuzzify(Map<FuzzySet, Double> aggregatedOutput) {
        return 0;
    }
}
