package genetic.case_studies.cpu;

import java.util.List;
import java.util.Random;

public final class JobFactory {

    private JobFactory() {}

    public static List<Job> defaultJobs() {
        return List.of(
                new Job("P1", 0, 5),
                new Job("P2", 1, 3),
                new Job("P3", 2, 6),
                new Job("P4", 4, 2),
                new Job("P5", 6, 8),
                new Job("P6", 7, 4),
                new Job("P7", 8, 5),
                new Job("P8", 10, 7),
                new Job("P9", 12, 3),
                new Job("P10", 13, 9),
                new Job("P11", 15, 2),
                new Job("P12", 16, 6)
        );
    }


    public static List<Job> randomJobs(int n, int maxArrival, int maxBurst, Random rng) {
        List<Job> jobs = new java.util.ArrayList<>();
        for (int i = 1; i <= n; i++) {
            int arrival = rng.nextInt(maxArrival + 1);
            int burst = 1 + rng.nextInt(maxBurst);
            jobs.add(new Job("P" + i, arrival, burst));
        }
        return jobs;
    }
}
