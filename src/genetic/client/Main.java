package genetic.client;

import genetic.case_studies.cpu.CPUJobScheduling;
import genetic.case_studies.cpu.Job;
import genetic.engine.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== GA Library Demo: CPU Scheduling ===");

        List<Job> jobs = List.of(
                new Job("P1", 0, 5),
                new Job("P2", 2, 3),
                new Job("P3", 4, 1),
                new Job("P4", 6, 7)
        );

        // Step 1: Configure parameters
        GAParameters params = new GAParameters.Builder()
                .setPopulationSize(30)
                .setGenerations(200)
                .setChromosomeLength(8)
                .setCrossoverRate(0.7)
                .setMutationRate(0.1)
                .setRepresentationType("Floating_Point")
                .setRandomSeed(42) // Optional reproducibility
                .build();


        // Step 2: Define fitness function (case study)
        FitnessFunction fitnessFunction = new CPUJobScheduling();

        // --- Option A: Minimal setup (defaults) ---
        GeneticAlgorithmEngine gaDefault = new GeneticAlgorithmEngine.Builder(params, fitnessFunction)
                .build();

        gaDefault.run();

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
    }
}
