package collections
class Person1
    (val name : String, val age: Int) {
    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + age
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person1

        if (name != other.name) return false
        if (age != other.age) return false

        return true
    }
}


fun main() {

    val pair = Pair<Int, String>(1, "one")

    // mapOf -> giữ thứ tự
    val mapOf = mapOf(
        pair,
        2 to "two",
        3 to "three",
        120 to "four",
        2200 to "hive",
        6 to "three"
    )

    // hashmap -> không giữ thứ tự
    val hashMap = hashMapOf(
        pair,
        2 to "two",
        3 to "three",
        120 to "four",
        2200 to "hive",
        4 to "four",
    )

//    println(hashMap[2])

//    mapOf.forEach{(key, value) -> println("key: $key, value: $value") }

//    val treeMap = sortedMapOf(
//        pair,
//        2 to "two",
//        3 to "three",
//        120 to "four",
//        2200 to "hive",
//        6 to "three"
//    )

    val treeMap = sortedMapOf<Person1, Int>(compareBy{it.age})

    treeMap.forEach{(key, value) -> println("key: $key, value: $value") }

    val peopleHashMap = hashMapOf<Person1, Int>()
    val people1 = Person1("Ivan", 25)
    val people2 = Person1("Ivan", 25)



    peopleHashMap[people1] = 1
    peopleHashMap[people2] = 2


    println("size: ${peopleHashMap.size}")
}