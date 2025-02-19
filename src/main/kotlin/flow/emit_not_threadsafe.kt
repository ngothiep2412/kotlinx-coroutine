package flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val createFlow: Flow<Int> = channelFlow<Int> {
      launch {
         launch {
            repeat(3) {
                delay(1000)
                send(1)
            }
         }
      }
    }

    createFlow.collect{
        println(it)
    }

    println("Done")
    Unit
}