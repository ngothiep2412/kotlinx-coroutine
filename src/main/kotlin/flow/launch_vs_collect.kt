package flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
//    flow<Int> {
//        emit(1)
//        emit(2)
//        emit(3)
//    }.collect {
//        println("Collecting 2: $it")
//    }

    flow<Int> {
        emit(1)
        emit(1)
        emit(1)
    }.onEach {
        println(it)
    }.launchIn(this)

    println("Done")
}