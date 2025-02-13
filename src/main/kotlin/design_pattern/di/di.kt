package design_pattern.di

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

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