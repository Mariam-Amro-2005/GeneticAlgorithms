package genetic.operators.selection;

import genetic.core.Population;
import genetic.core.Chromosome;

import java.util.Random;

public interface SelectionStrategy {
    Chromosome selectParent(Population population, Random random);
}
