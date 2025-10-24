package genetic.client.other;

import genetic.case_studies.binary.OneMaxFitness;
import genetic.core.*;
import genetic.engine.*;
import genetic.util.PerformanceMetrics;
import genetic.operators.selection.*;
import genetic.operators.crossover.*;
import genetic.operators.mutation.*;
import genetic.replacement.*;

public class OneMaxMain {
    public static void main(String[] args) {
        System.out.println("=== GA Library Demo: OneMax (Binary Representation) ===");

        FitnessFunction fitness = new OneMaxFitness();

        GAParameters params = new GAParameters.Builder()
                .setPopulationSize(100)
                .setGenerations(50)
                .setChromosomeLength(32)
                .setCrossoverRate(0.9)
                .setCrossoverPoints(1)
                .setMutationRate(0.05)
                .setRepresentationType("BINARY")
                .build();

        GeneticAlgorithmEngine ga = new GeneticAlgorithmEngine.Builder(params, fitness)
                .withSelection(OperatorFactory.createSelection("roulette"))
                .withCrossover(OperatorFactory.createCrossover("npoint", params))
                .withMutation(OperatorFactory.createMutation("bitflip", params))
                .withReplacement(OperatorFactory.createReplacement("elitism"))
                .build();

        PerformanceMetrics metrics = new PerformanceMetrics();
        metrics.start();

        Chromosome best = ga.run(metrics);

        metrics.stop();
        metrics.setFinalFitness(best.getFitness());
        metrics.setGenerationsRun(ga.getLastGeneration());
        metrics.exportToCSV("fitness_history.csv");

        metrics.printReport("OneMax Problem");
        metrics.visualize();
    }
}
