package coroutine

import kotlinx.coroutines.*

/**
 * ### **`SupervisorJob` là gì?**
 * `SupervisorJob` là một biến thể của `Job` trong Kotlin Coroutines, được dùng khi bạn muốn quản lý các coroutine **độc lập với nhau**, nghĩa là **một coroutine con thất bại sẽ không làm hủy tất cả các coroutine anh em khác**.
 *
 * ---
 *
 * ## **1 Sự khác biệt giữa `Job` và `SupervisorJob`**
 * | **Đặc điểm**            | **`Job` (Mặc định)** | **`SupervisorJob`** |
 * |-------------------------|---------------------|---------------------|
 * | **Hủy coroutine con**   | Khi một coroutine con bị lỗi, **toàn bộ scope (bao gồm các coroutine anh em)** sẽ bị hủy. | Khi một coroutine con bị lỗi, **các coroutine anh em khác vẫn tiếp tục chạy**. |
 * | **Tính độc lập**        | Các coroutine trong scope **phụ thuộc lẫn nhau**. | Các coroutine **hoạt động độc lập** với nhau. |
 * | **Use case**            | Khi các coroutine con liên quan chặt chẽ và **cần hủy toàn bộ nếu có lỗi**. | Khi các coroutine **hoạt động riêng lẻ** và một coroutine lỗi không nên ảnh hưởng đến các coroutine khác. |
 *
 * ---
 *
 * ## **2 Ví dụ minh họa**
 * ### **❌ Khi dùng `Job` (Mặc định) – Tất cả coroutine bị hủy khi có lỗi**
 * ```kotlin
 * val scope = CoroutineScope(Dispatchers.Default + Job())
 *
 * scope.launch {
 *     launch {
 *         delay(1000)
 *         println("✅ Task 1 hoàn thành")
 *     }
 *
 *     launch {
 *         delay(500)
 *         throw RuntimeException("❌ Task 2 gặp lỗi")
 *     }
 *
 *     launch {
 *         delay(1500)
 *         println("✅ Task 3 hoàn thành")
 *     }
 * }
 * ```
 * 🔴 **Kết quả:**
 * ```
 * ❌ Task 2 gặp lỗi
 * (Tất cả các coroutine khác bị hủy và không chạy nữa)
 * ```
 * 👉 **Vì `Job` mặc định hủy toàn bộ scope khi có lỗi.**
 *
 * ---
 *
 * ### **✅ Khi dùng `SupervisorJob` – Chỉ coroutine lỗi bị hủy, các coroutine khác vẫn chạy**
 * ```kotlin
 * val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
 *
 * scope.launch {
 *     launch {
 *         delay(1000)
 *         println("✅ Task 1 hoàn thành")
 *     }
 *
 *     launch {
 *         delay(500)
 *         throw RuntimeException("❌ Task 2 gặp lỗi")
 *     }
 *
 *     launch {
 *         delay(1500)
 *         println("✅ Task 3 hoàn thành")
 *     }
 * }
 * ```
 * 🟢 **Kết quả:**
 * ```
 * ❌ Task 2 gặp lỗi
 * ✅ Task 1 hoàn thành
 * ✅ Task 3 hoàn thành
 * ```
 * 👉 **Vì `SupervisorJob` chỉ hủy coroutine bị lỗi, các coroutine khác vẫn tiếp tục chạy.**
 *
 * ---
 *
 * ## **3 Khi nào nên dùng `SupervisorJob`?**
 * ✅ **Khi muốn các coroutine hoạt động độc lập**, ví dụ:
 * - **Tải nhiều dữ liệu song song**: Nếu một API call thất bại, các API khác vẫn tiếp tục.
 * - **Chạy nhiều tác vụ nền (background work)**: Nếu một task lỗi, không làm hủy toàn bộ hệ thống.
 * - **UI & ViewModel trong Android**: Một coroutine cập nhật UI bị lỗi không nên làm sập toàn bộ ViewModel.
 *
 * ❌ **Không dùng khi các coroutine có quan hệ chặt chẽ**, vì nếu một task lỗi mà các task khác vẫn tiếp tục có thể gây lỗi logic.
 *
 * ---
 *
 * ## **4 Tóm lại**
 * | **Dùng `Job` khi**                | **Dùng `SupervisorJob` khi** |
 * |------------------------------------|----------------------------|
 * | Các coroutine **phụ thuộc nhau**, một cái lỗi thì cả nhóm phải dừng. | Các coroutine **độc lập**, một cái lỗi thì các cái khác vẫn chạy. |
 * | Ví dụ: Transaction ngân hàng (nếu một bước lỗi, rollback tất cả). | Ví dụ: Tải nhiều API song song, một API lỗi không làm hủy tất cả. |
 *
 * 🚀 **Kết luận**: Nếu bạn muốn **các coroutine hoạt động độc lập và không bị hủy khi có lỗi từ coroutine khác**, hãy dùng `SupervisorJob`.
 */

@Suppress("RedundantSuspendModifier")
suspend inline fun <R> runSuspendCatching(block: () -> R): Result<R> {
    return runCatching {  block() }
        .onFailure {
            // rethrow CancellationException -> cancel coroutine
            if (it is CancellationException) throw it
        }
}

suspend fun getApi(): Result<String> {
    return runSuspendCatching {
        delay(100)
        "OK response"
    }
}

fun main(): Unit = runBlocking {
    // launch: CoroutineExceptionHandler
    // uncaught exception

    val scope = CoroutineScope(
        context = Dispatchers.Default + SupervisorJob() +
        CoroutineExceptionHandler{coroutineContext, throwable ->
            println(">>> CoroutineExceptionHandler: $throwable")
            println(">>> CoroutineExceptionHandler: $coroutineContext")
        }
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

    scope.launch {
        println("launch 1")
        delay(1)
        println("launch 1 throws ...")
        throw RuntimeException("launch 1 failed")
    }

    scope.launch {
        println("launch 2")
        delay(1000)
        println("launch 2 throws ...")
    }

    scope.launch {
        println("launch 3")
        delay(100)
        println("launch 3 done ...")
    }

    delay(5_5000)
}
