package genetic.client;

import genetic.case_studies.cpu.CPUJobScheduling;
import genetic.case_studies.cpu.Job;
import genetic.case_studies.cpu.JobGene;
import genetic.core.Chromosome;
import genetic.core.Gene;
import genetic.engine.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== GA Library Demo: CPU Scheduling ===");

        List<Job> jobs = List.of(
                new Job("P1", 0, 5),
                new Job("P2", 2, 3),
                new Job("P3", 4, 1),
                new Job("P4", 6, 7)
        );

        final long seed = 42L; // reproducible

        // create initial population of job permutations using the same seed
        Random rng = new Random(seed);
        List<Chromosome> initialPopulation = createJobInitialPopulation(jobs, 30, rng);

        // Step 1: Configure parameters (chromosome length must equal number of jobs)
        GAParameters params = new GAParameters.Builder()
                .setPopulationSize(30)
                .setGenerations(200)
                .setChromosomeLength(jobs.size())    // <- important
                .setCrossoverRate(0.7)
                .setMutationRate(0.1)
                .setRepresentationType("JOB")        // must match RepresentationType enum
                .setRandomSeed(seed)                 // same seed used to create initial pop
                .setInitialPopulation(initialPopulation)
                .build();

        // Step 2: Define fitness function (case study) -- pass jobs if your implementation requires it
        // If your CPUJobScheduling constructor expects the job list, pass 'jobs'; otherwise adjust.
        FitnessFunction fitnessFunction = new CPUJobScheduling();

        // Build and run GA with defaults
        GeneticAlgorithmEngine ga = new GeneticAlgorithmEngine.Builder(params, fitnessFunction).build();
        ga.run();
    }

    private static List<Chromosome> createJobInitialPopulation(List<Job> jobs, int populationSize, Random rng) {
        List<Chromosome> pop = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            List<Job> shuffled = new ArrayList<>(jobs);
            Collections.shuffle(shuffled, rng);
            List<Gene<?>> genes = shuffled.stream()
                    .map(JobGene::new)
                    .collect(Collectors.toList());
            Chromosome chrom = new Chromosome(genetic.core.RepresentationType.JOB, genes);
            pop.add(chrom);
        }
        return pop;
    }
}


// --- Option B: Advanced setup (custom operators) ---
        /*
        SelectionStrategy selection = new TournamentSelection();
        CrossoverStrategy crossover = new NPointCrossover(2, params.getCrossoverRate());
        MutationStrategy mutation = new SwapMutation(params.getMutationRate());
        ReplacementStrategy replacement = new SteadyStateReplacement();

        GeneticAlgorithmEngine gaCustom = new GeneticAlgorithmEngine.Builder(params, fitnessFunction)
                .withSelection(selection)
                .withCrossover(crossover)
                .withMutation(mutation)
                .withReplacement(replacement)
                .build();

        gaCustom.run();
        */