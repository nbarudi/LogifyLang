package ca.bungo.compilers.logic

import ca.bungo.compilers.logic.data.Data
import ca.bungo.compilers.logic.data.FunctionData

class Runtime(var parent: Runtime? = null) {

    val symbolTable:MutableMap<String, Data> = mutableMapOf()

    fun subscope(bindings:Map<String, Data>):Runtime {
        val parentSymbolTable = this.symbolTable
        return Runtime().apply {
            //symbolTable.putAll(parentSymbolTable)
            symbolTable.putAll(bindings)
        }
    }

    fun getVar(name: String): Data {
        return symbolTable[name] ?: parent?.getVar(name) ?: throw RuntimeException("Variable $name not found!")
    }

    fun setVar(name: String, value: Data) {
        if(parent != null){
            try {
                parent!!.getVar(name)
                parent!!.setVar(name, value)
                return
            } catch(ignore: RuntimeException) {}
        }
        symbolTable[name] = value
    }

    fun callFunction(function: FunctionData, args: List<Data>): Data {

        if(function.params.size != args.size) {
            throw RuntimeException("Function called with wrong number of arguments")
        }

        val newScope = subscope(function.params.zip(args).toMap())
        newScope.parent = this
        return function.body.evaluate(newScope)

    }

    override fun toString():String =
        symbolTable.map {
                entry -> "${entry.key} = ${entry.value}"
        }.joinToString("; ")
}