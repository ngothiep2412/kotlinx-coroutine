package flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

fun simpleFlow1(): Flow<Int> = flow {
    emit(1)
    emit(2)
}

fun main() = runBlocking {
    val secondFlow: Flow<Int> = flowOf(1, 2, 3, 4, 5, 6)

//    secondFlow.collect{
//        println("Collecting $it")
//    }

//    listOf("a", "b", "c").asFlow().collect{
//        println("Collecting 1: $it")
//    }

    flow<Int> {
        delay(2000)
        emit(1)
        emitAll(secondFlow)
    }.filter { it % 2 == 0 }
        .collect {
            println("Collecting 2: $it")
        }
}