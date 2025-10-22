package genetic.operators.crossover;

import genetic.core.*;
import java.util.*;

public class UniformCrossover implements CrossoverStrategy {
    private final double crossoverRate;

    public UniformCrossover(double crossoverRate) {
        this.crossoverRate = crossoverRate;
    }

    @Override
    public Chromosome[] crossover(Chromosome p1, Chromosome p2, Random random) {
        Chromosome c1 = p1.copy();
        Chromosome c2 = p2.copy();

        for (int i = 0; i < p1.length(); i++) {
            if (random.nextDouble() < crossoverRate) {
                Gene<?> temp = c1.getGenes().get(i).copy();
                c1.getGenes().set(i, c2.getGenes().get(i).copy());
                c2.getGenes().set(i, temp);
            }
        }

        return new Chromosome[]{c1, c2};
    }
}
