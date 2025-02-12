package design_pattern.builder

/**
 * ## 🎭 **Prototype Pattern trong Kotlin**
 *
 * ### 📌 **1. Prototype Pattern là gì?**
 * **Prototype Pattern** là một **Creational Design Pattern**, cho phép **sao chép** (clone) một đối tượng **thay vì tạo mới** từ đầu.
 *
 * ✅ **Dùng khi:**
 * - Tạo đối tượng mới **tốn kém tài nguyên** hoặc **phức tạp**.
 * - Muốn giữ **cấu trúc & trạng thái** của đối tượng gốc.
 * - Dễ **tùy chỉnh đối tượng mới** dựa trên một mẫu có sẵn.
 *
 * ---
 *
 * ### 📌 **2. Cách triển khai Prototype Pattern trong Kotlin**
 *
 * #### 🔹 **Cách 1: Dùng `copy()` với `data class`** (Kotlin-style)
 * ```kotlin
 * data class Person(val name: String, val age: Int) {
 *     fun clone() = this.copy() // Dùng copy() để tạo bản sao
 * }
 *
 * fun main() {
 *     val original = Person("John", 30)
 *     val clone = original.clone().copy(age = 25) // Tạo bản sao và chỉnh sửa
 *
 *     println(original) // Person(name=John, age=30)
 *     println(clone)    // Person(name=John, age=25)
 * }
 * ```
 * ✅ **Nhanh & đơn giản** khi dùng `data class`
 * ❌ Không hỗ trợ **sao chép sâu (deep copy)** nếu có danh sách hoặc object bên trong.
 *
 * ---
 *
 * #### 🔹 **Cách 2: Dùng `Cloneable` Interface (Deep Copy)**
 * Nếu có danh sách hoặc đối tượng con bên trong, cần **sao chép sâu (deep copy)** để tránh lỗi **tham chiếu cùng bộ nhớ**.
 *
 * ```kotlin
 * class Address(var city: String) {
 *     fun clone() = Address(city) // Tạo bản sao thủ công
 * }
 *
 * class Person(val name: String, val age: Int, val address: Address) : Cloneable {
 *     public override fun clone(): Person {
 *         return Person(name, age, address.clone()) // Deep Copy
 *     }
 * }
 *
 * fun main() {
 *     val original = Person("John", 30, Address("New York"))
 *     val clone = original.clone()
 *     clone.address.city = "Los Angeles" // Chỉnh sửa chỉ clone
 *
 *     println(original.address.city) // New York ✅ Không bị ảnh hưởng
 *     println(clone.address.city)    // Los Angeles
 * }
 * ```
 * ✅ **Sao chép sâu (deep copy)**, không ảnh hưởng đến đối tượng gốc.
 * ❌ Cần tự triển khai `clone()`, **tốn công hơn**.
 *
 * ---
 *
 * ### 📌 **3. Khi nào dùng Prototype Pattern?**
 * | **Trường hợp** | **Có nên dùng Prototype Pattern?** |
 * |--------------|-------------------------|
 * | Tạo đối tượng tốn kém | ✅ Nên dùng |
 * | Cần sao chép nhanh | ✅ Nên dùng |
 * | Đối tượng có danh sách phức tạp | ⚠️ Cần sao chép sâu |
 * | Chỉ có vài tham số đơn giản | ❌ Không cần, dùng constructor hoặc `copy()` |
 *
 * ---
 *
 * ### 🚀 **Tóm lại**
 * - **Prototype Pattern giúp tạo bản sao của đối tượng**, tránh phải tạo mới từ đầu.
 * - **Dùng `copy()` nếu chỉ có dữ liệu đơn giản** (Kotlin-style).
 * - **Dùng `Cloneable` nếu có danh sách hoặc object bên trong** (Deep Copy).
 * - **Hữu ích khi tạo đối tượng tốn kém tài nguyên hoặc có cấu trúc phức tạp**. 🚀
 */

fun main() {
//    val user1 = User.UserBuilder()
//        .setAge(10)
//        .setLastName("Ngo")
//        .setFirstName("Thiep")
//        .build()
//
//    val user2 = User.UserBuilder()
//        .setAge(user1.age!!)
//        .setLastName(user1.lastName!!)
//        .setFirstName(user1.firstName!!)
//        .build()
//
//    println(user1)
//    println(user2)
//
//    val student = Student(1, "A", Address("Hồ Chí Minh"))
//    val student2 = student.clone()
//
//    println(student)
//    println(student2)
//
//    println("-".repeat(8))
//
//    student.id = 10
//    student.address.city = "Hà Nội"
//    println(student)
//    println(student2)

//    val person = Person2("A", 20)
//    val person1 = Person(person)

//    val person2 = person1.copy(age = 30)
//
//    println(person1)
//    println(person)
//
//    println(person2)
    val person = Person2("A", 20, City("Ha Noi"))

    val personNormal = person.copy(city = person.city.copy(name = "Ho Chi Minh"))

    println(person)
    println(personNormal)
}


data class Person2(
    val name: String,
    val age: Int,
    val city: City
)

data class City(
    val name: String
)

class Person(
    val name: String,
    val age: Int
) {
    constructor(other: Person) : this(other.name, other.age)

    override fun toString(): String {
        return "Person(name=$name, age=$age)"
    }

    fun copy(name: String = this.name, age: Int = this.age): Person {
        return Person(name, age)
    }
}

class Address(
    var city: String
): Cloneable {
    public override fun clone(): Address {
        val address = super.clone() as Address
        return address
    }

    override fun toString(): String {
        return "Address(city=$city)"
    }
}


class Student(
    var id: Int,
    var name: String,
    var address: Address,
): Cloneable {
    public override fun clone(): Student {
        val student = super.clone() as Student
        student.address = address.clone()
        return student
    }
    override fun toString(): String {
        return "Student(id=$id, name=$name, city=${address.city})"
    }
}