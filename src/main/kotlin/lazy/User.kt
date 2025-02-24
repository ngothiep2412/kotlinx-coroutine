package lazy

import kotlin.properties.Delegates

class User {
//    var name: String by Delegates.observable(initialValue = "<no name>") {prop, old, new ->
//        println("$old -> $new")
//    }

//    var address: String by Delegates.notNull() // -> initialized before get
//    lateinit var address2: String

    var age: Int by Delegates.vetoable(0) {prop, old, new ->
        println("Will change; $old -> $new")
        // false -> no change
        // true -> change
        new >= 0
    }
}

private fun returnNameAndAge() = "RxMobile" to 100

fun main() {
    val user = User()
//    user.address = "RxMobile"

    println(user.age)
    user.age = 100

    println("[2] age is ${user.age}")
    user.age = -50

    println("[3] age is ${user.age}")

    println(returnNameAndAge())
}