package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.*

class ForLoopExpr(val assignment: Expression, val conditional: Expression, val body: Expression) : Expression() {

    /*
    override fun evaluate(runtime: Runtime) : Data {
        val range = conditional.evaluate(runtime) as? RangeData ?: throw RuntimeException("For Loop Range is not of type RangeData")

        var lastEval: Data = None
        for(i in range.start..range.end) {
            val scope = runtime.subscope(mapOf(index to IntData(i)))
            scope.parent = runtime
            for(stmt in body){
                lastEval = stmt.evaluate(scope)
                if(lastEval is BreakData)
                    break
            }
            if(lastEval is BreakData){
                lastEval = None
                break
            }

        }
        return lastEval
    }*/

    override fun evaluate(runtime: Runtime): Data {
        val loopScope = runtime.subscope(emptyMap())
        loopScope.parent = runtime
        assignment.evaluate(loopScope)

        val lastEval = WhileExpr(conditional, body as BlockExpr)
        return lastEval.evaluate(loopScope)
    }

}