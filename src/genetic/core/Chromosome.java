package genetic.core;

import java.util.*;

public class Chromosome implements Comparable<Chromosome> {
    private List<Gene<?>> genes;
    private double fitness;
    private final RepresentationType type;

    public Chromosome(RepresentationType type, int length) {
        this.type = type;
        this.genes = new ArrayList<>();
        initializeGenes(length);
    }

    private void initializeGenes(int length) {
        for (int i = 0; i < length; i++) {
            genes.add(switch (type) {
                case BINARY -> new BinaryGene();
                case INTEGER -> new IntegerGene();
                case FLOATING_POINT -> new FloatingPointGene();
            });
        }
    }

    public List<Gene<?>> getGenes() {
        return genes;
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

    public Chromosome copy() {
        Chromosome clone = new Chromosome(type, genes.size());
        List<Gene<?>> clonedGenes = new ArrayList<>();
        for (Gene<?> g : this.genes) clonedGenes.add(g.copy());
        clone.genes = clonedGenes;
        clone.fitness = fitness;
        return clone;
    }

    @Override
    public int compareTo(Chromosome o) {
        return Double.compare(o.fitness, this.fitness);
    }

    @Override
    public String toString() {
        return genes.toString() + " | Fitness: " + fitness;
    }
}
