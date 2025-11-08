package fuzzylogic.inference;

import fuzzylogic.rules.RuleBase;
import fuzzylogic.variables.FuzzySet;
import fuzzylogic.variables.LinguisticVariable;

import java.util.Map;

public class SugenoInference implements InferenceEngine {

    @Override
    public Map<FuzzySet, Double> infer(Map<LinguisticVariable, Map<FuzzySet, Double>> fuzzifiedInputs, RuleBase ruleBase) {
        return Map.of();
    }
}
