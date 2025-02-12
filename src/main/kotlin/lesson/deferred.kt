package lesson

import kotlinx.coroutines.*

/**
 * Trong kotlin, [Deferred] là một interface trong coroutines,
 * đại diện cho một giá trị có sẵn trong tưởng lai
 *
 * 📌`Tổng quan về Deferred`
 *
 *  [Deferred] là một coroutine trả về một kiểu T sau khi hoàn thành.
 *  Được tạo bằng async {} trong Kotlin.
 *  Lấy kết quả bằng cách sử dụng .await(), có thể bị suspend cho đến khi giá trị sẵn sàng.
 *
 */

fun main() = runBlocking {
    val def1: Deferred<String> = async {
        delay(100)
        "Ok"
    }

    println("Waiting for result")
    val result = def1.await()
    println("Received: $result")
}
/**
 * 📌 Giải thích:
 * async { } tạo một Deferred<String> chạy bất đồng bộ.
 * await() đợi kết quả nhưng không block thread chính.
 * Khi await() được gọi, nó sẽ suspend coroutine hiện tại cho đến khi Deferred hoàn thành.
 */

