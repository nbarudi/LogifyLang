package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.ArrayData
import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.IntData

class ArrayVarExpr(var id: String, var idx: Expression) : Expression() {

    override fun evaluate(runtime: Runtime): Data {
        val index = idx.evaluate(runtime) as IntData
        val array = VarExpr(id).evaluate(runtime) as ArrayData
        return array.data[index.value]
    }

}