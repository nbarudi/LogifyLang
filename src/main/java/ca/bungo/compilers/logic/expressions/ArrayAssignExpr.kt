package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.ArrayData
import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.IntData

class ArrayAssignExpr(var id: String, var index: Expression, var expression: Expression) : Expression() {

    override fun evaluate(runtime: Runtime): Data {
        val result = index.evaluate(runtime) as IntData
        val variable = VarExpr(id).evaluate(runtime) as ArrayData
        val value = expression.evaluate(runtime)

        if(variable.data.size <= result.value) throw RuntimeException("Index out of bounds exception for array $variable")
        variable.data[result.value] = value
        return value
    }

}