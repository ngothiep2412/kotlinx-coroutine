package org.example

import kotlinx.coroutines.*



fun main(): Unit = runBlocking {
    // launch: CoroutineExceptionHandler
    // uncaught exception

    val scope = CoroutineScope(
        context = Dispatchers.Default + Job()
    )

    class ViewModel {
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

    val d1 = scope.async{
        println("launch 1")
        delay(1)
        println("launch 1 throws ...")
        throw RuntimeException("launch 1 failed")
    }

    val d2 = scope.async {
        println("launch 2")
        delay(1000)
        println("launch 1 throws ...")
    }

    val d3 = scope.async {
        println("launch 3")
        delay(100)
        println("launch 3 done ...")
    }



    try {
        d1.await()
    } catch (e: RuntimeException) {
        println("d1 failed: $e")
    }

//
//    try {
//        d2.await()
//    } catch (e: RuntimeException) {
//        println("d2 failed: $e")
//    }

    delay(5_5000)
}
