package design_pattern.di

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * ### 🚀 **Dependency Injection (DI) trong Kotlin là gì?**
 *
 * **Dependency Injection (DI)** là một kỹ thuật trong lập trình giúp **quản lý phụ thuộc giữa các thành phần trong ứng dụng** một cách hiệu quả, giúp code **dễ bảo trì, dễ kiểm thử và linh hoạt hơn**.
 *
 * Thay vì một class **tự tạo ra dependencies** của nó, **dependencies được cung cấp từ bên ngoài**.
 *
 * ---
 *
 * ## 🔥 **1. Vì sao cần Dependency Injection?**
 * Không dùng DI sẽ dẫn đến **coupling cao** (phụ thuộc chặt), làm code khó mở rộng và kiểm thử.
 *
 * Ví dụ **không dùng DI**:
 * ```kotlin
 * class Engine {
 *     fun start() = println("Engine started")
 * }
 *
 * class Car {
 *     private val engine = Engine() // ❌ Phụ thuộc trực tiếp vào Engine
 *
 *     fun drive() {
 *         engine.start()
 *         println("Car is moving")
 *     }
 * }
 * ```
 * 🔴 **Vấn đề:**
 * - `Car` **tạo ra** `Engine`, nghĩa là nó phụ thuộc chặt vào `Engine`.
 * - Nếu muốn thay đổi `Engine` (ví dụ: đổi sang `ElectricEngine`), ta phải sửa code trong `Car`.
 *
 * ---
 *
 * ## ✅ **2. Giải quyết với Dependency Injection**
 *
 * ### 🔹 **Cách 1: Constructor Injection (Khuyến nghị)**
 * ```kotlin
 * class Engine {
 *     fun start() = println("Engine started")
 * }
 *
 * class Car(private val engine: Engine) { // ✅ Inject từ bên ngoài
 *     fun drive() {
 *         engine.start()
 *         println("Car is moving")
 *     }
 * }
 * ```
 * **Sử dụng DI:**
 * ```kotlin
 * val engine = Engine()
 * val car = Car(engine) // Inject Engine từ bên ngoài
 * car.drive()
 * ```
 * ✔ **Lợi ích:**
 * - `Car` không cần biết **cách tạo** `Engine`, chỉ cần sử dụng nó.
 * - Dễ dàng thay đổi **Engine** mà không cần sửa code của `Car`.
 *
 * ---
 *
 * ### 🔹 **Cách 2: Interface + DI (Dễ mở rộng hơn)**
 * Giả sử có **nhiều loại Engine** (`PetrolEngine`, `ElectricEngine`), ta dùng **interface**:
 * ```kotlin
 * interface Engine {
 *     fun start()
 * }
 *
 * class PetrolEngine : Engine {
 *     override fun start() = println("Petrol Engine started")
 * }
 *
 * class ElectricEngine : Engine {
 *     override fun start() = println("Electric Engine started")
 * }
 *
 * class Car(private val engine: Engine) { // Inject Engine từ bên ngoài
 *     fun drive() {
 *         engine.start()
 *         println("Car is moving")
 *     }
 * }
 *
 * // Inject dependency từ bên ngoài
 * val petrolCar = Car(PetrolEngine())
 * val electricCar = Car(ElectricEngine())
 *
 * petrolCar.drive()
 * electricCar.drive()
 * ```
 * ✔ **Dễ mở rộng:** Chỉ cần tạo class `HybridEngine`, không cần sửa `Car`.
 *
 * ---
 *
 * ## 🛠 **3. Dùng DI Framework trong Kotlin (Koin, Dagger)**
 * Nếu ứng dụng lớn, nên dùng **DI framework** như **Koin hoặc Dagger**.
 *
 * ### 🔹 **Dùng Koin để Inject Dependency**
 * 1 **Thêm Koin vào `build.gradle.kts`**
 * ```kotlin
 * dependencies {
 *     implementation("io.insert-koin:koin-core:3.4.0")
 *     implementation("io.insert-koin:koin-android:3.4.0")
 * }
 * ```
 * 2 **Định nghĩa module DI**
 * ```kotlin
 * import org.koin.dsl.module
 *
 * val appModule = module {
 *     single<Engine> { PetrolEngine() } // Provide PetrolEngine khi cần Engine
 *     factory { Car(get()) } // Inject Engine vào Car
 * }
 * ```
 * 3 **Khởi tạo Koin**
 * ```kotlin
 * import org.koin.core.context.startKoin
 *
 * fun main() {
 *     startKoin {
 *         modules(appModule)
 *     }
 *
 *     val car: Car = getKoin().get() // Lấy Car từ DI container
 *     car.drive()
 * }
 * ```
 * ✔ **Lợi ích của Koin:**
 * - **Tự động inject** dependencies.
 * - **Giảm boilerplate code**, không cần tạo thủ công.
 * - **Dễ dàng kiểm thử** bằng cách thay đổi module.
 *
 * ---
 *
 * ## 🎯 **Khi nào nên dùng Dependency Injection?**
 * ✅ Khi ứng dụng **có nhiều class phụ thuộc lẫn nhau**.
 * ✅ Khi muốn **dễ kiểm thử**, tách biệt dependencies.
 * ✅ Khi làm việc với framework lớn như **Android, Spring Boot**.
 *
 * Nếu chỉ là dự án nhỏ, **Constructor Injection** là đủ, không cần framework.
 *
 * Bạn có dùng DI trong dự án của mình chưa? 🚀
 */

// -- HIGHER MODULE (domain) --


// Entity (Business Object)
data class Person(
    val id: String,
    val name: String,
    val age: Int,
)

// UseCase: Business Logic
// Inject [PersonRepository] -> [GetPersonByIdUseCase]
// PersonRepository: dependency

class GetPersonByIdUseCase constructor(
    private val personRepository: PersonRepository,
) {
    // execute, invoke
    suspend operator fun invoke(id: String): Person {
        // get person from API/Local DB (Data/Infrastructure)
        return personRepository.getPersonById(id)
    }
}

// Abstractions => Repository (Gateway/Port)
interface PersonRepository {
    suspend fun getPersonById(id: String) : Person
}


// -- LOWER MODULE (data/ infrastructure) --

// Real -> Use in app
class RealPersonRepository: PersonRepository {
    override suspend fun getPersonById(id: String): Person {
        delay(1000)
        println("$this:getPersonById($id)")
        return Person(id, "John", 30)
    }
}

// Fake -> Use in unit tests
class FakePersonRepository(private val person: Person): PersonRepository {
    override suspend fun getPersonById(id: String): Person  = person
}

// -- MANUAL DI --
object DIContainer {
    fun provideGetPersonByIdUseCase(): GetPersonByIdUseCase {
        val personRepository = FakePersonRepository(
            Person(id = "1", name = "Thiep", age = 20)
        )

        // inject
        return GetPersonByIdUseCase(
            personRepository
        )
    }
}


// -- APP --
fun main() = runBlocking {
    val getPersonByIdUseCase = DIContainer.provideGetPersonByIdUseCase()
    val person = getPersonByIdUseCase("1")

    println(person)
}