package genetic.operators.crossover;

import genetic.core.*;
import java.util.*;

public class UniformCrossover implements CrossoverStrategy {
    private final double crossoverRate;

    public UniformCrossover(double crossoverRate) {
        this.crossoverRate = crossoverRate;
    }

    @Override
    public Chromosome[] crossover(Chromosome parent1, Chromosome parent2, Random random) {
        Chromosome child1 = parent1.copy();
        Chromosome child2 = parent2.copy();

        for (int i = 0; i < parent1.length(); i++) {
            if (random.nextDouble() < crossoverRate) {
                Gene<?> temp = child1.getGenes().get(i).copy();
                child1.getGenes().set(i, child2.getGenes().get(i).copy());
                child2.getGenes().set(i, temp);
            }
        }

        return new Chromosome[]{child1, child2};
    }
}
