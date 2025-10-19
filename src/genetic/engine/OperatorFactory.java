package genetic.engine;

import genetic.operators.selection.*;
import genetic.operators.crossover.*;
import genetic.operators.mutation.*;
import genetic.replacement.*;

import java.util.Random;

public class OperatorFactory {  // TODO: set operator variables instead of random numbers

    public static SelectionStrategy createSelection(String name) {
        return switch (name.toLowerCase()) {
            case "tournament" -> new TournamentSelection();
            case "roulette" -> new RouletteWheelSelection();
            default -> throw new IllegalArgumentException("Unknown selection method: " + name);
        };
    }

    public static CrossoverStrategy createCrossover(String name) {
        return switch (name.toLowerCase()) {
            case "order" -> new OrderCrossover();
            case "npoint" -> new NPointCrossover(new Random().nextInt(), new Random().nextDouble());
            case "uniform" -> new UniformCrossover(new Random().nextDouble());
            default -> throw new IllegalArgumentException("Unknown crossover method: " + name);
        };
    }

    public static MutationStrategy createMutation(String name) {
        return switch (name.toLowerCase()) {
            case "swap" -> new SwapMutation(new Random().nextDouble());
            case "bitflip" -> new BitFlipMutation(new Random().nextDouble());
            case "floating" -> new FloatingPointMutation(new Random().nextDouble(), 10.0);
            default -> throw new IllegalArgumentException("Unknown mutation method: " + name);
        };
    }

    public static ReplacementStrategy createReplacement(String name) {
        return switch (name.toLowerCase()) {
            case "steady" -> new SteadyStateReplacement();
            case "elitism" -> new ElitismReplacement();
            case "generational" -> new GenerationalReplacement();
            default -> throw new IllegalArgumentException("Unknown replacement method: " + name);
        };
    }
}
