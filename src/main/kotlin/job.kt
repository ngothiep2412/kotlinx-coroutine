package org.example

import kotlinx.coroutines.*

fun main() = runBlocking {

    val scope = CoroutineScope(Dispatchers.IO)

    val job = scope.launch {
        val job = currentCoroutineContext().job
        job.ensureActive()
        println("Current job: $job")

        println("Hello")
        delay(500) // suspend
        println("DOne")
    }
    println(" job: $job")


    println("isActive: ${job.isActive}")
    println("isCompleted: ${job.isCompleted}")
    println("isCancelled: ${job.isCancelled}")
    println("---")
//    job.cancelAndJoin() // cancel -> join (wait for completion)
    job.join()

    println("isActive: ${job.isActive}")
    println("isCompleted: ${job.isCompleted}")
    println("isCancelled: ${job.isCancelled}")
    Unit
}