package genetic.case_studies.optimization;

import genetic.core.Chromosome;
import genetic.core.Gene;
import genetic.engine.FitnessFunction;
import java.util.List;

public class SphereFunctionFitness implements FitnessFunction {

    @Override
    public Double evaluate(Chromosome chromosome) {
        List<Gene<?>> genes = chromosome.getGenes();
        double sumSquares = 0.0;

        for (Gene<?> gene : genes) {
            double x = ((Number) gene.getValue()).doubleValue();
            sumSquares += x * x;
        }

        // Fitness = 1 / (1 + f(x)) since we want to minimize f(x)
        return 1.0 / (1.0 + sumSquares);
    }

    @Override
    public boolean isValid(Chromosome chromosome) {
        // Always valid since all doubles are allowed
        return true;
    }
}
