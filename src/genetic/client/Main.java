package genetic.client;

import genetic.engine.*;
import genetic.case_studies.*;
import genetic.operators.selection.*;
import genetic.operators.crossover.*;
import genetic.operators.mutation.*;
import genetic.replacement.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== GA Library Demo: CPU Scheduling ===");

        // Step 1: Configure parameters
        GAParameters params = new GAParameters();
        params.setPopulationSize(20);
        params.setGenerations(100);
        params.setChromosomeLength(5);
        params.setCrossoverRate(0.8);
        params.setMutationRate(0.05);
        params.setRepresentationType("Integer");

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
