package design_pattern.abstract_factory

/**
 * Trong Kotlin, vararg là một từ khóa được sử dụng để khai báo tham số có thể nhận nhiều giá trị của một kiểu dữ liệu cụ thể, thay vì chỉ nhận một giá trị duy nhất.
 *
 * 📌 Cách thức hoạt động của vararg:
 * Tham số khai báo với vararg có thể chứa một hoặc nhiều đối tượng.
 * Khi sử dụng vararg, bạn có thể truyền vào nhiều giá trị dưới dạng một mảng, hoặc truyền trực tiếp các giá trị riêng biệt mà không cần phải đóng gói chúng trong một mảng.
 */
fun consumer(vararg factories: ShapeFactory) {
    factories.map { it.create() }.forEach{it.draw()}
}

fun main() {

    consumer(Circle, Rectangle, Square)
}