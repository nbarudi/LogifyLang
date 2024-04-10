package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.FunctionData

class FuncDefExpr(val name: String, val params: List<String>, val body: Expression) : Expression() {

    override fun evaluate(runtime: Runtime): Data {
        val funcData = FunctionData(params, body, runtime)
        runtime.setVar(name, funcData)
        return funcData
    }

}