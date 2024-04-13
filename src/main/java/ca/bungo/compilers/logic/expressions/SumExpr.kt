package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.ArrayData
import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.DecimalData
import ca.bungo.compilers.logic.data.IntData

class SumExpr(var expression: Expression) : Expression() {

    override fun evaluate(runtime: Runtime): Data {
        val result = expression.evaluate(runtime) as ArrayData
        var sum = 0.0
        for(data in result.data){
            if(data is IntData)
                sum += data.value
            if(data is DecimalData)
                sum += data.value
        }
        return DecimalData(sum)
    }

}