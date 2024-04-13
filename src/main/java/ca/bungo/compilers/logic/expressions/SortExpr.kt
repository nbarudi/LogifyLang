package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.ArrayData
import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.DecimalData
import ca.bungo.compilers.logic.data.IntData

class SortExpr(val expression: Expression) : Expression() {

    override fun evaluate(runtime: Runtime): Data {
        val result = expression.evaluate(runtime) as ArrayData
        val newData = result.data.toMutableList()
        bubbleSort(newData)
        return ArrayData(newData)
    }

    private fun bubbleSort(arr: MutableList<Data>) {
        var n = arr.size
        var temp: Data
        var swapped: Boolean
        do {
            swapped = false
            for (i in 1 until n) {
                if(arr[i] !is IntData && arr[i] !is DecimalData) throw RuntimeException("Attempted to call sort on Non-Numeric array element!")

                var numA = 0.0
                var numB = 0.0

                numB = if(arr[i] is DecimalData)
                            (arr[i] as DecimalData).value
                        else
                            (arr[i] as IntData).value + 0.0

                numA = if(arr[i-1] is DecimalData)
                            (arr[i-1] as DecimalData).value
                        else
                            (arr[i-1] as IntData).value + 0.0

                if (numA > numB) {
                    // Swap elements
                    temp = arr[i - 1]
                    arr[i - 1] = arr[i]
                    arr[i] = temp
                    swapped = true
                }
            }
            n--
        } while (swapped)
    }

}