package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.Main
import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.FunctionData

class FuncCallExpr(val name: String, val args: List<Expression>): Expression() {
    override fun evaluate(runtime: Runtime): Data {
        val func = runtime.getVar(name) as FunctionData
        val argValues = args.map { it.evaluate(runtime) }
        return runtime.callFunction(func, argValues)
    }
}