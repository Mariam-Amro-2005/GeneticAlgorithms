package genetic.case_studies.binary;

import genetic.core.Chromosome;
import genetic.core.Gene;
import genetic.engine.FitnessFunction;

import java.util.List;

/**
 * OneMax problem: maximize the number of 1 bits in a binary chromosome.
 */
public class OneMaxFitness implements FitnessFunction {

    @Override
    public Double evaluate(Chromosome chromosome) {
        List<Gene<?>> genes = chromosome.getGenes();

        int ones = 0;
        for (Gene<?> gene : genes) {
            Object value = gene.getValue();
            int bit = (value instanceof Boolean)
                    ? ((Boolean) value ? 1 : 0)
                    : ((Number) value).intValue(); // Fallback for integer-based binary
            ones += bit;
        }

        return (double) ones / genes.size();
    }


    @Override
    public boolean isValid(Chromosome chromosome) {
        // Always valid for binary strings
        return true;
    }
}
