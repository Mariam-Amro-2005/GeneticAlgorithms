package genetic.operators.mutation;

import genetic.core.*;
import java.util.*;

/**
 * Floating Point Mutation:
 * Randomly perturbs floating-point genes within a configurable mutation range.
 * The mutation can be absolute or relative to the gene’s current value.
 */
public class FloatingPointMutation implements MutationStrategy {
    private final double mutationRate;
    private final double mutationRange; // e.g., 0.1 = ±10% of current value if relative

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

                // Apply relative perturbation
                double delta = (random.nextDouble() * 2 - 1) * mutationRange * Math.abs(current);
                double mutated = current + delta;

                // No artificial clamping — let user-defined fitness function handle constraints
                g.setValue(mutated);
            }
        }
    }
}
