package genetic.operators.mutation;

import genetic.core.*;
import java.util.*;

/**

 Swap Mutation (for Integer Representation)

 Randomly selects two different positions in the chromosome and swaps the genes.

 Works directly with List<Gene<?>> as returned by the Chromosome class.
 */
public class SwapMutation implements MutationStrategy {

    /** Probability of applying mutation to a chromosome */
    private final double mutationRate;

    public SwapMutation(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    @Override
    public void mutate(Chromosome chromosome) {
        Random random = new Random();
        // Apply mutation with probability = mutationRate
        if (random.nextDouble() < mutationRate) {
            // Get the gene list (List<Gene<?>>)
            List<Gene<?>> genes = (List<Gene<?>>) chromosome.getGenes();

            // Defensive check
            if (genes == null || genes.size() < 2) {
                return;
            }

            // Randomly select two different indices
            int i = random.nextInt(genes.size());
            int j = random.nextInt(genes.size());
            while (i == j) {
                j = random.nextInt(genes.size());
            }

            // Swap the two genes
            Gene<?> temp = genes.get(i);
            genes.set(i, genes.get(j));
            genes.set(j, temp);
        }
    }
}
