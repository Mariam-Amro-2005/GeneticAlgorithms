package genetic.operators.crossover;

import genetic.core.Chromosome;
import genetic.core.Gene;

import java.util.*;

public class OrderCrossover implements CrossoverStrategy {

    @Override
    public Chromosome[] crossover(Chromosome parent1, Chromosome parent2, Random random) {
        int length = parent1.length();
        int point1 = random.nextInt(length);
        int point2 = random.nextInt(length);

        if (point1 > point2) {
            int tmp = point1;
            point1 = point2;
            point2 = tmp;
        }

        // Initialize children with null placeholders
        List<Gene<?>> child1Genes = new ArrayList<>(Collections.nCopies(length, null));
        List<Gene<?>> child2Genes = new ArrayList<>(Collections.nCopies(length, null));

        // Copy the segment from parents
        for (int i = point1; i <= point2; i++) {
            child1Genes.set(i, parent1.getGenes().get(i).copy());
            child2Genes.set(i, parent2.getGenes().get(i).copy());
        }

        // Fill remaining positions from the opposite parent
        fillRemaining(child1Genes, parent2, point2);
        fillRemaining(child2Genes, parent1, point2);

        return new Chromosome[]{
                new Chromosome(parent1.getType(), child1Genes),
                new Chromosome(parent2.getType(), child2Genes)
        };
    }

    private void fillRemaining(List<Gene<?>> childGenes, Chromosome otherParent, int start) {
        int length = otherParent.length();
        int index = (start + 1) % length;

        // Track genes already used in the child (compare by value)
        Set<Object> usedValues = new HashSet<>();
        for (Gene<?> g : childGenes) {
            if (g != null)
                usedValues.add(g.getValue());
        }

        for (Gene<?> g : otherParent.getGenes()) {
            if (!usedValues.contains(g.getValue())) {
                // Find the next empty slot
                while (childGenes.get(index) != null)
                    index = (index + 1) % length;

                childGenes.set(index, g.copy());
                usedValues.add(g.getValue()); // mark as used
                index = (index + 1) % length; // move forward
            }
        }
    }
}
