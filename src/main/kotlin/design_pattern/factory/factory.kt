package design_pattern.factory

/**
 * ## 🏭 **Factory Pattern trong Kotlin**
 *
 * ### 📌 **1. Factory Pattern là gì?**
 * **Factory Pattern** là một **Creational Design Pattern**, giúp **tạo đối tượng mà không cần chỉ định chính xác lớp cụ thể**.
 *
 * ✅ **Dùng khi:**
 * - **Ẩn chi tiết khởi tạo** của đối tượng.
 * - **Dễ mở rộng**, chỉ cần thêm lớp mới mà không sửa đổi code cũ.
 * - **Tạo nhiều loại đối tượng từ cùng một logic chung**.
 *
 * ---
 *
 * ### 📌 **2. Cách triển khai Factory Pattern trong Kotlin**
 *
 * #### 🔹 **Cách 1: Factory Method (Hàm tĩnh)**
 * Dùng **hàm tĩnh** (`companion object`) để tạo đối tượng.
 *
 * ```kotlin
 * interface Shape {
 *     fun draw()
 * }
 *
 * class Circle : Shape {
 *     override fun draw() = println("Drawing a Circle")
 * }
 *
 * class Square : Shape {
 *     override fun draw() = println("Drawing a Square")
 * }
 *
 * class ShapeFactory {
 *     companion object {
 *         fun createShape(type: String): Shape {
 *             return when (type) {
 *                 "Circle" -> Circle()
 *                 "Square" -> Square()
 *                 else -> throw IllegalArgumentException("Unknown shape type")
 *             }
 *         }
 *     }
 * }
 *
 * fun main() {
 *     val shape1 = ShapeFactory.createShape("Circle")
 *     shape1.draw() // Drawing a Circle
 *
 *     val shape2 = ShapeFactory.createShape("Square")
 *     shape2.draw() // Drawing a Square
 * }
 * ```
 * ✅ **Dễ đọc, dễ dùng**.
 * ✅ **Ẩn logic khởi tạo**, chỉ cần gọi `ShapeFactory.createShape("Circle")`.
 * ❌ **Mở rộng khó hơn**, phải sửa `ShapeFactory` nếu thêm lớp mới.
 *
 * ---
 *
 * #### 🔹 **Cách 2: Factory với `object` (Singleton)**
 * Dùng `object` để **đảm bảo Factory chỉ có một instance duy nhất**.
 *
 * ```kotlin
 * object ShapeFactory {
 *     fun createShape(type: String): Shape = when (type) {
 *         "Circle" -> Circle()
 *         "Square" -> Square()
 *         else -> throw IllegalArgumentException("Unknown shape type")
 *     }
 * }
 * ```
 * ✅ **Không cần khởi tạo Factory**, chỉ gọi `ShapeFactory.createShape(...)`.
 * ✅ **Giảm thiểu bộ nhớ** vì `object` là Singleton.
 *
 * ---
 *
 * #### 🔹 **Cách 3: Factory Pattern với Enum (Dễ mở rộng)**
 * Dùng `enum` để quản lý các loại đối tượng.
 *
 * ```kotlin
 * enum class ShapeType { CIRCLE, SQUARE }
 *
 * class ShapeFactory {
 *     companion object {
 *         fun createShape(type: ShapeType): Shape {
 *             return when (type) {
 *                 ShapeType.CIRCLE -> Circle()
 *                 ShapeType.SQUARE -> Square()
 *             }
 *         }
 *     }
 * }
 *
 * fun main() {
 *     val shape = ShapeFactory.createShape(ShapeType.CIRCLE)
 *     shape.draw() // Drawing a Circle
 * }
 * ```
 * ✅ **Dễ mở rộng**, chỉ cần thêm Enum mà không sửa `ShapeFactory`.
 * ✅ **Giảm lỗi**, tránh dùng `String` dễ nhập sai.
 *
 * ---
 *
 * ### 📌 **3. Khi nào dùng Factory Pattern?**
 * | **Trường hợp** | **Có nên dùng Factory Pattern?** |
 * |--------------|-------------------------|
 * | Cần **ẩn chi tiết khởi tạo** | ✅ Nên dùng |
 * | Cần **tạo nhiều loại đối tượng khác nhau** | ✅ Nên dùng |
 * | Đối tượng phức tạp | ✅ Nên dùng |
 * | Chỉ có 1 hoặc 2 loại đối tượng | ❌ Không cần, dùng constructor trực tiếp |
 *
 * ---
 *
 * ### 🚀 **Tóm lại**
 * - **Factory Pattern giúp tạo đối tượng mà không cần biết chính xác class nào được tạo**.
 * - **Dùng `companion object` hoặc `object` để quản lý Factory**.
 * - **Dùng `enum` nếu có nhiều loại đối tượng, dễ mở rộng**.
 * - **Hữu ích khi cần tạo đối tượng động, giảm phụ thuộc vào constructor**. 🚀
 */

sealed interface Shape {
    fun draw()
}

object ShapeFactory {
    @JvmStatic
    fun getShape(shapeType: String): Shape {
        return when (shapeType) {
            "CIRCLE" -> Circle()
            "RECTANGLE" -> Rectangle()
            "SQUARE" -> Square()
            else -> throw IllegalArgumentException("Unknown shape type")
        }
    }
}

fun Shape(shapeType: String) = ShapeFactory.getShape(shapeType)

private class Circle: Shape {
    override fun draw() {
        println("Circle::draw()")
    }
}

private class Rectangle: Shape {
    override fun draw() {
        println("Rectangle::draw()")
    }
}

private class Square: Shape {
    override fun draw() {
        println("Square::draw()")
    }
}

