package ca.bungo.compilers.logic.data

class RangeData(val start: Int, val end: Int): Data() {
    fun toList(): List<Int> = (start .. end).toList()

    override fun toString(): String = "$start..$end"
}