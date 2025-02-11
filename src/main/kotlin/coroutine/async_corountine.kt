package org.example.coroutine

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val def: Deferred<Int> = async {
        function1()
    }

    val result = def.await()
    println(result)
}

suspend fun function1() :Int {
    delay(2_000)
    return 2
}