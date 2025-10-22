package genetic.util;

public class PerformanceMetrics {
    private long startTime;
    private long endTime;
    private long startMemory;
    private long endMemory;
    private int generationsRun;
    private double finalFitness;

    public void start() {
        System.gc(); // trigger GC for consistent baseline
        Runtime runtime = Runtime.getRuntime();
        this.startMemory = runtime.totalMemory() - runtime.freeMemory();
        this.startTime = System.nanoTime();
    }

    public void stop() {
        this.endTime = System.nanoTime();
        Runtime runtime = Runtime.getRuntime();
        this.endMemory = runtime.totalMemory() - runtime.freeMemory();
    }

    public void setGenerationsRun(int generationsRun) {
        this.generationsRun = generationsRun;
    }

    public void setFinalFitness(double finalFitness) {
        this.finalFitness = finalFitness;
    }

    public long getElapsedMillis() {
        return (endTime - startTime) / 1_000_000;
    }

    public long getMemoryUsedBytes() {
        return Math.max(0, endMemory - startMemory);
    }

    public void printReport(String caseName) {
        System.out.println("\n=== PERFORMANCE REPORT: " + caseName + " ===");
        System.out.printf("🕒 Runtime: %.3f seconds%n", getElapsedMillis() / 1000.0);
        System.out.printf("📈 Generations Executed: %d%n", generationsRun);
        System.out.printf("🎯 Final Fitness: %.6f%n", finalFitness);
        System.out.printf("💾 Memory Used: %.2f MB%n", getMemoryUsedBytes() / (1024.0 * 1024.0));
        System.out.println("=============================================\n");
    }
}
