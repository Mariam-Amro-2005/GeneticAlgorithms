package genetic.engine;

import genetic.core.*;
import java.util.*;
import java.util.function.Supplier;

/**
 * Holds all configuration parameters for a Genetic Algorithm run.
 * Uses the Builder pattern for clean and flexible construction.
 */
public class GAParameters {
    private final int populationSize;
    private final int generations;
    private final int chromosomeLength;
    private final double crossoverRate;
    private final double mutationRate;
    private final RepresentationType representationType;
    private final Random random;

    // Optional fields
    private final List<Chromosome> initialPopulation;
    private final Supplier<List<Chromosome>> populationInitializer;

    // --- Private constructor (built via Builder) ---
    private GAParameters(Builder builder) {
        this.populationSize = builder.populationSize;
        this.generations = builder.generations;
        this.chromosomeLength = builder.chromosomeLength;
        this.crossoverRate = builder.crossoverRate;
        this.mutationRate = builder.mutationRate;
        this.representationType = builder.representationType;
        this.random = builder.random;
        this.initialPopulation = builder.initialPopulation;
        this.populationInitializer = builder.populationInitializer;
    }

    // --- Getters ---
    public int getPopulationSize() { return populationSize; }
    public int getGenerations() { return generations; }
    public int getChromosomeLength() { return chromosomeLength; }
    public double getCrossoverRate() { return crossoverRate; }
    public double getMutationRate() { return mutationRate; }
    public RepresentationType getRepresentationType() { return representationType; }
    public Random getRandom() { return random; }
    public List<Chromosome> getInitialPopulation() { return initialPopulation; }
    public Supplier<List<Chromosome>> getPopulationInitializer() { return populationInitializer; }

    // --- Validation ---
    public void validate() {
        if (populationSize <= 0 || generations <= 0)
            throw new IllegalArgumentException("Population size and generations must be positive.");
        if (crossoverRate < 0 || crossoverRate > 1)
            throw new IllegalArgumentException("Crossover rate must be between 0 and 1.");
        if (mutationRate < 0 || mutationRate > 1)
            throw new IllegalArgumentException("Mutation rate must be between 0 and 1.");
    }

    // --- Builder Pattern ---
    public static class Builder {
        private int populationSize = 20;
        private int generations = 100;
        private int chromosomeLength = 10;
        private double crossoverRate = 0.8;
        private double mutationRate = 0.05;
        private RepresentationType representationType = RepresentationType.INTEGER;
        private Random random = new Random();
        private List<Chromosome> initialPopulation;
        private Supplier<List<Chromosome>> populationInitializer;

        public Builder setPopulationSize(int populationSize) {
            this.populationSize = populationSize;
            return this;
        }

        public Builder setGenerations(int generations) {
            this.generations = generations;
            return this;
        }

        public Builder setChromosomeLength(int chromosomeLength) {
            this.chromosomeLength = chromosomeLength;
            return this;
        }

        public Builder setCrossoverRate(double crossoverRate) {
            this.crossoverRate = crossoverRate;
            return this;
        }

        public Builder setMutationRate(double mutationRate) {
            this.mutationRate = mutationRate;
            return this;
        }

        public Builder setRepresentationType(String representationType) {
            this.representationType = RepresentationType.valueOf(representationType.toUpperCase());
            return this;
        }

        public Builder setRandomSeed(long seed) {
            this.random = new Random(seed);
            return this;
        }

        public Builder setInitialPopulation(List<Chromosome> population) {
            this.initialPopulation = population;
            return this;
        }

        public Builder setPopulationInitializer(Supplier<List<Chromosome>> initializer) {
            this.populationInitializer = initializer;
            return this;
        }

        public GAParameters build() {
            return new GAParameters(this);
        }
    }
}
