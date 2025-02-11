package org.example.coroutine

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = launch {
        repeat(5) {i ->
            try {
                println("job: I'm sleeping $i ...")
                delay(500)
            } finally {
              withContext(NonCancellable) {
                  delay(100)
                  println("job: I'm already to quit")
              }
            }
        }
    }

    delay(1300L)
    println("main: I'm tired of waiting!")
    job.cancelAndJoin()
    println("main: now I can quit.")
}