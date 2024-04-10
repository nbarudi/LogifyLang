package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.BooleanData
import ca.bungo.compilers.logic.data.Data

class BoolExpr(val value: Boolean): Expression() {

    override fun evaluate(runtime: Runtime?): Data = BooleanData(value)

}