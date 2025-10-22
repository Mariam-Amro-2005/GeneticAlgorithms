package genetic.case_studies.cpu;

import genetic.core.Gene;

import java.util.Objects;

/**
 * Represents a Gene that holds a Job object.
 * Used for permutation-based GA problems such as CPU scheduling.
 */
public class JobGene implements Gene<Job> {
    private Job job;

    public JobGene(Job job) {
        this.job = new Job(job.getName(), job.getArrivalTime(), job.getBurstTime());
    }

    @Override
    public Job getValue() {
        return job;
    }

    @Override
    public void setValue(Job value) {
        this.job = new Job(value.getName(), value.getArrivalTime(), value.getBurstTime());
    }

    @Override
    public Gene<Job> copy() {
        return new JobGene(new Job(job.getName(), job.getArrivalTime(), job.getBurstTime()));
    }

    @Override
    public String toString() {
        return job.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof JobGene other)) return false;
        return Objects.equals(this.job, other.job);
    }

    @Override
    public int hashCode() {
        return Objects.hash(job);
    }

}