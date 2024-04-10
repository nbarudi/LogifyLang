package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.None

class BlockExpr(val statements: List<Expression>): Expression() {

    override fun evaluate(runtime: Runtime): Data {
        var lastEval: Data = None
        for(stmt in statements) {
            lastEval = stmt.evaluate(runtime)
        }
        return lastEval
    }

}