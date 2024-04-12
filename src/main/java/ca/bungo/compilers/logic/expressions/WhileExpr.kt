package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.Main
import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.BooleanData
import ca.bungo.compilers.logic.data.BreakData
import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.None

class WhileExpr(var condition: Expression, var body: BlockExpr) : Expression() {
    override fun evaluate(runtime: Runtime): Data {
        var conditionResult = condition.evaluate(runtime) as BooleanData
        var lastEval: Data = None
        while (conditionResult.value) {
            val scope = runtime.subscope(emptyMap())
            scope.parent = runtime
            lastEval = body.evaluate(scope)
            if(lastEval is BreakData) {
                lastEval = None
                break
            }
            conditionResult = condition.evaluate(runtime) as BooleanData
        }
        return lastEval
    }
}