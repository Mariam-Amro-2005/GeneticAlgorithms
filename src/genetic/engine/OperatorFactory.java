package genetic.engine;

import genetic.operators.selection.*;
import genetic.operators.crossover.*;
import genetic.operators.mutation.*;
import genetic.replacement.*;

public class OperatorFactory {

    public static SelectionStrategy createSelection(String name) {
        return switch (name.toLowerCase()) {
            case "tournament" -> new TournamentSelection();
            case "roulette" -> new RouletteWheelSelection();
            default -> throw new IllegalArgumentException("Unknown selection method: " + name);
        };
    }

    public static CrossoverStrategy createCrossover(String name, GAParameters params) {
        double rate = params.getCrossoverRate();
        int numPoints = params.getCrossoverPoints();

        return switch (name.toLowerCase()) {
            case "order" -> new OrderCrossover();
            case "npoint" -> new NPointCrossover(numPoints, rate); // 2-point crossover example
            case "uniform" -> new UniformCrossover(rate);
            default -> throw new IllegalArgumentException("Unknown crossover method: " + name);
        };
    }

    public static MutationStrategy createMutation(String name, GAParameters params) {
        double rate = params.getMutationRate();
        double range = params.getMutationRange();

        return switch (name.toLowerCase()) {
            case "swap" -> new SwapMutation(rate);
            case "bitflip" -> new BitFlipMutation(rate);
            case "floating" -> new FloatingPointMutation(rate, range);
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
