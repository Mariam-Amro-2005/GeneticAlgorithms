package genetic.core;

import java.util.*;

public class Population {
    private List<Chromosome> individuals = List.of();

    public Population() {
        this.individuals = new ArrayList<>();
    }

    public Population(List<Chromosome> offspringList) {
        for (Chromosome c : offspringList) {
            this.individuals.add(c.copy());
        }
    }

    public void addChromosome(Chromosome chromosome) {
        individuals.add(chromosome);
    }

    public List<Chromosome> getIndividuals() {
        return individuals;
    }

    public int size() {
        return individuals.size();
    }

    public Chromosome getBest() {
        return individuals.stream()
                .max(Comparator.comparingDouble(Chromosome::getFitness))
                .orElse(null);
    }

    public void sortDescending() {
        individuals.sort(Collections.reverseOrder());
    }

    public Population copy() {
        Population newPop = new Population();
        for (Chromosome c : individuals) {
            newPop.addChromosome(c.copy());
        }
        return newPop;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Population:\n");
        for (Chromosome c : individuals) {
            sb.append(c).append("\n");
        }
        return sb.toString();
    }
}
