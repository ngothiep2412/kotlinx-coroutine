package org.example.coroutine

import kotlinx.coroutines.*
import java.util.concurrent.Executors


@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
fun main() {
    Dispatchers.IO
    Dispatchers.Default
    Dispatchers.Unconfined
    val singleThread = newSingleThreadContext(name = "MyThreadDispatcher")
    val fromExecutor = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    val scope = CoroutineScope(Dispatchers.IO)
    println("Processor count: $${Runtime.getRuntime().availableProcessors()}")
    runBlocking {
        repeat(100) { i ->
            /**
             * ở đây sẽ chạy thread ở Default
             */
            scope.launch(Dispatchers.Default) {
                println("START $i -- thread= ${Thread.currentThread().name}")

                /**
                 * Nên để Dispatchers.IO vì Thread.sleep sẽ chặn Main Thread nên xài IO
                 */
                withContext(Dispatchers.IO) {
                    Thread.sleep(500)
                }

                println("END $i -- thread = ${Thread.currentThread().name}")
            }
        }
        delay(15_000)
    }
}