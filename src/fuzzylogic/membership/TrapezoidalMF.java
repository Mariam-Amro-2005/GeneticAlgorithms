package fuzzylogic.membership;

import java.util.ArrayList;
import java.util.List;

public class TrapezoidalMF implements MembershipFunction {
    List<Double> params;

    TrapezoidalMF(double param1, double param2,  double param3,  double param4) {
        params = new ArrayList<Double>();
        params.add(param1);
        params.add(param2);
        params.add(param3);
        params.add(param4);
    }

    @Override
    public double compute(double x) {
        return 0;
    }

    @Override
    public String getName() {
        return "";
    }
}
