package genetic.client.other;

import genetic.case_studies.optimization.SphereFunctionFitness;
import genetic.engine.*;
import genetic.core.*;
import genetic.util.PerformanceMetrics;
import genetic.operators.selection.*;
import genetic.operators.crossover.*;
import genetic.operators.mutation.*;
import genetic.replacement.*;

public class SphereOptimizationMain {
    public static void main(String[] args) {
        System.out.println("=== GA Library Demo: Sphere Function Optimization ===");

        FitnessFunction fitness = new SphereFunctionFitness();

        GAParameters params = new GAParameters.Builder()
                .setPopulationSize(50)
                .setGenerations(100)
                .setChromosomeLength(5)
                .setCrossoverRate(0.8)
                .setMutationRate(0.1)
                .setMutationRange(0.5)
                .setRepresentationType("FLOATING_POINT")
                .build();

        GeneticAlgorithmEngine ga = new GeneticAlgorithmEngine.Builder(params, fitness)
                .withSelection(OperatorFactory.createSelection("tournament"))
                .withCrossover(OperatorFactory.createCrossover("uniform", params))
                .withMutation(OperatorFactory.createMutation("floating", params))
                .withReplacement(OperatorFactory.createReplacement("steady"))
                .build();

        PerformanceMetrics metrics = new PerformanceMetrics();
        metrics.start();

        Chromosome best = ga.run(metrics);

        metrics.stop();
        metrics.setFinalFitness(best.getFitness());
        metrics.setGenerationsRun(ga.getLastGeneration());
        metrics.exportToCSV("fitness_history.csv");

        metrics.printReport("Sphere Function Optimization Problem");
        metrics.visualize();
    }
}
