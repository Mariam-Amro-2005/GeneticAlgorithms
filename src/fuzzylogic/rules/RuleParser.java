package fuzzylogic.rules;

import fuzzylogic.variables.*;

import java.util.HashMap;
import java.util.Map;

public class RuleParser {

    public Rule parse(String ruleText,
                      Map<String, LinguisticVariable> variables,
                      Map<String, FuzzySet> outputSets) {

        ruleText = ruleText.trim();

        // Split into IF and THEN
        String[] parts = ruleText.split("THEN");
        String conditionPart = parts[0].replace("IF", "").trim();
        String resultPart = parts[1].trim();

        Map<LinguisticVariable, String> antecedents = new HashMap<>();

        // Handle AND conditions (extendable to OR later)
        String[] conditions = conditionPart.split("AND");
        for (String cond : conditions) {
            String[] tokens = cond.trim().split("is");
            String varName = tokens[0].trim();
            String setName = tokens[1].trim();

            antecedents.put(variables.get(varName), setName);
        }

        // Parse consequent
        String[] resultTokens = resultPart.split("is");
        String outputSetName = resultTokens[1].trim().split(" ")[0];
        FuzzySet consequent = outputSets.get(outputSetName);

        return new Rule(antecedents, consequent);
    }
}
