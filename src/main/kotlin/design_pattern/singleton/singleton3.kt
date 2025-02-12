package design_pattern.singleton

import kotlinx.coroutines.*

/**
 * Double-checked locking
 */
class Singleton3 private constructor(
    private val name: String,
    private val counter: Int,
) {
    init {
        println("$this instance is initialized")
    }

    fun doSomething() {
        println("$this: Doing something...")
    }

    override fun toString(): String {
        return "Singleton3(name=$name, counter=$counter)"
    }

    companion object {
        // Đảm bảo mọi thread đều thấy đc INSTANCE này
        @Volatile
        private var INSTANCE: Singleton3? = null

        // Double-checked locking
        fun getInstance(name: String, counter: Int): Singleton3 {
            // (1)
            val i = INSTANCE
            if (i != null)  {
                return i
            }

            // (2)
            return synchronized(this) {
                // (3)
                val i = INSTANCE
                if (i != null)  {
                    return@synchronized i
                }

                // (4)
                Singleton3(name, counter).also { INSTANCE = it }
            }
        }

        fun getInstanceUsingElvis(name: String, counter: Int): Singleton3 =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Singleton3(name, counter).also { INSTANCE  = it}
            }
    }


}


@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
fun main() = runBlocking{
  println("Before")
    repeat(10) {
        launch( newSingleThreadContext(name = "My-Thread-$it")) {
            delay(10L -it)
            Singleton3.getInstanceUsingElvis(name = "Hello-$it", counter = it).doSomething()
        }
    }

    delay(5_000)

}
