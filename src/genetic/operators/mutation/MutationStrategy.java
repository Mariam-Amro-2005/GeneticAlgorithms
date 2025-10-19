package genetic.operators.mutation;

import genetic.core.Chromosome;

public interface MutationStrategy {
    void mutate(Chromosome chromosome);
}
