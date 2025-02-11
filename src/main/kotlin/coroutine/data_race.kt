package org.example.coroutine

// Race condition
// Cyclic barrier


fun main() {
    val nonSync = NonSync()
    nonSync.count()
}

class NonSync {
    private var counter = 0;
    fun count() {
        val thread1 = Thread {
            repeat(10000) {
                counter++
            }
        }

        val thread2 = Thread {
            repeat(10000) {
                counter++
            }
        }

        thread1.start()
        thread2.start()

        thread1.join()
        thread2.join()

        println("Counter = $counter")
    }
}