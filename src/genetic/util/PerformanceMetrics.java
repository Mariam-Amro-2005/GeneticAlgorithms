package genetic.util;

public class PerformanceMetrics {
    private long startTime;
    private long endTime;
    private int generationsRun;
    private double finalFitness;

    public void start() {
        this.startTime = System.nanoTime();
    }

    public void stop() {
        this.endTime = System.nanoTime();
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

    public void printReport(String caseName) {
        System.out.println("\n=== PERFORMANCE REPORT: " + caseName + " ===");
        System.out.printf("🕒 Total Runtime: %.2f seconds%n", getElapsedMillis() / 1000.0);
        System.out.printf("📈 Generations Executed: %d%n", generationsRun);
        System.out.printf("🎯 Final Fitness: %.6f%n", finalFitness);
        System.out.println("========================================\n");
    }
}
