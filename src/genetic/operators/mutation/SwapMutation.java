package genetic.operators.mutation;

import genetic.core.*;
import java.util.*;

public class SwapMutation implements MutationStrategy {
    private final double mutationRate;

    public SwapMutation(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    @Override
    public void mutate(Chromosome chromosome) {
        if (chromosome.getType() != RepresentationType.INTEGER)
            return;

        Random random = new Random();
        List<Gene<?>> genes = chromosome.getGenes();

        if (random.nextDouble() < mutationRate) {
            int i = random.nextInt(genes.size());
            int j = random.nextInt(genes.size());
            Collections.swap(genes, i, j);
        }
    }
}
