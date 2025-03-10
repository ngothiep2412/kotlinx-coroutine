package lesson

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*

@JvmInline
value class UserId(val id: Int)

data class User(
    val id: UserId,
    val name: String,
)

data class UserDetails(
    val id: UserId,
    val email: String,
    val phone: String,
)

data class Post(
    val id: String,
    val title: String,
    val body: String,
    val userId: UserId,
)

data class UserAndPosts(
    val user: User,
    val posts: List<Post>,
)

data class UserAndDetails(
    val user: User,
    val details: UserDetails,
)

interface UserRepository {
    /**
     * Find a user by id.
     * @return the user if found, null otherwise.
     */
    suspend fun findUserById(id: UserId): User?

    /**
     * Find all posts by user id.
     * @return the list of posts if found, empty list otherwise.
     */
    suspend fun getPostsByUserId(id: UserId): List<Post>

    /**
     * Find a user by id and all posts by that user.
     * @return the user and all posts if found, null otherwise.
     */
    suspend fun findUserAndPostsById(id: UserId): UserAndPosts?

    /**
     * Find a user by id and all details by that user.
     * @return the user and all details if found, null otherwise.
     */
    suspend fun findUserAndUserDetailsById(id: UserId): UserAndDetails?
}

interface UserApi {
    suspend fun findUserById(id: UserId): User?
    suspend fun findDetailsByUser(id: UserId): UserDetails?
    suspend fun getPostsByUser(user: User): List<Post>
}

// ------------------------------------------------------------------------------------------

internal class RealUserRepository(
    private val userApi: UserApi,
    private val ioDispatcher: CoroutineDispatcher,
) : UserRepository {
    override suspend fun findUserById(id: UserId): User? {
        // Call userApi's methods on ioDispatcher
        return withContext(Dispatchers.IO) {
            userApi.findUserById(id)
        }
    }

    override suspend fun getPostsByUserId(id: UserId): List<Post> =
        // Call userApi's methods on ioDispatcher
        withContext(Dispatchers.IO) {
            userApi.findUserById(id)?.let {
                userApi.getPostsByUser(it)
            } ?: emptyList()
        }


    override suspend fun findUserAndPostsById(id: UserId): UserAndPosts? =
        // Call userApi's methods on ioDispatcher
        withContext(Dispatchers.IO) {
            val user = userApi.findUserById(id)
            val posts = getPostsByUserId(id)
            user?.let {
                UserAndPosts(user, posts)
            }
        }

    override suspend fun findUserAndUserDetailsById(id: UserId): UserAndDetails? =
        // Call concurrently userApi's methods on ioDispatcher
        withContext(Dispatchers.IO) {
            val userDeferred = async {
                delay(1000L)
                userApi.findUserById(id)
            }

            val userDetailDeferred = async {
                delay(2000L)
                userApi.findDetailsByUser(id)
            }

            // Use awaitAll to wait for both deferred results concurrently
            val result = awaitAll(userDeferred, userDetailDeferred)
            val user = result[0] as User?
            val userDetail = result[1] as UserDetails?

            // Return UserAndDetails if both user and userDetail are not null
            user?.let {
                userDetail?.let {
                    UserAndDetails(user, userDetail)
                }
            }
        }
}


// ------------------------------------------------------------------------------------------

fun provideUserRepository(): UserRepository =
    RealUserRepository(
        userApi = object : UserApi {
            val users = listOf(
                User(UserId(1), "Leanne Graham"),
                User(UserId(2), "Ervin Howell"),
                User(UserId(3), "Clementine Bauch"),
                User(UserId(4), "Patricia Lebsack"),
                User(UserId(5), "Chelsey Dietrich"),
            )
            val userDetails = users.map {
                UserDetails(
                    id = it.id,
                    email = "${it.name.replace(" ", ".").lowercase()}@gmail.com",
                    phone = "+1-770-736-8031",
                )
            }
            val posts = users.map { user ->
                List(10) {
                    Post(
                        id = UUID.randomUUID().toString(),
                        title = "Title #${it} of ${user.name}",
                        body = "Body #${it} of ${user.name}",
                        userId = user.id,
                    )
                }
            }

            override suspend fun findUserById(id: UserId): User? = users
                .find { it.id == id }
                .also {
                    delay(500)
                    println(it)
                }

            override suspend fun findDetailsByUser(id: UserId): UserDetails? =
                userDetails
                    .find { it.id == id }
                    .also { delay(500) }

            override suspend fun getPostsByUser(user: User): List<Post> =
                posts
                    .find { it.firstOrNull()?.userId == user.id }
                    .orEmpty()
                    .also { delay(500) }
        },
        ioDispatcher = Dispatchers.IO,
    )


fun main() = runBlocking {
    val userRepository = provideUserRepository()

    println("findUserById")
    userRepository.findUserById(UserId(1))
    userRepository.findUserById(UserId(100))
    println("-".repeat(80))

    println("getPostsByUserId")
    userRepository.getPostsByUserId(UserId(1))
    userRepository.getPostsByUserId(UserId(100))
    println("-".repeat(80))

    println("findUserAndPostsById")
    userRepository.findUserAndPostsById(UserId(1))
    userRepository.findUserAndPostsById(UserId(100))
    println("-".repeat(80))

    println("findUserAndUserDetailsById")
    userRepository.findUserAndUserDetailsById(UserId(1))
    userRepository.findUserAndUserDetailsById(UserId(100))
    println("-".repeat(80))
}