package genetic.operators.mutation;

import genetic.core.Chromosome;

import java.util.Random;

public interface MutationStrategy {
    void mutate(Chromosome chromosome, Random random);
}
