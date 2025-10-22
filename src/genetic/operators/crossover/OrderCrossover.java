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

        // Initialize child genes as nulls
        List<Gene<?>> child1Genes = new ArrayList<>(Collections.nCopies(length, null));
        List<Gene<?>> child2Genes = new ArrayList<>(Collections.nCopies(length, null));

        // Copy slice from parent1 to child1 and parent2 to child2
        for (int i = point1; i <= point2; i++) {
            child1Genes.set(i, parent1.getGenes().get(i).copy());
            child2Genes.set(i, parent2.getGenes().get(i).copy());
        }

        // Fill remaining positions in order from the other parent
        fillRemaining(child1Genes, parent2, point2);
        fillRemaining(child2Genes, parent1, point2);

        Chromosome child1 = new Chromosome(parent1.getType(), child1Genes);
        Chromosome child2 = new Chromosome(parent2.getType(), child2Genes);
        return new Chromosome[]{child1, child2};
    }

    private void fillRemaining(List<Gene<?>> childGenes, Chromosome otherParent, int start) {
        int length = otherParent.length();
        int index = (start + 1) % length;

        for (Gene<?> g : otherParent.getGenes()) {
            if (!childGenes.contains(g)) {
                while (childGenes.get(index) != null)
                    index = (index + 1) % length;
                childGenes.set(index, g.copy());
            }
        }
    }
}
