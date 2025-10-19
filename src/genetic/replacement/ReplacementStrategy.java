package genetic.replacement;

import genetic.core.Population;

public interface ReplacementStrategy {
    Population replace(Population currentPopulation, Population offspringPopulation);
}
