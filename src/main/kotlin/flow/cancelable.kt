package flow

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    launch {
        (1..10)
            .asFlow()
//            .onEach {
//                delay(100) // -> suspend point
//            }
            .onCompletion {
                if (it is CancellationException) {
                    println("Flow was canceled")
                }
            }
            .cancellable()
            .collect{
                println(it)
                if (it % 2 == 0) {
                    cancel()
                }
        }
    }

    Unit
}