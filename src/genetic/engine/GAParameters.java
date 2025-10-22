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
    private final int maxRetries;
    private final double fitnessThreshold;
    private final double crossoverRate;
    private final int crossoverPoints;
    private final double mutationRate;
    private final double mutationRange;
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
        this.maxRetries = builder.maxRetries;
        this.fitnessThreshold = builder.fitnessThreshold;
        this.crossoverRate = builder.crossoverRate;
        this.crossoverPoints = builder.crossoverPoints;
        this.mutationRate = builder.mutationRate;
        this.mutationRange = builder.mutationRange;
        this.representationType = builder.representationType;
        this.random = builder.random;
        this.initialPopulation = builder.initialPopulation;
        this.populationInitializer = builder.populationInitializer;
    }

    // --- Getters ---
    public int getPopulationSize() { return populationSize; }
    public int getGenerations() { return generations; }
    public int getChromosomeLength() { return chromosomeLength; }
    public int getMaxRetries() { return maxRetries; }
    public double getFitnessThreshold() { return fitnessThreshold; }
    public double getCrossoverRate() { return crossoverRate; }
    public int getCrossoverPoints() { return crossoverPoints; }
    public double getMutationRate() { return mutationRate; }
    public double getMutationRange() { return mutationRange; }
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
        private int maxRetries = 5;
        private double fitnessThreshold = 1;
        private double crossoverRate = 0.8;
        private int crossoverPoints = 2;
        private double mutationRate = 0.05;
        private double mutationRange = 0.1;
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

        public Builder setMaxRetries(int maxRetries) {
            this.maxRetries = maxRetries;
            return this;
        }

        public Builder setFitnessThreshold(double fitnessThreshold) {
            this.fitnessThreshold = fitnessThreshold;
            return this;
        }

        public Builder setCrossoverRate(double crossoverRate) {
            this.crossoverRate = crossoverRate;
            return this;
        }

        public Builder setCrossoverPoints(int crossoverPoints) {
            this.crossoverPoints = crossoverPoints;
            return this;
        }

        public Builder setMutationRate(double mutationRate) {
            this.mutationRate = mutationRate;
            return this;
        }

        public Builder setMutationRange(double mutationRange) {
            this.mutationRange = mutationRange;
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
