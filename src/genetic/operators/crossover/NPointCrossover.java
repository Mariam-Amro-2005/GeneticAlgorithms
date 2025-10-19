package genetic.operators.crossover;

import genetic.core.*;
import java.util.*;

/**
 * Generic N-Point crossover operator for binary, integer, or floating-point chromosomes.
 * Works by selecting N crossover points and alternately swapping gene segments
 * between two parents.
 */
public class NPointCrossover implements CrossoverStrategy {
    private final int numPoints;       // e.g., 1 for single-point, 2 for two-point
    private final double crossoverRate;

    public NPointCrossover(int numPoints, double crossoverRate) {
        this.numPoints = numPoints;
        this.crossoverRate = crossoverRate;
    }

    @Override
    public Chromosome[] crossover(Chromosome parent1, Chromosome parent2) {
        Random random = new Random();
        int length = parent1.length();

        // If crossover does not occur, return copies
        if (random.nextDouble() > crossoverRate)
            return new Chromosome[]{parent1.copy(), parent2.copy()};

        Chromosome child1 = parent1.copy();
        Chromosome child2 = parent2.copy();

        // Generate unique sorted crossover points
        TreeSet<Integer> points = new TreeSet<>();
        while (points.size() < numPoints)
            points.add(random.nextInt(length - 1) + 1);  // between 1 and length-1

        List<Integer> crossoverPoints = new ArrayList<>(points);
        crossoverPoints.add(length); // ensures we process until end

        boolean swap = false;
        int prev = 0;

        for (int point : crossoverPoints) {
            if (swap) {
                for (int i = prev; i < point; i++) {
                    Gene<?> g1 = child1.getGenes().get(i).copy();
                    child1.getGenes().set(i, child2.getGenes().get(i).copy());
                    child2.getGenes().set(i, g1);
                }
            }
            swap = !swap;
            prev = point;
        }

        return new Chromosome[]{child1, child2};
    }
}
