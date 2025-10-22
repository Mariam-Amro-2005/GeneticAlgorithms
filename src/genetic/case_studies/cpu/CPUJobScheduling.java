package genetic.case_studies.cpu;

import genetic.core.*;
import genetic.engine.FitnessFunction;

import java.util.*;

public class CPUJobScheduling implements FitnessFunction {

    private final double weight1 = 0.5; // weight for waiting time
    private final double weight2 = 0.5; // weight for turnaround time

    @Override
    public Double evaluate(Chromosome chromosome) {
        List<JobGene> jobGenes = chromosome.getGenes().stream()
                .map(g -> (JobGene) g)
                .toList();

        int currentTime = 0;
        int totalWait = 0;
        int totalTurnaround = 0;

        for (JobGene jobGene : jobGenes) {
            Job job = jobGene.getValue();
            if (currentTime < job.getArrivalTime())
                currentTime = job.getArrivalTime(); // CPU idle until job arrives

            int waitTime = currentTime - job.getArrivalTime();
            int turnaround = waitTime + job.getBurstTime();

            totalWait += waitTime;
            totalTurnaround += turnaround;
            currentTime += job.getBurstTime();
        }

        double avgWait = (double) totalWait / jobGenes.size();
        double avgTurnaround = (double) totalTurnaround / jobGenes.size();

        // Invert because we want to minimize time but maximize fitness
        return weight1 * (1.0 / avgWait) + weight2 * (1.0 / avgTurnaround);
    }
}
