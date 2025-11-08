package fuzzylogic.fuzzification;

import fuzzylogic.variables.FuzzySet;
import fuzzylogic.variables.LinguisticVariable;

import java.util.HashMap;
import java.util.Map;

public class BasicFuzzifier implements Fuzzifier {
    @Override
    public Map<FuzzySet, Double> fuzzify(double crispValue, LinguisticVariable variable) {
        Map<FuzzySet, Double> results = new HashMap<>();
        for (FuzzySet set : variable.getSets()) {
            results.put(set, set.getMembership(crispValue));
        }
        return results;
    }
}

