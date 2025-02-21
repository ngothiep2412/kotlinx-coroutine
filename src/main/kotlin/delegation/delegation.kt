package delegation

interface Base {
    fun print()
}

class BaseImpl(private val x: Int): Base {
    override fun print() = println("BaseImpl: $x")
}

class AnotherImpl(private val x: String): Base {
    override fun print() = println("AnotherImpl: $x")
}

class Derived(private val b: Base): Base {
    override fun print() {
        b.print()
    }
}

class Derived1(private val b: Base): Base by b

fun main() {
    val b = AnotherImpl("Hello")
    Derived(b).print() // prints 10
}