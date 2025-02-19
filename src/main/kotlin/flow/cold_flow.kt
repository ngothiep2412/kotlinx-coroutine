package flow

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

// cold_flow -> xử lý bất đồng bộ
fun simpleFlow() = flow<Int> {
    println("Flow started ${System.currentTimeMillis()}")

    for (i in 1..3) {
        println("Emitting $i")
        emit(i)
    }
}

fun main() = runBlocking {
    println("Calling simpleFlow...")
    val simpleFlow = simpleFlow()

    simpleFlow.collect {}

    println("Calling again simpleFlow...")
    val simpleFlow2 = simpleFlow()

    simpleFlow2.collect {}
}