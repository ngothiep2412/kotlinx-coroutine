package org.example.coroutine

import kotlinx.coroutines.*

fun main() = runBlocking{
    val scope = CoroutineScope(Dispatchers.Default)

    val job = scope.launch {
       val result: Int =  withContext(Dispatchers.IO) {
           println("Current Thread withContext ${currentCoroutineContext()}")
            delay(2000)
            10
        }

        println("Result is $result")

        println("Current Thread launch ${currentCoroutineContext()}")

    }

    job.join()


}