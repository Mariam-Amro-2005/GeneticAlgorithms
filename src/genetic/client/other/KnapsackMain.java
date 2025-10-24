package genetic.client.other;

import genetic.case_studies.knapsack.KnapsackFitness;
import genetic.core.*;
import genetic.engine.*;
import genetic.operators.crossover.*;
import genetic.operators.mutation.*;
import genetic.operators.selection.*;
import genetic.replacement.*;
import genetic.util.PerformanceMetrics;

public class KnapsackMain {
    public static void main(String[] args) {
        System.out.println("=== GA Library Demo: Knapsack Problem (30 items) ===");

        int[] weights = {
                10, 20, 30, 40, 15, 25, 35, 45, 50, 55,
                60, 65, 70, 75, 80, 85, 90, 95, 5,  12,
                8,  18, 22, 28, 32, 38, 42, 48, 52, 58
        };

        int[] values = {
                60, 100, 120, 200, 90, 75, 160, 300, 250, 400,
                500, 450, 600, 700, 800, 850, 900, 950, 40,  55,
                65, 85, 110, 130, 150, 175, 190, 210, 230, 260
        };

        int capacity = 600; // increased capacity to make it feasible

        FitnessFunction fitness = new KnapsackFitness(weights, values, capacity);

        GAParameters params = new GAParameters.Builder()
                .setPopulationSize(100)
                .setGenerations(200)
                .setChromosomeLength(weights.length)
                .setCrossoverRate(0.8)
                .setMutationRate(0.2)
                .setRepresentationType("INTEGER")
                .build();

        // Use integer-based strategies suitable for knapsack problems
        GeneticAlgorithmEngine ga = new GeneticAlgorithmEngine.Builder(params, fitness)
                .withSelection(OperatorFactory.createSelection("roulette"))
                .withCrossover(OperatorFactory.createCrossover("npoint", params))
                .withMutation(OperatorFactory.createMutation("swap", params)) // assumes you have integer mutation
                .withReplacement(OperatorFactory.createReplacement("elitism"))
                .build();

        PerformanceMetrics metrics = new PerformanceMetrics();
        metrics.start();

//        Chromosome best = ga.run();
        Chromosome best = ga.run(metrics);

        metrics.stop();
        metrics.setFinalFitness(best.getFitness());
        metrics.setGenerationsRun(ga.getLastGeneration());
        metrics.exportToCSV("fitness_history.csv");

        metrics.printReport("Knapsack Problem (30 Items)");
        metrics.visualize();
    }
}
