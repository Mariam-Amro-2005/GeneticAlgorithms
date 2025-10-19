package genetic.operators.crossover;

import genetic.core.Chromosome;

public interface CrossoverStrategy {
    Chromosome[] crossover(Chromosome parent1, Chromosome parent2);
}
