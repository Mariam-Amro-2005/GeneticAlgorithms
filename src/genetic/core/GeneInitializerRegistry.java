package genetic.core;

import genetic.case_studies.cpu.JobFactory;
import genetic.case_studies.cpu.JobGene;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Central registry for gene initialization strategies.
 */
public final class GeneInitializerRegistry {
    private static final Map<RepresentationType, GeneInitializer> REGISTRY = new EnumMap<>(RepresentationType.class);

    static {
        REGISTRY.put(RepresentationType.BINARY,
                (length, random) -> {
                    List<Gene<?>> genes = new ArrayList<>();
                    for (int i = 0; i < length; i++) genes.add(new BinaryGene(random));
                    return genes;
                });

        REGISTRY.put(RepresentationType.INTEGER,
                (length, random) -> {
                    List<Gene<?>> genes = new ArrayList<>();
                    for (int i = 0; i < length; i++) genes.add(new IntegerGene(random));
                    return genes;
                });

        REGISTRY.put(RepresentationType.FLOATING_POINT,
                (length, random) -> {
                    List<Gene<?>> genes = new ArrayList<>();
                    for (int i = 0; i < length; i++) genes.add(new FloatingPointGene(random));
                    return genes;
                });

        REGISTRY.put(RepresentationType.JOB,
                (length, random) -> {
                    // Generate a random job set and map them to genes
                    var jobs = JobFactory.randomJobs(length, 20, 10, random);
                    return jobs.stream()
                            .map(JobGene::new)
                            .collect(Collectors.toList());
                });
    }

    private GeneInitializerRegistry() {}

    /** Returns the registered initializer for a representation type. */
    public static GeneInitializer get(RepresentationType type) {
        return Optional.ofNullable(REGISTRY.get(type))
                .orElseThrow(() -> new IllegalStateException("No initializer registered for type: " + type));
    }

    /** Allows client code to register custom initializers dynamically. */
    public static void register(RepresentationType type, GeneInitializer initializer) {
        REGISTRY.put(type, initializer);
    }
}
