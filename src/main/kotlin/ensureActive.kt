package org.example

import kotlinx.coroutines.*

fun main() = runBlocking{
    val job = launch(Dispatchers.Default) {
        repeat(5) {
            if (it == 3) {
                cancel()
            }
            ensureActive()
            println("job: I'm sleeping $it ...")
        }
    }
}