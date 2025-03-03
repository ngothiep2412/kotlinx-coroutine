package lesson

fun returnAny(): Any {
    return -1
}

fun main() {
    val number1: Int = 1 // (primitive type)

    // Reference types: nullable and higher type (autoboxing)
    val number2: Int? = 1 // java.lang.Integer number2 = Integer.valuesOf(1)
    val number3: Int? = 4000 //  java.lang.Integer number2 = Integer.valuesOf(4000)
    val number4: Any? = 5000 // Object number4 = Integer.valuesOf(5000)

    println(number4 === number3)
    println(returnAny().hashCode())

    val object1 = DemoMutable(
        list = mutableListOf(1, 2 ,3 ),
        name = "Hello"
    )

    val object2 = object1.copy()

    println(object2 === object1)

    println(object2.list === object1.list)

    val demo = mutableListOf(1, 2, 3)
    val demo2 = demo // assign ref
}

// tránh sử dụng mutable list
data class DemoMutable(
    val list: MutableList<Int>,
    val name: String
)