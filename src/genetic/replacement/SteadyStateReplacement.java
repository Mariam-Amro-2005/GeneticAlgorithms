package genetic.replacement;

import genetic.core.Chromosome;
import genetic.core.Population;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SteadyStateReplacement implements ReplacementStrategy {
    private final int replaceCount;

    public SteadyStateReplacement() {
        this(2); // default: replace 2 weakest
    }

    public SteadyStateReplacement(int replaceCount) {
        this.replaceCount = replaceCount;
    }

    @Override
    public Population replace(Population currentPopulation, Population offspringPopulation) {
        List<Chromosome> survivors = new ArrayList<>(currentPopulation.getIndividuals());
        survivors.sort(Comparator.comparingDouble(Chromosome::getFitness)); // ascending (worst first)

        // Remove the weakest replaceCount individuals
        for (int i = 0; i < Math.min(replaceCount, survivors.size()); i++) {
            survivors.remove(0);
        }

        // Add new offspring (best first)
        List<Chromosome> newOffspring = new ArrayList<>(offspringPopulation.getIndividuals());
        newOffspring.sort(Comparator.comparingDouble(Chromosome::getFitness).reversed());

        for (int i = 0; i < Math.min(replaceCount, newOffspring.size()); i++) {
            survivors.add(newOffspring.get(i).copy());
        }

        // Return new population
        Population newPop = new Population();
        survivors.forEach(c -> newPop.addChromosome(c.copy()));
        return newPop;
    }
}

