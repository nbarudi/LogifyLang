package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.Main
import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.BooleanData
import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.None

class IfExpr(val condition: Expression, val thenExpr: Expression, val elseIfExprs: List<Expression>, val elseExpr: Expression?) : Expression() {
    override fun evaluate(runtime: Runtime): Data {
        val condRes = condition.evaluate(runtime)
        if (condRes is BooleanData && condRes.value) {
            return thenExpr.evaluate(runtime)
        } else {
            for (elseIfExpr in elseIfExprs) {
                val elseIfCondRes = elseIfExpr.evaluate(runtime)
                if (elseIfCondRes !is None) {
                    return elseIfExpr.evaluate(runtime)
                }
            }
            return elseExpr?.evaluate(runtime) ?: None
        }
    }
}



