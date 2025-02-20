package collections

import java.util.concurrent.CopyOnWriteArrayList
import kotlin.concurrent.thread

data class Person(
    val name: String,
    val age: Int
)

fun main() {
//    val numbers = mutableListOf("one", "two", "three")
//
//    numbers.add("four")
//
//    numbers.remove("three")
//
//    numbers.remove("two")
//    numbers.forEach(::println)
//
//    for ((index, number) in numbers.withIndex()) {
//        println("index: $index, number: $number")
//    }
//
//
//    val list = listOf("1, 2, 3, 4, 5")
//
//
//    val list1 = buildList<Int> {
//        add(1)
//        add(2)
//        add(3)
//    }
//
//
//    val person1 = Person("John", 29)
//    val person2 = Person("Rick", 30)
//    val people = mutableListOf(person1, person2)
//
//    people.add(person2)
//    people.add(person1)
//
//    val people2: List<Person> = listOf(person2, person1)
//
//    val mutableList = mutableListOf(1, 2, 3, 4)
//    val readOnlyList: List<Int> = mutableList.toList() // -> tạo ra list mới
//
//    println(mutableList === readOnlyList)
//    println("before changed $readOnlyList")
//    mutableList.add(5)
//
//    println("after changed $readOnlyList")

    val person1 = Person("Alice1", 10)
    val person2 = Person("Alice2", 29)
    val person3 = Person("RxAndroid", 30)

//    val peoples = listOf(person1, person2)
//    val filterPeople = peoples.filter { it.age > 25 }
//
//    println(filterPeople == peoples)
//
//    val arrayList = CopyOnWriteArrayList<Int>()
//    val threads = (1..10).map {
//        thread {
//            for (i in 1..1000) {
//                arrayList.add(i)
//            }
//        }
//    }
//    threads.forEach{it.join()}
//
//    println(arrayList.size)

    val people = listOf(person1, person2, person3)

    val newList = people.map {
        if (it.name == person3.name) {
            it.copy(name = "RxJava")
        } else
            it
    }

    println(newList[2].hashCode() === person3.hashCode())
}