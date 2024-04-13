package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.BooleanData
import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.DecimalData
import ca.bungo.compilers.logic.data.IntData

class ConditionalExpr(val left: Expression, val op: String, val right: Expression) : Expression() {

    override fun evaluate(runtime: Runtime): Data {
        val leftData = left.evaluate(runtime)
        val rightData = right.evaluate(runtime)
        if((leftData is IntData || leftData is DecimalData) && ((rightData is IntData || rightData is DecimalData))){
            val leftValue: Double = if(leftData is IntData) leftData.value + 0.0 else (leftData as DecimalData).value
            val rightValue: Double = if(rightData is IntData) rightData.value + 0.0 else (rightData as DecimalData).value

            return when(op){
                "<" -> BooleanData(leftValue < rightValue)
                ">" -> BooleanData(leftValue > rightValue)
                "<=" -> BooleanData(leftValue <= rightValue)
                ">=" -> BooleanData(leftValue >= rightValue)
                "==" -> BooleanData(leftValue == rightValue)
                else -> throw RuntimeException("Bad Argument. This shouldn't be possible!")
            }
        }
        //ToDo(String Comparison)
        throw RuntimeException("Comparing non numbers!")
    }
}