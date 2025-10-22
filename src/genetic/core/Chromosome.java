package genetic.core;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a chromosome consisting of a list of genes.
 * Initialization and representation logic are now fully decoupled.
 */
public class Chromosome implements Comparable<Chromosome> {
    private List<Gene<?>> genes;
    private double fitness;
    private final RepresentationType type;

    // --- Constructors ---
    public Chromosome(RepresentationType type, int length, Random random) {
        this.type = Objects.requireNonNull(type);
        this.genes = GeneInitializerRegistry.get(type).initialize(length, random);
    }

    public Chromosome(RepresentationType type, List<Gene<?>> genes) {
        this.type = Objects.requireNonNull(type);
        this.genes = new ArrayList<>(genes);
    }

    // --- Accessors ---
    public List<Gene<?>> getGenes() {
        return genes;
    }

    public void setGenes(List<Gene<?>> genes) {
        this.genes = new ArrayList<>(genes);
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public RepresentationType getType() {
        return type;
    }

    public int length() {
        return genes.size();
    }

    // --- Copying ---
    public Chromosome copy() {
        List<Gene<?>> copiedGenes = genes.stream()
                .map(Gene::copy)
                .collect(Collectors.toList());

        Chromosome clone = new Chromosome(type, copiedGenes);
        clone.setFitness(fitness);
        return clone;
    }

    // --- Comparison ---
    @Override
    public int compareTo(Chromosome other) {
        return Double.compare(other.fitness, this.fitness); // Higher is better
    }

    // --- Representation ---
    @Override
    public String toString() {
        return genes.toString() + " | Fitness: " + String.format("%.4f", fitness);
    }
}
