package genetic.core;
import java.util.Random;

public class IntegerGene implements Gene<Integer> {
    private Integer value;

    public IntegerGene(Random random) {
        this.value = random.nextInt(100); // Default 0–99
    }

    public IntegerGene(Integer value) {
        this.value = value;
    }

    /** Initialize with range [min, max] */
    public IntegerGene(Integer lowerBound, Integer upperBound, Random random) {
        this.value = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
    }

    @Override
    public Integer getValue() { return value; }

    @Override
    public void setValue(Integer value) { this.value = value; }

    @Override
    public Gene<Integer> copy() {
        return new IntegerGene(this.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
