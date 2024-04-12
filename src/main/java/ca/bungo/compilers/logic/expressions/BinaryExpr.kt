package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.BooleanData
import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.IntData
import ca.bungo.compilers.logic.data.StringData

class BinaryExpr(val left: Expression, val op: String, val right: Expression): Expression() {

    override fun evaluate(runtime: Runtime): Data {
        val leftValue = left.evaluate(runtime)
        val rightValue = right.evaluate(runtime) as IntData
        when(op){
            "+" -> {
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
            "<" -> {
                if(leftValue is IntData){
                    return BooleanData(leftValue.value < rightValue.value)
                }
                throw RuntimeException("Bad Argument. Not a number!")
            }
            ">" -> {
                if(leftValue is IntData){
                    return BooleanData(leftValue.value > rightValue.value)
                }
                throw RuntimeException("Bad Argument. Not a number!")
            }
            "==" -> {
                if(leftValue is IntData){
                    return BooleanData(leftValue.value == rightValue.value)
                }
                throw RuntimeException("Bad Argument. Not a number!")
            }
            "<=" -> {
                if(leftValue is IntData){
                    return BooleanData(leftValue.value <= rightValue.value)
                }
                throw RuntimeException("Bad Argument. Not a number!")
            }
            ">=" -> {
                if(leftValue is IntData){
                    return BooleanData(leftValue.value >= rightValue.value)
                }
                throw RuntimeException("Bad Argument. Not a number!")
            }
            else -> throw RuntimeException("Unsupported Operator : $op")
        }
    }

}