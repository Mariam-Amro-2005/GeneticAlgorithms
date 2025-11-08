package fuzzylogic.fuzzification;

import fuzzylogic.variables.*;

import java.util.Map;

public interface Fuzzifier {
    Map<FuzzySet, Double> fuzzify(double crispValue, LinguisticVariable variable);
}
