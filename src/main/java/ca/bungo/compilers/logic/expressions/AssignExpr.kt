package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.Data

class AssignExpr(val name: String, val expr: Expression): Expression() {
    override fun evaluate(runtime: Runtime): Data {
        val value = expr.evaluate(runtime)
        runtime.setVar(name, value)
        return value
    }
}