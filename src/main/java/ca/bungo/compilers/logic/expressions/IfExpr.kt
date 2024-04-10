package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.BooleanData
import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.None

class IfExpr(val condition: Expression, val body: Expression, val otherwise: Expression?) : Expression() {

    override fun evaluate(runtime: Runtime): Data {
        val condRes = condition.evaluate(runtime)
        return if(condRes is BooleanData && condRes.value) {
            body.evaluate(runtime)
        } else {
            otherwise?.evaluate(runtime) ?: None
        }
    }

}