package genetic.core;

import java.util.List;
import java.util.Random;

/**
 * Strategy interface for initializing chromosome genes
 * based on the representation type.
 */
@FunctionalInterface
public interface GeneInitializer {
    List<Gene<?>> initialize(int length, Random random);
}
