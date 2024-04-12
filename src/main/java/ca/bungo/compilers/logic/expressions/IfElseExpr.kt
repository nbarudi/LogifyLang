package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.Main
import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.BooleanData
import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.None

class IfElseExpr(val condition: Expression, val thenExpr: Expression) : Expression() {
    override fun evaluate(runtime: Runtime): Data {
        val condRes = condition.evaluate(runtime)
        if (condRes is BooleanData && condRes.value) {
            val result = thenExpr.evaluate(runtime)
            if (result !is None) {
                return result
            }
        }
        return None
    }
}


