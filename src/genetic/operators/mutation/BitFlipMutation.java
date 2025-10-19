package genetic.operators.mutation;

import genetic.core.*;
import java.util.*;

public class BitFlipMutation implements MutationStrategy {
    private final double mutationRate;

    public BitFlipMutation(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    @Override
    public void mutate(Chromosome chromosome) {
        Random random = new Random();

        if (chromosome.getType() != RepresentationType.BINARY)
            return;

        for (Gene<?> gene : chromosome.getGenes()) {
            if (random.nextDouble() < mutationRate) {
                BinaryGene g = (BinaryGene) gene;
                g.setValue(!g.getValue());
            }
        }
    }
}
