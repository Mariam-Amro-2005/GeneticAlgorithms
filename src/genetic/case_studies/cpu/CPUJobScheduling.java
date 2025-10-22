package genetic.case_studies.cpu;

import genetic.core.Chromosome;
import genetic.engine.FitnessFunction;
import genetic.core.Gene;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CPUJobScheduling implements FitnessFunction {

    @Override
    public Double evaluate(Chromosome chromosome) {
        List<Gene<?>> genes = chromosome.getGenes();
        double currentTime = 0.0;
        double totalWaiting = 0.0;
        double totalTurnaround = 0.0;

        for (Gene<?> gene : genes) {
            Job job = ((JobGene) gene).getValue();

            // If CPU is idle until job arrives, advance time
            if (currentTime < job.getArrivalTime()) {
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

        // We want smaller waiting and turnaround times → higher fitness
        // Using inverse to make fitness directly proportional to performance
        double fitness = 1.0 / (1.0 + (1.5 * avgWaiting) + avgTurnaround);

        // Optionally normalize to prevent near-zero values
        return Math.max(0.0001, fitness);
    }
    public boolean isValidSchedule(Chromosome chrom) {
        Set<Job> seen = new HashSet<>();
        double currentTime = 0.0;

        for (Gene<?> gene : chrom.getGenes()) {
            Job job = ((JobGene) gene).getValue();

            // 1️⃣ Check for duplicate jobs
            if (!seen.add(job)) {
                return false; // duplicate
            }

            // 2️⃣ Check if job starts before its arrival time
            if (currentTime < job.getArrivalTime()) {
                return false; // invalid start time
            }

            // advance CPU time
            currentTime += job.getBurstTime();
        }

        return true; // passed all checks
    }

}
