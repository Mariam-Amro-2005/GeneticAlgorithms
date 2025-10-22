package genetic.core;
import java.util.Random;

public class FloatingPointGene implements Gene<Double> {
    private Double value;

    public FloatingPointGene(Random random) {
        this.value = random.nextDouble(); // Default 0.0–1.0
    }

    public FloatingPointGene(Double value) {
        this.value = value;
    }

    /** Initialize with range [min, max] */
    public FloatingPointGene(Double lowerBound, Double upperBound, Random random) {
        this.value = lowerBound + (upperBound - lowerBound) * random.nextDouble();
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public Gene<Double> copy() {
        return new FloatingPointGene(this.value);
    }

    @Override
    public String toString() {
        return String.format("%.3f", value);
    }
}
