package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.ArrayData
import ca.bungo.compilers.logic.data.Data

class ReverseExpr(val expression: Expression) : Expression() {

    override fun evaluate(runtime: Runtime): Data {
        val result = expression.evaluate(runtime) as ArrayData
        return ArrayData(result.data.reversed().toMutableList())
    }

}