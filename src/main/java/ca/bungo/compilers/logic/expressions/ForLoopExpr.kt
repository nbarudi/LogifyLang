package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.*

class ForLoopExpr(val assignment: Expression, val conditional: Expression, val body: Expression) : Expression() {

    override fun evaluate(runtime: Runtime): Data {
        val loopScope = runtime.subscope(emptyMap())
        loopScope.parent = runtime
        assignment.evaluate(loopScope)

        val lastEval = WhileExpr(conditional, body as BlockExpr)
        return lastEval.evaluate(loopScope)
    }

}