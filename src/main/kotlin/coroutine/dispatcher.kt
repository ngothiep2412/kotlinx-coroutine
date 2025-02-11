package org.example.coroutine

import kotlinx.coroutines.*
import java.util.concurrent.Executors


fun main() {
    Dispatchers.IO
    Dispatchers.Default
    Dispatchers.Unconfined
    val singleThread = newSingleThreadContext(name = "MyThreadDispatcher")
    val fromExecutor = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    val scope = CoroutineScope(Dispatchers.IO.limitedParallelism(2))
    println("Processor count: $${Runtime.getRuntime().availableProcessors()}")
    runBlocking {
        repeat(100) { i ->
            scope.launch {
                println("START $i -- thread= ${Thread.currentThread().name}")
                Thread.sleep(500)
                println("END $i -- thread = ${Thread.currentThread().name}")
            }
        }
        delay(15_000)
    }
}