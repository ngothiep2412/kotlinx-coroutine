package org.example.coroutine

import kotlinx.coroutines.*



fun main(): Unit = runBlocking {
    // launch: CoroutineExceptionHandler
    // uncaught exception

    val scope = CoroutineScope(
        context = Dispatchers.Default + Job() +
        CoroutineExceptionHandler{coroutineContext, throwable ->
            println(">>> CoroutineExceptionHandler: $throwable")
            println(">>> CoroutineExceptionHandler: $coroutineContext")
        }
    )

    class VIewModel {
        suspend fun getApi() = "OK"

        fun doSomething() {
            scope.launch {
                try {
                    getApi()
                } catch (cancel: CancellationException) {
                    throw cancel
                } catch( e: Throwable) {
                    // show error to UI
                }
            }
        }
    }

    scope.launch {
        println("launch 1")
        delay(1)
        println("launch 1 throws ...")
        throw RuntimeException("launch 1 failed")
    }

    scope.launch {
        println("launch 2")
        delay(1000)
        println("launch 1 throws ...")
    }

    scope.launch {
        println("launch 3")
        delay(100)
        println("launch 3 done ...")
    }

    delay(5_5000)
}
