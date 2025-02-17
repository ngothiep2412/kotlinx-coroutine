package lesson

import kotlinx.coroutines.*

fun main() = runBlocking {
    // Một scope coroutine với SupervisorJob, điều này là không tốt khi dùng trong withContext
    val supervisorJob = SupervisorJob()

    // Fix:
    val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    try {
        // supervisorJob -> parent
        withContext( supervisorJob) {
            val job1 = launch {
                delay(1000)
                println("Job 1 completed.")
            }

            val job2 = launch {
                delay(500)
                throw Exception("Job 2 failed.")
            }

            job1.join()
            job2.join()
        }
    } catch (e: Exception) {
        println("Caught exception: ${e.message}")
    }
}