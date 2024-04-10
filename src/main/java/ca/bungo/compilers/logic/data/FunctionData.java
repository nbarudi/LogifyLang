package ca.bungo.compilers.logic.data;

import ca.bungo.compilers.logic.Runtime;
import ca.bungo.compilers.logic.expressions.Expression;

import java.util.List;

public class FunctionData extends Data {

    public List<String> params;
    public Expression body;
    public Runtime environment;

    public FunctionData(List<String> params, Expression body, Runtime environment) {
        this.params = params;
        this.body = body;
        this.environment = environment;
    }

    @Override
    public String toString() {
        return "Function(" + String.join(", ", params) + ")";
    }

}
