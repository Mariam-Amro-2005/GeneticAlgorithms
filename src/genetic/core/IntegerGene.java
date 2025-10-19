package genetic.core;

import java.util.Random;

public class IntegerGene implements Gene<Integer> {
    private Integer value;

    public IntegerGene() {
        this.value = new Random().nextInt(10); // default range 0–9
    }

    public IntegerGene(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public Gene<Integer> copy() {
        return new IntegerGene(this.value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
