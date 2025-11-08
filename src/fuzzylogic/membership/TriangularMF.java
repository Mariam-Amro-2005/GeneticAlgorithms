package fuzzylogic.membership;

import java.util.ArrayList;
import java.util.List;

public class TriangularMF implements MembershipFunction {
    List<Double> params;

    TriangularMF(double param1, double param2, double param3)
    {
        params = new ArrayList<>();
        params.add(param1);
        params.add(param2);
        params.add(param3);
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
