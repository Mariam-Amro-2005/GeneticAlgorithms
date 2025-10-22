package genetic.operators.selection;

import genetic.core.Chromosome;
import genetic.core.Population;

import java.util.List;
import java.util.Random;

/**
 * Roulette Wheel Selection (Fitness-Proportionate Selection):
 *
 * Each individual’s chance of being selected is proportional to its fitness value.
 * Works best when all fitness values are non-negative and higher = better.
 */
public class RouletteWheelSelection implements SelectionStrategy {

    @Override
    public Chromosome selectParent(Population population, Random random) {
        List<Chromosome> individuals = population.getIndividuals();

        // --- 1. Compute total fitness ---
        double totalFitness = 0.0;
        for (Chromosome c : individuals) {
            totalFitness += Math.max(0.0, c.getFitness()); // defensive: avoid negatives
        }

        // --- 2. Handle degenerate case (all fitness = 0) ---
        if (totalFitness == 0.0) {
            // Return a random chromosome to avoid division by zero
            return individuals.get(random.nextInt(individuals.size()));
        }

        // --- 3. Spin the roulette wheel ---
        double spin = random.nextDouble() * totalFitness;
        double cumulative = 0.0;

        for (Chromosome c : individuals) {
            cumulative += Math.max(0.0, c.getFitness());
            if (cumulative >= spin) {
                return c;
            }
        }

        // --- 4. Fallback (rare rounding case) ---
        return individuals.get(individuals.size() - 1);
    }
}
