package flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

// FLow - Rx
// flatMapConcat - flatMapConcat
// flatMapMerge - flatMap
// flatMapLatest - switchMap

fun requestFlow(i: Int) = flow {
    emit("$i: First")
    delay(2)
    emit("$i: Second")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking {
    (1..3)
        .asFlow()
        .onEach { delay(100) }
        .flatMapMerge {
            requestFlow(it)
        }
        .collect {
            println(it)
        }

    val a = flowOf(1, 2, 3, 4).first()
    Unit
}