package ca.bungo.compilers.logic.data

class ArrayData(var data: MutableList<Data>) : Data() {

    override fun toString(): String {
        var array = "["
        for (element in data) {
            array += element.toString()
            array += ", "
        }
        array = array.substring(0, array.length - 2)
        array += "]"
        return array
    }

}