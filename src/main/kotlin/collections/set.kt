package collections

fun main() {
    val hashset = hashSetOf(1,1,223,3,4,5,5)

    hashset.forEach(::println)

    val linkedSet  = linkedSetOf(1,2,3,4,4,5,5,3)
    linkedSet.forEach(::println)
}


data class Student(val name: String, val age: Int): Comparable<Student> {
    override fun compareTo(other: Student): Int {
        return if (this.age != other.age) other.age - this.age else this.name.compareTo(other.name)
    }
}