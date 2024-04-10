package ca.bungo.compilers.logic.data;

public class BooleanData extends Data {
    public final boolean value;

    public BooleanData(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}
