package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.BooleanData
import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.DecimalData
import ca.bungo.compilers.logic.data.IntData
import ca.bungo.compilers.logic.data.StringData

class ConditionalExpr(val left: Expression, val op: String, val right: Expression) : Expression() {

    override fun evaluate(runtime: Runtime): Data {
        val leftData = left.evaluate(runtime)
        val rightData = right.evaluate(runtime)

        if (leftData is IntData || leftData is DecimalData) {
            // Handle numeric comparison
            if (rightData is IntData || rightData is DecimalData) {
                val leftValue: Double = if (leftData is IntData) leftData.value + 0.0 else (leftData as DecimalData).value
                val rightValue: Double = if (rightData is IntData) rightData.value + 0.0 else (rightData as DecimalData).value

                return when (op) {
                    "<" -> BooleanData(leftValue < rightValue)
                    ">" -> BooleanData(leftValue > rightValue)
                    "<=" -> BooleanData(leftValue <= rightValue)
                    ">=" -> BooleanData(leftValue >= rightValue)
                    "==" -> BooleanData(leftValue == rightValue)
                    else -> throw RuntimeException("Bad Argument. This shouldn't be possible!")
                }
            } else {
                throw RuntimeException("Comparing number with non-number!")
            }
        } else if (leftData is BooleanData && rightData is BooleanData) {
            // Handle boolean comparison
            return when (op) {
                "==" -> BooleanData(leftData.value == rightData.value)
                else -> throw RuntimeException("Bad Argument. This shouldn't be possible!")
            }
        } else if (leftData is StringData && rightData is StringData) {
            // Handle string comparison
            return when (op) {
                "==" -> BooleanData(leftData.value == rightData.value)
                else -> throw RuntimeException("Bad Argument. This shouldn't be possible!")
            }
        } else {
            throw RuntimeException("Unsupported comparison types!")
        }
    }
}
