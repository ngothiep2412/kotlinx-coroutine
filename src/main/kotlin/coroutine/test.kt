package coroutine

import kotlinx.coroutines.*

fun main()= runBlocking {

       doWorld()
       println("Done")

}

suspend fun doWorld() = coroutineScope {
//    launch {
        delay(2000L)
        println("World 2")
//    }

    withContext(Dispatchers.IO) {

    }

    launch {
        delay(100L)
        println("World 1")
    }

    println("Hello")
}