package org.example

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{

    val def = async {
        function1()
    }

    val def2 = async {
        function1()
    }

    val result: List<Int> = awaitAll(def, def2)
    println(result)



}