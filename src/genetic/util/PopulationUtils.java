package genetic.util;

import genetic.case_studies.cpu.Job;
import genetic.case_studies.cpu.JobGene;
import genetic.core.Chromosome;
import genetic.core.Gene;
import genetic.core.RepresentationType;

import java.util.*;
import java.util.stream.Collectors;

public final class PopulationUtils {

    private PopulationUtils() {}

    public static List<Chromosome> createJobInitialPopulation(List<Job> jobs, int populationSize, Random rng) {
        List<Chromosome> population = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            List<Job> shuffled = new ArrayList<>(jobs);
            Collections.shuffle(shuffled, rng);
            List<Gene<?>> genes = shuffled.stream()
                    .map(JobGene::new)
                    .collect(Collectors.toList());
            population.add(new Chromosome(RepresentationType.JOB, genes));
        }
        return population;
    }
}
