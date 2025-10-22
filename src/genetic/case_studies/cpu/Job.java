package genetic.case_studies.cpu;

import java.util.Objects;

public class Job {
    private final String name;
    private final int arrivalTime;
    private final int burstTime;

    public Job(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }

    public String getName() { return name; }
    public int getArrivalTime() { return arrivalTime; }
    public int getBurstTime() { return burstTime; }

    @Override
    public String toString() {
        return name + "(A:" + arrivalTime + ",B:" + burstTime + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Job job)) return false;
        return arrivalTime == job.arrivalTime &&
                burstTime == job.burstTime &&
                Objects.equals(name, job.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, arrivalTime, burstTime);
    }
}
