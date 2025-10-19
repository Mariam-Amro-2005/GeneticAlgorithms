package genetic.engine;

import genetic.core.*;
import genetic.operators.selection.*;
import genetic.operators.crossover.*;
import genetic.operators.mutation.*;
import genetic.replacement.*;

import java.util.*;

/**
 * Reusable Genetic Algorithm Engine.
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

    public GeneticAlgorithmEngine(GAParameters params,
                                  FitnessFunction fitnessFunction,
                                  SelectionStrategy selection,
                                  CrossoverStrategy crossover,
                                  MutationStrategy mutation,
                                  ReplacementStrategy replacement) {
        this.params = params;
        this.fitnessFunction = fitnessFunction;
        this.selection = selection;
        this.crossover = crossover;
        this.mutation = mutation;
        this.replacement = replacement;
    }

    /** Initializes the population and evaluates initial fitness. */
    private void initializePopulation() {
        population = new Population();
        for (int i = 0; i < params.getPopulationSize(); i++) {
            Chromosome c = new Chromosome(params.getRepresentationType(), params.getChromosomeLength());
            double fitness = fitnessFunction.evaluate(c);
            c.setFitness(fitness);
            population.addChromosome(c);
        }
    }

    /** Evaluates fitness for all chromosomes in the population. */
    private void evaluatePopulation(Population pop) {
        for (Chromosome c : pop.getIndividuals()) {
            double fitness = fitnessFunction.evaluate(c);
            c.setFitness(fitness);
        }
    }

    /** Runs the GA evolution loop. */
    public void run() {
        initializePopulation();

        for (int generation = 1; generation <= params.getGenerations(); generation++) {
            List<Chromosome> offspringList = new ArrayList<>();

            // Generate offspring via selection, crossover, and mutation
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

            // Evaluate fitness of offspring
            Population offspringPop = new Population();
            for (Chromosome c : offspringList) {
                double fitness = fitnessFunction.evaluate(c);
                c.setFitness(fitness);
                offspringPop.addChromosome(c);
            }

            // Apply replacement strategy
            population = replacement.replace(population, offspringPop);

            // Track and print best individual
            Chromosome best = population.getBest();
            System.out.printf("Generation %d | Best Fitness: %.4f%n", generation, best.getFitness());
        }

        Chromosome finalBest = population.getBest();
        System.out.println("\n=== Evolution Complete ===");
        System.out.println("Best Solution: " + finalBest);
    }

    public Chromosome getBestSolution() {
        return population.getBest();
    }
}
