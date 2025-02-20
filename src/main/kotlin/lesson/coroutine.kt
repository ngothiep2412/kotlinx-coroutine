package lesson

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random


// cancel nhưng mà ko check lại coroutine thì có khả năng sẽ vẫn tiếp tục chạy coroutine mặc dù đã cancel rồi
suspend fun main() {
    var count = 0
    val scope = CoroutineScope(Dispatchers.Default).launch {
        var random = Random.nextInt(100_000)
        while (random != 50000 && isActive) {
            count++
            println("Count: $count")
            random = Random.nextInt(100_000)
        }
    }

    delay(500L)
    scope.cancel()
}