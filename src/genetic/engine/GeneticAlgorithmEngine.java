package genetic.engine;

import genetic.core.*;
import genetic.operators.selection.*;
import genetic.operators.crossover.*;
import genetic.operators.mutation.*;
import genetic.replacement.*;

import java.util.*;
import java.util.function.Supplier;

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

    private final Random random;
    private Population population;

    /** Private constructor — only accessible via the Builder */
    private GeneticAlgorithmEngine(Builder builder) {
        this.params = builder.params;
        this.fitnessFunction = builder.fitnessFunction;
        this.selection = builder.selection;
        this.crossover = builder.crossover;
        this.mutation = builder.mutation;
        this.replacement = builder.replacement;
        this.random = Optional.ofNullable(params.getRandom()).orElseGet(Random::new);
    }

    /** Initializes the population and evaluates initial fitness. */
    private void initializePopulation() {
        // 1) If the client provided a ready population, use it (defensive copy)
        List<Chromosome> providedPop = params.getInitialPopulation();
        if (providedPop != null && !providedPop.isEmpty()) {
            population = new Population();
            for (Chromosome c : providedPop) {
                Chromosome copy = c.copy();
                copy.setFitness(fitnessFunction.evaluate(copy));
                population.addChromosome(copy);
            }
            return;
        }

        // 2) If the client provided a population initializer Supplier, use it
        Supplier<List<Chromosome>> initializer = params.getPopulationInitializer();
        if (initializer != null) {
            List<Chromosome> built = initializer.get();
            population = new Population();
            for (Chromosome c : built) {
                Chromosome copy = c.copy();
                copy.setFitness(fitnessFunction.evaluate(copy));
                population.addChromosome(copy);
            }
            return;
        }

        // 3) Default: create random population according to representation
        population = new Population();
        for (int i = 0; i < params.getPopulationSize(); i++) {
            Chromosome c = new Chromosome(params.getRepresentationType(), params.getChromosomeLength(), random);

            // If any gene types support re-initialization via Random, engine doesn't assume method signatures.
            // Genes constructed by their default constructors will already be randomized.
            // Evaluate fitness and add
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
    private void evolveGeneration(int generation) {
        List<Chromosome> offspringList = new ArrayList<>();

        while (offspringList.size() < params.getPopulationSize()) {
            Chromosome parent1 = selection.selectParent(population, random);
            Chromosome parent2 = selection.selectParent(population,  random);

            Chromosome[] children = crossover.crossover(parent1, parent2, random);
            mutation.mutate(children[0], random);
            mutation.mutate(children[1], random);

            offspringList.add(children[0]);
            if (offspringList.size() < params.getPopulationSize())
                offspringList.add(children[1]);
        }

        // create offspring Population
        Population offspringPop = new Population();
        for (Chromosome c : offspringList) {
            offspringPop.addChromosome(c);
        }

        evaluatePopulation(offspringPop);

        population = replacement.replace(population, offspringPop);

        Chromosome best = population.getBest();
        double avg = population.getIndividuals().stream()
                .mapToDouble(Chromosome::getFitness)
                .average()
                .orElse(0.0);

        System.out.printf("Generation %d | Best: %.5f | Avg: %.5f%n", generation, best.getFitness(), avg);
//        System.out.printf("Generation %d | Best Fitness: %.4f%n", generation, best.getFitness());
    }

    /** Runs the GA evolution loop with validation. */
    public Chromosome run() {
        params.validate();
        initializePopulation();

        boolean thresholdReached = false;
        final double EPSILON = 1e-6;

        for (int generation = 1; generation <= params.getGenerations(); generation++) {
            evolveGeneration(generation);

            Chromosome best = getBestSolution();
            if (best != null && best.getFitness() + EPSILON >= params.getFitnessThreshold()) {
                System.out.printf(
                        "🎯 Fitness threshold %.5f reached at generation %d (fitness = %.5f)%n",
                        params.getFitnessThreshold(), generation, best.getFitness()
                );
                thresholdReached = true;
                break;
            }
        }

        Chromosome finalBest = getBestSolution();

        // --- Validation retry loop ---
        int attempts = 0;
        final int maxRetries = params.getMaxRetries();

        while (!fitnessFunction.isValid(finalBest) && attempts < maxRetries) {
            System.out.println("❌ Invalid solution found — restarting evolution...");
            initializePopulation();

            thresholdReached = false;
            for (int generation = 1; generation <= params.getGenerations(); generation++) {
                evolveGeneration(generation);
                Chromosome best = getBestSolution();
                if (best != null && best.getFitness() + EPSILON >= params.getFitnessThreshold()) {
                    System.out.printf(
                            "🎯 Fitness threshold %.5f reached during retry %d at generation %d (fitness = %.5f)%n",
                            params.getFitnessThreshold(), attempts + 1, generation, best.getFitness()
                    );
                    thresholdReached = true;
                    break;
                }
            }

            finalBest = getBestSolution();
            attempts++;
        }

        if (!thresholdReached && params.getFitnessThreshold() != 1.0) {
            System.out.println("⚠️ Fitness threshold not reached within the allotted generations.");
        }

        System.out.printf("Attempts: %d%n", attempts);

        if (fitnessFunction.isValid(finalBest)) {
            System.out.println("✅ Valid solution found!");
        } else {
            System.out.println("⚠️ Could not find a valid solution after retries.");
        }

        System.out.println("\n=== Evolution Complete ===");
        System.out.println("Best Solution: " + finalBest);
        return finalBest;
    }



    /** Returns the best chromosome in the final population. */
    public Chromosome getBestSolution() {
        return (population == null) ? null : population.getBest();
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
