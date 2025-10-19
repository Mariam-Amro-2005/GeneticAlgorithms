package genetic.core;

import java.util.Random;

public class FloatingPointGene implements Gene<Double> {
    private Double value;

    public FloatingPointGene() {
        this.value = new Random().nextDouble(); // range 0.0–1.0
    }

    public FloatingPointGene(Double value) {
        this.value = value;
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
        return String.format("%.2f", value);
    }
}
