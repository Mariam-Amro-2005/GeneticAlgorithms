package genetic.case_studies.cpu;

import genetic.core.Chromosome;
import genetic.engine.FitnessFunction;
import genetic.core.Gene;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CPUJobScheduling implements FitnessFunction {

    private static final double DUPLICATE_PENALTY = 0.5;    // reduce fitness by 50%
    private static final double ARRIVAL_PENALTY = 0.3;      // reduce fitness by 70%
    private static final double INVALID_SCHEDULE_PENALTY = 0.1; // near-zero if both errors occur

    @Override
    public Double evaluate(Chromosome chromosome) {
        List<Gene<?>> genes = chromosome.getGenes();
        double currentTime = 0.0;
        double totalWaiting = 0.0;
        double totalTurnaround = 0.0;

        Set<Job> seen = new HashSet<>();
        boolean hasDuplicate = false;
        boolean hasEarlyStart = false;

        for (Gene<?> gene : genes) {
            Job job = ((JobGene) gene).getValue();

            // Duplicate job check
            if (!seen.add(job)) {
                hasDuplicate = true;
            }

            // CPU idle time handling
            if (currentTime < job.getArrivalTime()) {
                // Instead of jumping, count idle as penalty time
                hasEarlyStart = true;
                currentTime = job.getArrivalTime();
            }

            double waitingTime = currentTime - job.getArrivalTime();
            totalWaiting += waitingTime;

            currentTime += job.getBurstTime();
            double turnaroundTime = currentTime - job.getArrivalTime();
            totalTurnaround += turnaroundTime;
        }

        int n = genes.size();
        double avgWaiting = totalWaiting / n;
        double avgTurnaround = totalTurnaround / n;

        // Base fitness (higher is better)
        double fitness = 1.0 / (1.0 + (1.5 * avgWaiting) + avgTurnaround);

        // Apply penalties
        if (hasDuplicate && hasEarlyStart) {
            fitness *= INVALID_SCHEDULE_PENALTY; // both errors
        } else if (hasDuplicate) {
            fitness *= DUPLICATE_PENALTY;
        } else if (hasEarlyStart) {
            fitness *= ARRIVAL_PENALTY;
        }

        // Prevent total collapse of scale
        return Math.max(0.0001, fitness);
    }

    @Override
    public boolean isValid(Chromosome chromosome) {
        Set<Job> seen = new HashSet<>();
        double currentTime = 0.0;

        for (var gene : chromosome.getGenes()) {
            Job job = ((JobGene) gene).getValue();

            // Duplicate check
            if (!seen.add(job)) {
                return false;
            }

            // Early start check
            if (currentTime < job.getArrivalTime()) {
                return false;
            }

            currentTime += job.getBurstTime();
        }

        return true;
    }
}
