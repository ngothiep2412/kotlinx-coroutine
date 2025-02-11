package org.example.coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.time.measureTime

fun main() = runBlocking {
    val timeRun = measureTime {
        val value1 = function1()
        val value3 = function1()

        println("Sum is ${value3 + value1}")
    }

    println("Time run is $timeRun")

    main1()
}

fun main1() = runBlocking {
    val timeRun = measureTime {
        val deferred1 = async { function1() }
        val deferred2 = async { function1() }

        val value1 = deferred1.await()
        val value2 = deferred2.await()
        println("Sum is ${value1 + value2}")
    }
    println("Time run is $timeRun")
}