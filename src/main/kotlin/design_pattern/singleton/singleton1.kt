package design_pattern.singleton

object Singleton1 {
    init {
        println("$this instance is initialized")
    }
    fun doSomething() {
        println("$this: Doing something...")
    }
}


fun main() {
    println("Start")
    Singleton1
    println("---")
    Singleton1.doSomething()
    Singleton1.doSomething()
    Singleton1.doSomething()
    Singleton1.doSomething()
    println("Done")
    println(Singleton1 === Singleton1)
}
