package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.ArrayData
import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.IntData

class LengthExpr(val expression: Expression) : Expression() {

    override fun evaluate(runtime: Runtime): Data {
        val result = expression.evaluate(runtime) as ArrayData
        return IntData(result.data.size)
    }

}