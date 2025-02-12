package design_pattern.builder

/**
 * ### 🏗 **Builder Pattern là gì?**
 * **Builder Pattern** là một mẫu thiết kế (design pattern) thuộc nhóm **Creational Patterns**, giúp xây dựng **đối tượng phức tạp** một cách linh hoạt và dễ đọc.
 *
 * ---
 *
 * ### 📌 **1. Vì sao cần Builder Pattern?**
 * Giả sử bạn có một class với nhiều tham số, nếu dùng **constructor truyền thống**, bạn sẽ gặp các vấn đề sau:
 *
 * ❌ **Constructor quá dài:**
 * ```kotlin
 * val user = User("John", "Doe", 30, "USA", "john@example.com", "123-456")
 * ```
 * ❌ **Khó đọc, khó hiểu:** Không biết tham số nào là gì.
 *
 * ❌ **Nhiều constructor overload:**
 * ```kotlin
 * class User(val firstName: String, val lastName: String, val age: Int) {
 *     constructor(firstName: String, lastName: String) : this(firstName, lastName, 0)
 * }
 * ```
 * Làm vậy dễ gây rối khi mở rộng.
 *
 * ---
 *
 * ### 📌 **2. Cách triển khai Builder Pattern trong Kotlin**
 * ✔️ **Tạo một class `Builder` bên trong class chính để khởi tạo đối tượng linh hoạt.**
 *
 * #### **🔹 Ví dụ 1: Builder Pattern đơn giản**
 * ```kotlin
 * class User private constructor(
 *     val firstName: String?,
 *     val lastName: String?,
 *     val age: Int?
 * ) {
 *     class Builder {
 *         private var firstName: String? = null
 *         private var lastName: String? = null
 *         private var age: Int? = null
 *
 *         fun setFirstName(firstName: String) = apply { this.firstName = firstName }
 *         fun setLastName(lastName: String) = apply { this.lastName = lastName }
 *         fun setAge(age: Int) = apply { this.age = age }
 *
 *         fun build() = User(firstName, lastName, age)
 *     }
 * }
 * ```
 *
 * #### **🔹 Cách sử dụng Builder**
 * ```kotlin
 * val user = User.Builder()
 *     .setFirstName("John")
 *     .setLastName("Doe")
 *     .setAge(30)
 *     .build()
 *
 * println(user.firstName) // John
 * println(user.age) // 30
 * ```
 * ✔️ **Dễ đọc**
 * ✔️ **Linh hoạt** (không cần truyền tất cả tham số)
 *
 * ---
 *
 * ### 📌 **3. Builder Pattern với `data class` trong Kotlin**
 * Kotlin hỗ trợ **named parameters & default values**, nên Builder Pattern không phải lúc nào cũng cần.
 *
 * #### **🔹 Cách thay thế bằng `copy()`**
 * ```kotlin
 * data class User(
 *     val firstName: String = "",
 *     val lastName: String = "",
 *     val age: Int = 0
 * )
 *
 * val user = User(firstName = "John", lastName = "Doe", age = 30)
 * val updatedUser = user.copy(age = 35) // Dễ dàng thay đổi dữ liệu
 * ```
 * 📌 Nếu dùng `data class`, chỉ nên dùng Builder khi có logic khởi tạo phức tạp.
 *
 * ---
 *
 * ### 🚀 **Tóm lại**
 * - **Builder Pattern** hữu ích khi class có nhiều tham số và bạn muốn **dễ đọc, dễ mở rộng**.
 * - Kotlin hỗ trợ **default values, named parameters, và `copy()`**, nên không phải lúc nào cũng cần Builder.
 * - Dùng Builder khi có **logic khởi tạo phức tạp** hoặc cần **tính linh hoạt cao**.
 */


class User private constructor(
     val firstName: String?,
     val lastName: String?,
     val age: Int?
) {
    override fun toString(): String {
        return "User: firstName = $firstName, lastName = $lastName, age = $age"
    }

    class UserBuilder {
         private var firstName: String? = null
         private var lastName: String? = null
         private var age: Int? = null

        fun setFirstName(firstName: String) = apply { this.firstName = firstName }
        fun setLastName(lastName: String) = apply { this.lastName = lastName }
        fun setAge(age: Int) = apply { this.age = age }

        fun build() =
             User(
                firstName,
                lastName,
                age
            )
    }
}

fun main() {
    val user = User.UserBuilder()
        .setFirstName("Thiep")
        .setAge(20)
        .setLastName("Ngo")
        .build().toString()
    println(user)
}