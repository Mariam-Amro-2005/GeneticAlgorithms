package genetic.replacement;

import genetic.core.Population;
import genetic.core.Chromosome;

import java.util.*;

public class ElitismReplacement implements ReplacementStrategy {
    private final int eliteCount;

    public ElitismReplacement() {
        this.eliteCount = 2; // default 2 elites
    }

    public ElitismReplacement(int eliteCount) {
        this.eliteCount = eliteCount;
    }

    @Override
    public Population replace(Population currentPopulation, Population offspringPopulation) {
        List<Chromosome> combined = new ArrayList<>();
        combined.addAll(currentPopulation.getIndividuals());
        combined.addAll(offspringPopulation.getIndividuals());

        // Sort descending by fitness
        combined.sort(Comparator.comparingDouble(Chromosome::getFitness).reversed());

        // Keep top population size individuals
        List<Chromosome> nextGen = combined.subList(0, currentPopulation.size());

        Population newPop = new Population();
        for (Chromosome c : nextGen) {
            newPop.addChromosome(c.copy());
        }

        return newPop;
    }
}
