package org.example

import kotlin.concurrent.thread

class MyThread: Thread() {

    override fun run() {
        for (i in 1 .. 10) {
            println("MyThread is running $i")
        }
    }
}

class MyRunnable: Runnable {
    override fun run() {
        for (i in 1 .. 10) {
            println("MyRunnable is running $i")
        }
    }
}

fun fetchToken(): Int {
    Thread.sleep(1000L)
    return 1
}

fun fetchItemToken(accessToken: Int): Int {
    Thread.sleep(1000L)
    return accessToken + 10
}

fun renderUI(item: Int) {
    println("Render UI with itemToken $item")
}


fun main() {
//    val myThread = MyThread()
//    myThread.start()
//
    println("Current thread is running ${Thread.currentThread().name}")

    thread {
        val accessToken = fetchToken()
        val fetchItemToken = fetchItemToken(accessToken)

        renderUI(fetchItemToken)
    }
    println("Main thread is finished")

//
//    val stThread = thread {
//        println("Current thread is running ${Thread.currentThread().name}")
//    }
}