package coroutine

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = launch {
        repeat(5) { i ->
            try {
                println("job: I'm sleeping $i ...")
                delay(500)
            } catch (cancel: CancellationException) {
                throw cancel
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    delay(1300L)
    println("main: I'm tired of waiting!")
    job.cancelAndJoin()
    println("main: now I can quit.")
}