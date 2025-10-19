package genetic.operators.selection;

import genetic.core.Population;
import genetic.core.Chromosome;

public interface SelectionStrategy {
    Chromosome selectParent(Population population);
}
