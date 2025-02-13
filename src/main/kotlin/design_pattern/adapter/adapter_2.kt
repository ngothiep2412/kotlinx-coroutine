package design_pattern.adapter

import kotlinx.coroutines.*

data class Student(
    val name: String,
    val age: Int,
)

interface StudentRepository {
    suspend fun login(name: String, password: String): Student
}

class AuthViewModel1(
    private val studentRepository: StudentRepository
) {
    private val viewModelScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun login(name: String, password: String) {
        viewModelScope.launch {
            try {
                studentRepository.login(name, password)
                println("Login success")
            } catch (cancel: CancellationException) {
                throw cancel
            } catch (e: Exception) {
                println("Login failed $e")
            }
        }
    }
}

class ApiService {
    // @POST("login")
    suspend fun login(name: String, password: String): Student {
        println("ApiService::login($name, $password) ")
        delay(2_000)
        return Student( name, 20)
    }
}

class RealStudentRepository(
    private val apiService: ApiService
): StudentRepository {
    override suspend fun login(name: String, password: String): Student {
        return apiService.login(name, password)
    }

}

fun main()  = runBlocking{
    val adaptee = ApiService()
    val adapter = RealStudentRepository(adaptee)
    val target = AuthViewModel1(adapter)
    target.login(name = "Thiep", password = "123")
    delay(2_000)
}

