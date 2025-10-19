package genetic.operators.crossover;

import genetic.core.*;
import java.util.*;

public class OrderCrossover implements CrossoverStrategy {

    @Override
    public Chromosome[] crossover(Chromosome parent1, Chromosome parent2) {
        if (parent1.getType() != RepresentationType.INTEGER)
            return new Chromosome[] { parent1.copy(), parent2.copy() };

        int size = parent1.length();
        Random random = new Random();

        int start = random.nextInt(size);
        int end = random.nextInt(size - start) + start;

        Chromosome child1 = parent1.copy();
        Chromosome child2 = parent2.copy();

        List<Gene<?>> genes1 = child1.getGenes();
        List<Gene<?>> genes2 = child2.getGenes();

        // Preserve a segment
        Set<Integer> segment1 = new HashSet<>();
        Set<Integer> segment2 = new HashSet<>();

        for (int i = start; i < end; i++) {
            segment1.add((Integer) ((IntegerGene) genes1.get(i)).getValue());
            segment2.add((Integer) ((IntegerGene) genes2.get(i)).getValue());
        }

        // Fill remaining positions
        int idx1 = end % size, idx2 = end % size;
        for (int i = 0; i < size; i++) {
            int val1 = (Integer) ((IntegerGene) parent2.getGenes().get((end + i) % size)).getValue();
            if (!segment1.contains(val1)) {
                ((IntegerGene) genes1.get(idx1)).setValue(val1);
                idx1 = (idx1 + 1) % size;
            }

            int val2 = (Integer) ((IntegerGene) parent1.getGenes().get((end + i) % size)).getValue();
            if (!segment2.contains(val2)) {
                ((IntegerGene) genes2.get(idx2)).setValue(val2);
                idx2 = (idx2 + 1) % size;
            }
        }

        return new Chromosome[]{child1, child2};
    }
}
