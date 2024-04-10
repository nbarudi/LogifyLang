package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime

class VarExpr(val name: String): Expression() {
    override fun evaluate(runtime: Runtime) = runtime.getVar(name)
}