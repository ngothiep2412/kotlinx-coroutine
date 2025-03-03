package coroutine

import kotlinx.coroutines.*
import java.util.concurrent.Executors


@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
fun main() = runBlocking{
    val singleThread = newSingleThreadContext(name = "MyThreadDispatcher")
    val fromExecutor = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    withContext(Dispatchers.Unconfined) {
        withContext(singleThread) {
            println("[1] thread=${Thread.currentThread().name}")
            delay(100)
        }

        println("[2] thread=${Thread.currentThread().name}")
        delay(100)

        withContext(fromExecutor) {
            println("[3] thread=${Thread.currentThread().name}")
            delay(100)
        }

        println("[4] thread=${Thread.currentThread().name}")
        delay(100)

        println("[5] thread=${Thread.currentThread().name}")
    }

    singleThread.close()
    fromExecutor.close()
}