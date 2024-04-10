package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.None

class NoneExpr(): Expression() {
    override fun evaluate(runtime: ca.bungo.compilers.logic.Runtime?): Data = None
}