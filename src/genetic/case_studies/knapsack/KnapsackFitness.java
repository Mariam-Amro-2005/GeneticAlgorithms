package genetic.case_studies.knapsack;

import genetic.core.Chromosome;
import genetic.core.Gene;
import genetic.engine.FitnessFunction;
import java.util.List;

public class KnapsackFitness implements FitnessFunction {

    private final int[] weights;
    private final int[] values;
    private final int capacity;

    public KnapsackFitness(int[] weights, int[] values, int capacity) {
        this.weights = weights;
        this.values = values;
        this.capacity = capacity;
    }

    @Override
    public Double evaluate(Chromosome chromosome) {
        List<Gene<?>> genes = chromosome.getGenes();
        int totalWeight = 0;
        int totalValue = 0;

        for (int i = 0; i < genes.size(); i++) {
            int included = ((Number) genes.get(i).getValue()).intValue();
            if (included == 1) {
                totalWeight += weights[i];
                totalValue += values[i];
            }
        }

        // Apply penalty if capacity exceeded
        if (totalWeight > capacity) {
            double penalty = 1.0 / (1.0 + (totalWeight - capacity));
            return totalValue * penalty;
        }

        return (double) totalValue;
    }

    @Override
    public boolean isValid(Chromosome chromosome) {
        int totalWeight = 0;
        for (int i = 0; i < chromosome.length(); i++) {
            int included = ((Number) chromosome.getGenes().get(i).getValue()).intValue();
            if (included == 1) {
                totalWeight += weights[i];
            }
        }
        return totalWeight <= capacity;
    }
}
