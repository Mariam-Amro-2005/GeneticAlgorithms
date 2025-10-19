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

        // Step 1: Create parameters
        GAParameters params = new GAParameters();
        params.setPopulationSize(20);
        params.setGenerations(100);
        params.setChromosomeLength(5);
        params.setCrossoverRate(0.8);
        params.setMutationRate(0.05);
        params.setRepresentationType("Integer");

        // Step 2: Set fitness function (case study)
        FitnessFunction fitnessFunction = new CPUJobScheduling();

        // Step 3: Dynamically create operators using the factory and params
        SelectionStrategy selection = OperatorFactory.createSelection("tournament");
        CrossoverStrategy crossover = OperatorFactory.createCrossover("npoint", params);
        MutationStrategy mutation = OperatorFactory.createMutation("swap", params);
        ReplacementStrategy replacement = OperatorFactory.createReplacement("steady");

        // Step 4: Plug everything into the engine
        GeneticAlgorithmEngine ga = new GeneticAlgorithmEngine(
                params, fitnessFunction, selection, crossover, mutation, replacement
        );

        ga.run();
    }
}
