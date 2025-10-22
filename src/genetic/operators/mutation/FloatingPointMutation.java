package genetic.operators.mutation;

import genetic.core.*;
import java.util.*;

public class FloatingPointMutation implements MutationStrategy {
    private final double mutationRate;
    private final double mutationRange; // e.g., 0.1 = ±10% change

    public FloatingPointMutation(double mutationRate, double mutationRange) {
        this.mutationRate = mutationRate;
        this.mutationRange = mutationRange;
    }

    @Override
    public void mutate(Chromosome chromosome, Random random) {
        if (chromosome.getType() != RepresentationType.FLOATING_POINT)
            return;

        for (Gene<?> gene : chromosome.getGenes()) {
            if (random.nextDouble() < mutationRate) {
                FloatingPointGene g = (FloatingPointGene) gene;
                double current = g.getValue();
                double delta = (random.nextDouble() * 2 - 1) * mutationRange;
                g.setValue(Math.max(0.0, Math.min(1.0, current + delta))); // keep in [0,1]
            }
        }
    }
}
