package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.StringData

class StringExpr(val value: String): Expression() {
    override fun evaluate(runtime: Runtime) = StringData(value)
}