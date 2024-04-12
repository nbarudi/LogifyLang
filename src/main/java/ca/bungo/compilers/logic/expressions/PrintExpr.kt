package ca.bungo.compilers.logic.expressions

import ca.bungo.compilers.Main
import ca.bungo.compilers.logic.Runtime
import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.None
import ca.bungo.compilers.logic.data.StringData

class PrintExpr(val expr: Expression): Expression() {

    override fun evaluate(runtime: Runtime): Data {
        val value = expr.evaluate(runtime)
        if(value is StringData) {
            println(value.value)
        } else{
            println(value.toString())
        }
        return None
    }

}