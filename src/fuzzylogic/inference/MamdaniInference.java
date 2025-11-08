package fuzzylogic.inference;

import fuzzylogic.operators.implications.Implication;
import fuzzylogic.operators.snorms.SNorm;
import fuzzylogic.operators.tnorms.TNorm;
import fuzzylogic.rules.Rule;
import fuzzylogic.rules.RuleBase;
import fuzzylogic.variables.FuzzySet;
import fuzzylogic.variables.LinguisticVariable;

import java.util.HashMap;
import java.util.Map;

public class MamdaniInference implements InferenceEngine {

    private final TNorm andOperator;
    private final SNorm orOperator;
    private final Implication implication;
    private final SNorm aggregation;

    public MamdaniInference(TNorm andOperator, SNorm orOperator,
                            Implication implication, SNorm aggregation) {
        this.andOperator = andOperator;
        this.orOperator = orOperator;
        this.implication = implication;
        this.aggregation = aggregation;
    }

    @Override
    public Map<FuzzySet, Double> infer(
            Map<LinguisticVariable, Map<FuzzySet, Double>> fuzzifiedInputs,
            RuleBase ruleBase
    ) {
        Map<FuzzySet, Double> outputMap = new HashMap<>();

        for (Rule rule : ruleBase.getEnabledRules()) {
            double ruleStrength = 1.0;

            // ---- Evaluate Rule Antecedent (IF part) ----
            for (var entry : rule.getAntecedents().entrySet()) {
                LinguisticVariable var = (LinguisticVariable) entry.getKey();
                String label = entry.getValue().toString();

                double membership = fuzzifiedInputs
                        .get(var)
                        .get(var.getFuzzySetByName(label));

                ruleStrength = andOperator.apply(ruleStrength, membership);
            }

            // ---- Apply Implication to Consequent ----
            FuzzySet outputSet = rule.getConsequent();
            double impliedValue = implication.apply(ruleStrength, 1.0);

            // ---- Aggregate results (combine with existing) ----
            outputMap.put(outputSet,
                    aggregation.apply(outputMap.getOrDefault(outputSet, 0.0), impliedValue));
        }

        return outputMap;
    }
}

