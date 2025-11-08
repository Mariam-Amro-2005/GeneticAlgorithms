package fuzzylogic.membership;

import java.util.ArrayList;
import java.util.List;

public class GaussianMF implements MembershipFunction {
    List<Double> params;

    GaussianMF(double param1, double param2){
        params = new ArrayList<>();
        params.add(param1);
        params.add(param2);
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
