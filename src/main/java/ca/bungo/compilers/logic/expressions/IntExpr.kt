package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.IntData

class IntExpr(val value: Int) : Expression() {
    override fun evaluate(runtime: Runtime?): Data = IntData(value)
}