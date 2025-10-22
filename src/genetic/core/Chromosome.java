package genetic.core;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a chromosome consisting of a list of genes.
 * Supports multiple representation types (binary, integer, floating point, or custom).
 */
public class Chromosome implements Comparable<Chromosome> {
    private List<Gene<?>> genes;
    private double fitness;
    private final RepresentationType type;

    // --- Constructors ---
    public Chromosome(RepresentationType type, int length, Random random) {
        this.type = type;
        this.genes = new ArrayList<>();
        initializeGenes(length, random);
    }

    public Chromosome(RepresentationType type, List<Gene<?>> genes) {
        this.type = type;
        // Defensive copy to prevent external modification
        this.genes = new ArrayList<>(genes);
    }

    // --- Initialization ---
    private void initializeGenes(int length, Random random) {
        for (int i = 0; i < length; i++) {
            genes.add(switch (type) {
                case BINARY -> new BinaryGene(random);
                case INTEGER -> new IntegerGene(random);
                case FLOATING_POINT -> new FloatingPointGene(random);
            });
        }
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
        // Higher fitness = better
        return Double.compare(other.fitness, this.fitness);
    }

    // --- Representation ---
    @Override
    public String toString() {
        return genes.toString() + " | Fitness: " + String.format("%.4f", fitness);
    }
}
