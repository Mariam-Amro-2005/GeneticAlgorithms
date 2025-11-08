package fuzzylogic.variables;

import java.util.List;

public class LinguisticVariable {
    private String name;
    private double domainStart, domainEnd;
    private List<FuzzySet> sets;

    public String getName() {
        return name;
    }

    public double getDomainStart() {
        return domainStart;
    }

    public double getDomainEnd() {
        return domainEnd;
    }

    public List<FuzzySet> getSets() {
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDomainStart(double domainStart) {
        this.domainStart = domainStart;
    }

    public void setDomainEnd(double domainEnd) {
        this.domainEnd = domainEnd;
    }

    public void setSets(List<FuzzySet> sets) {
        this.sets = sets;
    }

    public FuzzySet getFuzzySetByName(String label) {
        return null;
    }
}
