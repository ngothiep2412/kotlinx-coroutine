package org.example.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

fun main() = runBlocking{
    var counter = 0

    val lock = Any()

    val mutex = Mutex()

    withContext(Dispatchers.Default.limitedParallelism(1)) {
        repeat(1000) {
            launch {
//                mutex.lock()
//                counter++
//                mutex.unlock()
                mutex.withLock {
                    counter++
                }
            }
        }

        println("Thread withContext ${currentCoroutineContext()}")
    }

    println("Counter is $counter")


}