package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.*

class BinaryExpr(val left: Expression, val op: String, val right: Expression): Expression() {

    /*
    * "+" -> {
                if(leftValue is IntData){
                    return IntData(leftValue.value + rightValue.value)
                }
                throw RuntimeException("Bad Argument. Not a number!")
            }
            "-" -> {
                if(leftValue is IntData){
                    return IntData(leftValue.value - rightValue.value)
                }
                throw RuntimeException("Bad Argument. Not a number!")
            }
            "*" -> {
                if(leftValue is StringData){
                    var res = ""
                    for(idx in 1 .. rightValue.value){
                        res += leftValue.value
                    }
                    return StringData(res)
                }else if(leftValue is IntData){
                    return IntData(leftValue.value * rightValue.value)
                }
                throw RuntimeException("Bad Argument. Not a number!")
            }
            "/" -> {
                if(leftValue is IntData){
                    return IntData(leftValue.value / rightValue.value)
                }
                throw RuntimeException("Bad Argument. Not a number!")
            }
    * */

    override fun evaluate(runtime: Runtime): Data {
        val leftData = left.evaluate(runtime)
        val rightData = right.evaluate(runtime)

        if((leftData is IntData || leftData is DecimalData) && ((rightData is IntData || rightData is DecimalData))){
            val leftValue: Double = if(leftData is IntData) leftData.value + 0.0 else (leftData as DecimalData).value
            val rightValue: Double = if(rightData is IntData) rightData.value + 0.0 else (rightData as DecimalData).value

            return when(op) {
                "+" -> DecimalData(leftValue + rightValue)
                "-" -> DecimalData(leftValue - rightValue)
                "*" -> DecimalData(leftValue * rightValue)
                "/" -> DecimalData(leftValue / rightValue)
                else -> throw RuntimeException("Bad Argument. Not a number!")
            }
        }

        else if(leftData is StringData && rightData is IntData){

            val rightValue = rightData.value
            val leftValue = leftData.value

            when(op){
                "*" -> {
                    var res = ""
                    for(idx in 1 .. rightValue){
                        res += leftValue
                    }
                    return StringData(res)
                }
                else -> throw RuntimeException("String Arithmetic error! Only supports '*' of a string!")
            }
        }

        throw RuntimeException("Unknown Arithmetic Condition? Using Results: $leftData and $rightData")
    }

}