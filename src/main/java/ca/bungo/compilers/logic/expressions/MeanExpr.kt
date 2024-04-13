package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.ArrayData
import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.DecimalData
import ca.bungo.compilers.logic.data.IntData

class MeanExpr(var expression: Expression) : Expression() {

    override fun evaluate(runtime: Runtime?): Data {
        val result = expression.evaluate(runtime) as ArrayData
        var sum = 0.0
        for(v in result.data) {
            if(v !is IntData && v !is DecimalData) throw RuntimeException("Attempted to 'mean' Non-Numeric array index!")
            if(v is IntData)
                sum += v.value
            else if(v is DecimalData)
                sum += v.value
        }
        return DecimalData(sum / result.data.size)
    }

}