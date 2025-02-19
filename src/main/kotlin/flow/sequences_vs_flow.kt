package flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// sequence xử lí đồng bộ
fun createSequence(): Sequence<Int> = sequence {
    println("Sequence started ${System.currentTimeMillis()}")

    for (i in 1..3) {
        yield(i)
    }
}

fun createFlowNonBlock() = flow<Int> {
    for (i in 1..3 ) {
        delay(1000)
        emit(i)
    }
}

fun main(): Unit = runBlocking {


    launch {
        println("Current Thread: ${Thread.currentThread().name}")
        for (i in 1..3) {
            delay(1000)
            println("Emitting $i")
        }
    }

    createFlowNonBlock().collect{
        println("Collecting $it")
    }
//
//    createSequence().forEach {
//        println("Collecting $it")
//    }

}