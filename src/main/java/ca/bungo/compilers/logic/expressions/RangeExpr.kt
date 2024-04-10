package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.RangeData

class RangeExpr(val start: Int, val end: Int): Expression() {

    override fun evaluate(runtime: Runtime): Data {
        return RangeData(start, end)
    }

}