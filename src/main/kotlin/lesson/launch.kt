package lesson

/**
 * ### `launch {}` trong Kotlin Coroutines là gì?
 * `launch {}` là một hàm khởi chạy một **coroutine mới** mà không chặn luồng hiện tại.
 *
 * ---
 *
 * ## 🌟 **Tóm tắt về `launch {}`**
 * - **Chạy bất đồng bộ** (không chặn coroutine cha).
 * - **Không trả về kết quả** (dùng khi không cần giá trị trả về).
 * - **Thích hợp cho các tác vụ chạy nền** như tải dữ liệu, ghi log, gửi request API, v.v.
 *
 * ---
 *
 * ## 🛠 **So sánh `launch {}` và gọi trực tiếp (không dùng `launch`)**
 * ### 1 **Dùng `launch` (chạy song song)**
 * ```kotlin
 * suspend fun doWorld() = coroutineScope {
 *     launch {
 *         delay(2000L)
 *         println("World 2")
 *     }
 *
 *     launch {
 *         delay(100L)
 *         println("World 1")
 *     }
 *
 *     println("Hello")
 * }
 * ```
 * 📝 **Kết quả in ra:**
 * ```
 * Hello
 * World 1
 * World 2
 * ```
 * 🔹 **Giải thích:**
 * - `launch {}` tạo coroutine mới, chạy **song song**.
 * - `println("Hello")` chạy ngay lập tức.
 * - `World 1` in sau 100ms.
 * - `World 2` in sau 2000ms.
 *
 * ---
 *
 * ### 2 **Không dùng `launch` (chạy tuần tự)**
 * ```kotlin
 * suspend fun doWorld() = coroutineScope {
 *     delay(2000L)  // Chặn toàn bộ coroutineScope trong 2 giây
 *     println("World 2")
 *
 *     delay(100L)  // Chặn thêm 100ms
 *     println("World 1")
 *
 *     println("Hello")
 * }
 * ```
 * 📝 **Kết quả in ra:**
 * ```
 * (World 2 sau 2 giây)
 * World 2
 * (World 1 sau 100ms nữa)
 * World 1
 * Hello
 * ```
 * 🔹 **Giải thích:**
 * - `delay(2000L)` **chặn** toàn bộ `doWorld()`, nên `"World 2"` in trước.
 * - Sau đó `delay(100L)`, `"World 1"` in ra.
 * - `"Hello"` in ra cuối cùng.
 *
 * ---
 *
 * ## 🤔 **Khi nào dùng `launch {}`?**
 * ✔ Khi cần **chạy song song** mà không chặn coroutine cha.
 * ✔ Khi không cần lấy kết quả trả về (ví dụ: tải dữ liệu, ghi log).
 * ✔ Khi muốn khởi chạy nhiều coroutine cùng lúc.
 *
 * ## ⚠ **Khi nào KHÔNG nên dùng `launch {}`?**
 * ❌ Khi cần lấy giá trị trả về → Dùng `async {}` với `await()`.
 * ❌ Khi muốn đảm bảo tuần tự → Không dùng `launch`, gọi trực tiếp.
 *
 * ---
 *
 * ## ✅ **Kết luận**
 * - `launch {}` giúp **chạy bất đồng bộ** mà **không chặn coroutine cha**.
 * - Nếu không dùng `launch {}`, `delay()` sẽ chặn toàn bộ coroutine cha.
 * - Dùng `launch {}` khi muốn chạy nhiều coroutine song song mà không cần kết quả trả về.
 */
fun main() {}