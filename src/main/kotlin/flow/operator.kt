package flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
//    val flow = (1..10)
//        .asFlow()
//        .map { it * it }
//        .filter{it % 2 == 0}
//        .collect(::println)

//    val flow = (1..10)
//        .asFlow()
//        .map {
//            it * 10
//        }
//        .transform<Int, String> {
//            emit("Emitting $it")
//        }
//        .collect(::println)

    val flow1 = flow<Int> {
        repeat(5) {
            delay(1000)
            emit(it + 1)
        }
    }

    val flow2 = flowOf("A", "B", "C", "D", "E")

    val combineFlow = flow1.zip(flow2){a, b -> "$a -> $b"}
        .collect{
            println(it)
        }
    // zip
}