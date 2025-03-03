package class_lecture

import kotlin.random.Random

private fun getFullName(firstName: String, lastName: String): String {
    println("getFullName called")
    return "$firstName $lastName"
}

private fun getNickName(): String {
    println("getNickName called")
    return "007"
}

class Person2
constructor(
    // read-only properties (backing fields)
    val firstName: String,
    val lastName: String,
    // variable properties (backing fields)
    var age: Int = 1,
    // constructor params
    address: String = "Earth",
) {
    val fullName: String = getFullName(firstName, lastName)

    init {
        println("$this::init block 1 called: fullName=$fullName")
    }

    val nickName = getNickName()

    init {
        println("$this::init block 2 called: fullName=$fullName and nickName=$nickName")
    }

    // secondary constructor
    constructor(fullName: String) : this(
        firstName = fullName.substringBefore(' '),
        lastName = fullName.substringAfter(' '),
        age = 50,
        address = "Mars",
    ) {
        println("$this::secondary constructor called: fullName=$fullName")
    }

    lateinit var email: String

    init {

    }

    // Nullable type
    // 'lateinit' modifier is not allowed on properties of nullable types
    // 'lateinit' modifier is not allowed on properties of primitive types

    constructor() : this("James Bond") {
        println("$this::secondary constructor 2 called")
    }

    val number1: Int = Random.nextInt() // read-only backing field (private final int)

    val number2: Int
        get() = Random.nextInt()  // read-only getter (public final int getNumber2()) (computed property)
    val number3: Int = -100
        get() = field + Random.nextInt()

    var varBackingField: Int = 0

    var varSetterGetter: Int = -1 // -1 is default value of field
        get() {
            println("get varSetterGetter called")
            return field * 100
        }
        set(value) {
            println("set varSetterGetter called with value=$value")
            if (value < 0) {
                throw IllegalArgumentException("value must be positive or zero")
            }
            field = value
        }
}


fun main() {
//    handleMessage()
    val person = Person2(firstName = "John", lastName = "Doe", age = 30)
    println(person.firstName)
    println(person.lastName)
    println(person.age)
    println(person.fullName)
    println(person.nickName)

    person.varSetterGetter = -10

    val middleware = object : Middleware {
        override fun intercept() {
            println("intercept called")
        }
    }
}

//fun handleMessage(): Nothing {
//    throw IllegalStateException()
//}


interface Middleware {
    fun intercept()
}


open class Parent(name: String) {
    open fun doSomething() {
        println("Parent::doSomething called")
    }
}

class Child(name: String) : Parent(name) {
    var number: String? = null
    var bool: Boolean = false

    override fun doSomething() {
        println("Child::doSomething called")
        super.doSomething()
    }
}