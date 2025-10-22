package genetic.case_studies.cpu;

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
}
