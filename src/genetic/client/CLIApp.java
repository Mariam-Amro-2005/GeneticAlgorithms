package genetic.client;

import genetic.case_studies.cpu.*;
import genetic.core.Chromosome;
import genetic.engine.*;
import genetic.operators.crossover.CrossoverStrategy;
import genetic.operators.mutation.MutationStrategy;
import genetic.operators.selection.SelectionStrategy;
import genetic.replacement.ReplacementStrategy;
import genetic.util.InputUtils;
import genetic.util.PerformanceMetrics;
import genetic.util.PopulationUtils;

import java.util.List;
import java.util.Random;

public class CLIApp {

    public static void main(String[] args) {
        System.out.println("=== 🧬 Interactive Genetic Algorithm Configurator ===");

        List<Job> jobs = JobFactory.defaultJobs();
        FitnessFunction fitnessFunction = new CPUJobScheduling();

        GeneticAlgorithmEngine engine = configureInteractively(fitnessFunction, jobs);

        System.out.println("\n🚀 Starting evolution...\n");

        PerformanceMetrics metrics = new PerformanceMetrics();
        metrics.start();
        Chromosome best = engine.run();
        metrics.stop();

        metrics.setFinalFitness(best.getFitness());
        metrics.setGenerationsRun(engine.getLastGeneration());

        metrics.printReport("CPU Job Scheduling");
    }

    public static GeneticAlgorithmEngine configureInteractively(FitnessFunction fitnessFunction, List<Job> jobs) {
        final long seed = 42L;
        Random rng = new Random(seed);

        int populationSize = InputUtils.readIntOrDefault("Population size", 50);
        int generations = InputUtils.readIntOrDefault("Number of generations", 200);
        double crossoverRate = InputUtils.readDoubleOrDefault("Crossover rate (0.0–1.0)", 0.9);
        double mutationRate = InputUtils.readDoubleOrDefault("Mutation rate (0.0–1.0)", 0.25);
        double fitnessThreshold = InputUtils.readDoubleOrDefault("Fitness threshold for early stop", 0.02);

        var initialPopulation = PopulationUtils.createJobInitialPopulation(jobs, populationSize, rng);

        GAParameters.Builder paramBuilder = new GAParameters.Builder()
                .setPopulationSize(populationSize)
                .setGenerations(generations)
                .setChromosomeLength(jobs.size())
                .setCrossoverRate(crossoverRate)
                .setMutationRate(mutationRate)
                .setFitnessThreshold(fitnessThreshold)
                .setRepresentationType("JOB")
                .setRandomSeed(seed)
                .setInitialPopulation(initialPopulation);

        // === SELECTION ===
        System.out.println("\nSelect Selection Strategy:");
        System.out.println("1. Tournament (default)");
        System.out.println("2. Roulette");
        int selChoice = InputUtils.readMenuChoice("Choice", 1, 2, 1);

        SelectionStrategy selection = switch (selChoice) {
            case 2 -> OperatorFactory.createSelection("roulette");
            default -> OperatorFactory.createSelection("tournament");
        };

        // === CROSSOVER ===
        System.out.println("\nSelect Crossover Strategy:");
        System.out.println("1. Order (default)");
        System.out.println("2. N-Point");
        System.out.println("3. Uniform");
        int crossChoice = InputUtils.readMenuChoice("Choice", 1, 3, 1);

        if (crossChoice == 2) {
            int nPoints = InputUtils.readIntOrDefault("Number of crossover points", 2);
            paramBuilder.setCrossoverPoints(nPoints);
        }

        CrossoverStrategy crossover = switch (crossChoice) {
            case 2 -> OperatorFactory.createCrossover("npoint", paramBuilder.build());
            case 3 -> OperatorFactory.createCrossover("uniform", paramBuilder.build());
            default -> OperatorFactory.createCrossover("order", paramBuilder.build());
        };

        // === MUTATION ===
        System.out.println("\nSelect Mutation Strategy:");
        System.out.println("1. Swap (default)");
        System.out.println("2. BitFlip");
        System.out.println("3. FloatingPoint");
        int mutChoice = InputUtils.readMenuChoice("Choice", 1, 3, 1);

        if (mutChoice == 3) {
            double mutRange = InputUtils.readDoubleOrDefault("Mutation range (e.g., 0.1)", 0.1);
            paramBuilder.setMutationRange(mutRange);
        }

        MutationStrategy mutation = switch (mutChoice) {
            case 2 -> OperatorFactory.createMutation("bitflip", paramBuilder.build());
            case 3 -> OperatorFactory.createMutation("floating", paramBuilder.build());
            default -> OperatorFactory.createMutation("swap", paramBuilder.build());
        };

        // === REPLACEMENT ===
        System.out.println("\nSelect Replacement Strategy:");
        System.out.println("1. SteadyState (default)");
        System.out.println("2. Elitism");
        System.out.println("3. Generational");
        int repChoice = InputUtils.readMenuChoice("Choice", 1, 3, 1);

        ReplacementStrategy replacement = switch (repChoice) {
            case 2 -> OperatorFactory.createReplacement("elitism");
            case 3 -> OperatorFactory.createReplacement("generational");
            default -> OperatorFactory.createReplacement("steady");
        };

        System.out.println("\n✅ Configuration complete! Building GA...\n");

        return new GeneticAlgorithmEngine.Builder(paramBuilder.build(), fitnessFunction)
                .withSelection(selection)
                .withCrossover(crossover)
                .withMutation(mutation)
                .withReplacement(replacement)
                .build();
    }
}
