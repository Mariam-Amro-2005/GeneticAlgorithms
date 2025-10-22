package genetic.operators.crossover;

import genetic.core.Chromosome;

import java.util.Random;

public interface CrossoverStrategy {
    Chromosome[] crossover(Chromosome parent1, Chromosome parent2, Random random);
}
