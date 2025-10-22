package genetic.engine;

import genetic.core.Chromosome;

public interface FitnessFunction {
    Double evaluate(Chromosome chromosome);
    default boolean isValid(Chromosome chromosome) {
        return true;
    }
}
