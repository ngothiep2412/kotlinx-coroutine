package coroutine

import kotlinx.coroutines.*

fun main() {

    // AndroidX provided extension functions:
    // ViewModel: viewModelScope init -> onCleared
    // Activity: lifecycleScope onCreate -> onDestroy
    // Fragment: lifecycleScope onCreate -> onDestroy
    // Fragment view: viewLifecycleOwner.lifecycleScope onViewCreated -> onDestroyView
    // Scope.cancel -> prevent memory leak
    val scope = CoroutineScope(context = Dispatchers.IO + Job())
    println(scope)

    // scope has context -> 1 parent Job
    // scope.cancel() -> parentJob.cancel() -> children.forEach {it.cancel()}

    val job: Job = scope.launch {
        println("hello")
        launch {
            launch {
                launch {
                    launch {
                        launch {

                        }
                    }
                }
            }
        }
    }
    job.cancel()

    runBlocking {
        val jobs = List(100) {
            i ->
            scope.launch {
                println(">>> START corountine $i")
                launch {
                    launch {
                        launch {
                            launch {
                                launch {
                                    println(">>> before run $i")
                                    delay(600)
                                    println(">>> after run $i")
                                }
                            }
                        }
                    }
                }
            }
        }

        delay(2_000)

        val children = scope.coroutineContext.job.children.toList()
        children.forEach{
            println(">>> $it")
        }

        delay(100)
        scope.cancel()
    }
}