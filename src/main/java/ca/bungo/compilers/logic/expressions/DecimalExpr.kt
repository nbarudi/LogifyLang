package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.DecimalData

class DecimalExpr(val decimal: Double) : Expression() {
    override fun evaluate(runtime: Runtime?): Data = DecimalData(decimal)
}