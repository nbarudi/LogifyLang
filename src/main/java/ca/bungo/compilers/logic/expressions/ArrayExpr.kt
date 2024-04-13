package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.ArrayData
import ca.bungo.compilers.logic.data.Data

class ArrayExpr(var expressions: List<Expression>) : Expression() {

    override fun evaluate(runtime: Runtime?): Data {
        val arrayData: List<Data> = expressions.map { expression -> expression.evaluate(runtime) }
        return ArrayData(arrayData)
    }

}