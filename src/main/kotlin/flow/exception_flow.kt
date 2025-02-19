package flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
//    try {
//        createFlowExp().collect { value ->
//
//            val result = 10 / value
//            println(result)
//        }
//    } catch (e: Exception) {
//        println("Exception: $e")
//    }
    createFlowExp()
        .onEach {
            delay(1000)
        }
        .onEach { value ->
            val result = 10/ value
            println(result)
        }
        .catch { e -> println("Caught: $e") }
        .collect {}
    Unit
}

fun createFlowExp(): Flow<Int> = flow {
    for (i in 3 downTo -3) {
        emit(i)
    }
}