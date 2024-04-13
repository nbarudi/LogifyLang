package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.ArrayData
import ca.bungo.compilers.logic.data.BreakData
import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.None

class EachLoopExpr(val assignment: String, val array: Expression, val body: Expression) : Expression() {

    override fun evaluate(runtime: Runtime): Data {
        val loopScope = runtime.subscope(emptyMap())
        loopScope.parent = runtime
        val arrayData = array.evaluate(runtime) as ArrayData

        var lastEval: Data = None
        for(value in arrayData.data){
            var intScope = loopScope.subscope(mapOf(assignment to value))
            intScope.parent = loopScope
            lastEval = body.evaluate(intScope)
            if(lastEval is BreakData)
                break
        }

        return lastEval
    }

}