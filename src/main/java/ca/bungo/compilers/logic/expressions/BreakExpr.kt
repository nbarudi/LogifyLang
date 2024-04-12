package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.BreakData
import ca.bungo.compilers.logic.data.Data

class BreakExpr : Expression() {
    override fun evaluate(runtime: Runtime?): Data = BreakData()
}