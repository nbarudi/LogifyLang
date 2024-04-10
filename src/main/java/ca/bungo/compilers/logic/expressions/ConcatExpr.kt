package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.StringData

class ConcatExpr(val left: Expression, val right: Expression): Expression() {

    override fun evaluate(runtime: Runtime): Data {
        val leftVal = left.evaluate(runtime)
        val rightVal = right.evaluate(runtime)

        val result = leftVal.toString().substring(1, leftVal.toString().length - 1) + rightVal.toString().substring(1, rightVal.toString().length - 1)
        return StringData(result)
    }

}