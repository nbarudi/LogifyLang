package ca.bungo.compilers.logic.expressions;

import ca.bungo.compilers.logic.Runtime;
import ca.bungo.compilers.logic.data.Data;

public abstract class Expression {

    public abstract Data evaluate(Runtime runtime);

}
