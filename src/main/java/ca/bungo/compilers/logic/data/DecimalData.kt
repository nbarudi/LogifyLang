package ca.bungo.compilers.logic.data

class DecimalData(val value: Double) : Data() {
    override fun toString(): String = value.toString()
}