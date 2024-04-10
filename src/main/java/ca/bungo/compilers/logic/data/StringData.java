package ca.bungo.compilers.logic.data;

public class StringData extends Data {
    public final String value;

    public StringData(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }
}
