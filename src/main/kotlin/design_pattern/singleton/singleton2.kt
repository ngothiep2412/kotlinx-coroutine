package design_pattern.singleton

/**
 * Lazy delegate cho phép chúng ta khởi tạo một object một cách lazy
 * (chỉ khi nào cần thì mới khởi tạo).
 * Điều này giúp chúng ta tránh việc khởi tạo object khi chúng ta không cần sử dụng nó.
 */

class Singleton2 private constructor() {
    init {
        println("$this instance is initialized")
    }

    fun doSomething() {
        println("$this: Doing something...")
    }

    companion object {
        val INSTANCE by lazy {Singleton2()}
    }
}


fun main() {
    println("Start")
    Singleton2.INSTANCE


    Singleton2.INSTANCE.doSomething()
    Singleton2.INSTANCE.doSomething()
    Singleton2.INSTANCE.doSomething()
    Singleton2.INSTANCE.doSomething()

    check(Singleton2.INSTANCE == Singleton2.INSTANCE)

}
