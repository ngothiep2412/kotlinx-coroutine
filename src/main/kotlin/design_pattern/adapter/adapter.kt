package design_pattern.adapter

import design_pattern.observer.Subject
import kotlinx.coroutines.*

// Entity
data class User(
    val id: Int,
    val name: String,
)


// Repository: TARGET
interface UserRepository {
    // REQUEST method
    suspend fun login(name: String, password: String): User
}

// ViewModel: CLIENT -> TARGET (CLIENT uses TARGET)
class AuthViewModel(
    private val userRepository: UserRepository,
) {
    private val viewModelScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun login(name: String, password: String) {
        viewModelScope.launch {
            try {
                userRepository.login(name = name, password = password)
                println("AuthViewModel::login() success ")
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                println("AuthViewModel::login() failed $e")
            }
        }
    }
}


// ADAPTEE (3rd party library): Retrofit, Room DB, Local files, ...
class UserApiService {
    // @POST("login)
    // SPECIFIC REQUEST method
    suspend fun login(name: String, password: String): User {
        println("UserApiService::login($name, $password) ")
        delay(2_000)
        return User(id = 1, name = name)
    }
}

// ADAPTER: repository implementation
class RealUserRepository(
    // Inject ADAPTEE
    private val userApiService: UserApiService,
): UserRepository {
    override suspend fun login(name: String, password: String): User {
        // ...
        // adapt `userAPiService.login` to `UserRepository.login`
       return userApiService.login(name, password)
    }
}

// ------
fun main() = runBlocking {
    val adaptee =  UserApiService()
    val adapter = RealUserRepository(userApiService = adaptee)
    val target = adapter

    val client = AuthViewModel(userRepository = target)
    client.login(name = "John", password = "123")
    delay(5_000)

}