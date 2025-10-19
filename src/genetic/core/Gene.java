package genetic.core;

public interface Gene<T> {
    T getValue();
    void setValue(T value);
    Gene<T> copy();
}
