package class_lecture

import collections.Student

data class Address(var city: String)

data class Person(var name: String, var address: Address)

fun main() {
    val person = Person("John", Address(city = "HCM"))

    val person1 = person.copy()

    person1.address.city = "HN"

    println(person1)
    println(person)

}