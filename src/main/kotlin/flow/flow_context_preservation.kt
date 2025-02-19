package flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    launch(Dispatchers.Default) {
        println("${currentCoroutineContext()}")
        (1..5).asFlow()
            .onEach {
                println("Emitting ${currentCoroutineContext()}")
            }
            .flowOn(Dispatchers.IO)
            .flowOn(Dispatchers.Main)
            .collect {
                println("Collected ${currentCoroutineContext()}")
            }
    }

    changeContext().collect { value -> println(value) }
}

// vi phạm tính chất boản toàn của flow

//fun wrongContext(): Flow<Int> = flow {
//    withContext(Dispatchers.Default) {
//        for (i in 1..3) {
//            Thread.sleep(100)
//            emit(i)
//        }
//    }
//}

// -> giải pháp flowOn
fun changeContext(): Flow<Int> = flow {
    println("Flow started ${currentCoroutineContext()}")
    for (i in 1..3) {
        Thread.sleep(100)
        emit(i)
    }
}.flowOn(Dispatchers.Default)