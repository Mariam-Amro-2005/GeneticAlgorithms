package genetic.core;

import java.util.Random;

public class BinaryGene implements Gene<Boolean> {
    private Boolean value;

    public BinaryGene() {
        this.value = new Random().nextBoolean();
    }

    public BinaryGene(Boolean value) {
        this.value = value;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void setValue(Boolean value) {
        this.value = value;
    }

    @Override
    public Gene<Boolean> copy() {
        return new BinaryGene(this.value);
    }

    @Override
    public String toString() {
        return value ? "1" : "0";
    }
}
