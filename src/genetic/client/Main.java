package genetic.client;

import genetic.case_studies.cpu.*;
import genetic.core.Chromosome;
import genetic.engine.*;
import genetic.util.PerformanceMetrics;
import genetic.util.PopulationUtils;

import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Genetic Algorithm Library Demo: CPU Scheduling (Default Run) ===\n");

        // --- Load test jobs ---
        List<Job> jobs = JobFactory.defaultJobs();
        FitnessFunction fitnessFunction = new CPUJobScheduling();

        // --- Initialize population ---
        final long seed = 42L;
        Random rng = new Random(seed);
        List<Chromosome> initialPopulation =
                PopulationUtils.createJobInitialPopulation(jobs, 50, rng);

        // --- Define GA parameters ---
        GAParameters params = new GAParameters.Builder()
                .setPopulationSize(50)
                .setGenerations(200)
                .setChromosomeLength(jobs.size())
                .setCrossoverRate(0.9)
                .setMutationRate(0.25)
                .setRepresentationType("JOB")
                .setRandomSeed(seed)
                .setInitialPopulation(initialPopulation)
                .build();

        // --- Build and run engine ---
        GeneticAlgorithmEngine ga = new GeneticAlgorithmEngine.Builder(params, fitnessFunction).build();

        PerformanceMetrics metrics = new PerformanceMetrics();
        metrics.start();
        Chromosome best = ga.run();
        metrics.stop();

        metrics.setFinalFitness(best.getFitness());
        metrics.setGenerationsRun(ga.getLastGeneration());

        metrics.printReport("CPU Job Scheduling");
    }
}
