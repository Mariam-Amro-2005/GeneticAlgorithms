package fuzzylogic.variables;

import fuzzylogic.membership.MembershipFunction;

public class FuzzySet {
    private String label;
    private MembershipFunction mf;

    public double getMembership(double x) {
        return mf.compute(x);
    }
}

