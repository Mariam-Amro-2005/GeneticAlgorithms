package fuzzylogic.rules;

import fuzzylogic.variables.FuzzySet;
import fuzzylogic.variables.LinguisticVariable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RuleBase {
    private final List<Rule> rules = new ArrayList<>();

    public void addRule(Rule rule) {
        rules.add(rule);
    }

    public void removeRule(int index) {
        rules.remove(index);
    }

    public void enableRule(int index) {
        rules.get(index).setEnabled(true);
    }

    public void disableRule(int index) {
        rules.get(index).setEnabled(false);
    }

    public List<Rule> getEnabledRules() {
        return rules.stream().filter(Rule::isEnabled).toList();
    }

    // Simple persistence (JSON, TXT, etc)
    public void saveToFile(String path) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Rule r : rules) {
                writer.write(r.toString());
                writer.newLine();
            }
        }
    }

    public void loadFromFile(String path, RuleParser parser,
                             Map<String, LinguisticVariable> vars,
                             Map<String, FuzzySet> outputs) throws IOException {
        rules.clear();
        for (String line : Files.readAllLines(Paths.get(path))) {
            rules.add(parser.parse(line, vars, outputs));
        }
    }
}

