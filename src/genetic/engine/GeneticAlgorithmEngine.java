package genetic.engine;

import genetic.core.*;
import genetic.operators.selection.*;
import genetic.operators.crossover.*;
import genetic.operators.mutation.*;
import genetic.replacement.*;

import java.util.*;

/**
 * Reusable Genetic Algorithm Engine with a Builder pattern.
 * Handles the evolution loop using pluggable strategies and representations.
 */
public class GeneticAlgorithmEngine {
    private final GAParameters params;
    private final FitnessFunction fitnessFunction;
    private final SelectionStrategy selection;
    private final CrossoverStrategy crossover;
    private final MutationStrategy mutation;
    private final ReplacementStrategy replacement;

    private Population population;

    /** Private constructor — only accessible via the Builder */
    private GeneticAlgorithmEngine(Builder builder) {
        this.params = builder.params;
        this.fitnessFunction = builder.fitnessFunction;
        this.selection = builder.selection;
        this.crossover = builder.crossover;
        this.mutation = builder.mutation;
        this.replacement = builder.replacement;
    }

    /** Initializes the population and evaluates initial fitness. */
    private void initializePopulation() {
        population = new Population();
        for (int i = 0; i < params.getPopulationSize(); i++) {
            Chromosome c = new Chromosome(params.getRepresentationType(), params.getChromosomeLength());
            c.setFitness(fitnessFunction.evaluate(c));
            population.addChromosome(c);
        }
    }

    /** Evaluates fitness for all chromosomes in the given population. */
    private void evaluatePopulation(Population pop) {
        for (Chromosome c : pop.getIndividuals()) {
            double fitness = fitnessFunction.evaluate(c);
            c.setFitness(fitness);
        }
    }

    /** Executes a single generation of evolution. */
    private void evolveOneGeneration(int generation) {
        List<Chromosome> offspringList = new ArrayList<>();

        while (offspringList.size() < params.getPopulationSize()) {
            Chromosome parent1 = selection.selectParent(population);
            Chromosome parent2 = selection.selectParent(population);

            Chromosome[] children = crossover.crossover(parent1, parent2);
            mutation.mutate(children[0]);
            mutation.mutate(children[1]);

            offspringList.add(children[0]);
            if (offspringList.size() < params.getPopulationSize())
                offspringList.add(children[1]);
        }

        Population offspringPop = new Population(offspringList);
        evaluatePopulation(offspringPop);

        population = replacement.replace(population, offspringPop);

        Chromosome best = population.getBest();
        System.out.printf("Generation %d | Best Fitness: %.4f%n", generation, best.getFitness());
    }

    /** Runs the GA evolution loop. */
    public void run() {
        initializePopulation();

        for (int generation = 1; generation <= params.getGenerations(); generation++) {
            evolveOneGeneration(generation);
        }

        Chromosome finalBest = population.getBest();
        System.out.println("\n=== Evolution Complete ===");
        System.out.println("Best Solution: " + finalBest);
    }

    /** Returns the best chromosome in the final population. */
    public Chromosome getBestSolution() {
        return population.getBest();
    }

    // ---------------------------------------------------------
    // ✅ BUILDER CLASS
    // ---------------------------------------------------------
    public static class Builder {
        private final GAParameters params;
        private final FitnessFunction fitnessFunction;
        private SelectionStrategy selection;
        private CrossoverStrategy crossover;
        private MutationStrategy mutation;
        private ReplacementStrategy replacement;

        public Builder(GAParameters params, FitnessFunction fitnessFunction) {
            this.params = Objects.requireNonNull(params, "GAParameters cannot be null");
            this.fitnessFunction = Objects.requireNonNull(fitnessFunction, "FitnessFunction cannot be null");
        }

        public Builder withSelection(SelectionStrategy selection) {
            this.selection = selection;
            return this;
        }

        public Builder withCrossover(CrossoverStrategy crossover) {
            this.crossover = crossover;
            return this;
        }

        public Builder withMutation(MutationStrategy mutation) {
            this.mutation = mutation;
            return this;
        }

        public Builder withReplacement(ReplacementStrategy replacement) {
            this.replacement = replacement;
            return this;
        }

        /** Builds the engine, injecting defaults where needed. */
        public GeneticAlgorithmEngine build() {
            if (selection == null)
                selection = OperatorFactory.createSelection("tournament");
            if (crossover == null)
                crossover = OperatorFactory.createCrossover("order", params);
            if (mutation == null)
                mutation = OperatorFactory.createMutation("swap", params);
            if (replacement == null)
                replacement = OperatorFactory.createReplacement("steady");

            return new GeneticAlgorithmEngine(this);
        }
    }
}
