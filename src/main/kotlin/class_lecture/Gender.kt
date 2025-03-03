package class_lecture

import class_lecture.Gender.FEMALE
import class_lecture.Gender.MALE
import class_lecture.Gender.OTHER

enum class Gender(val initValue: Int) {
    MALE(initValue = 0),
    FEMALE(initValue = 1),
    OTHER(initValue = 2),

    ;

    val property = "Gender $this"

    val isMale: Boolean
        get() = this == MALE

    val isFemale: Boolean
        get() = this == FEMALE


    companion object {
        fun fromInt(value: Int): Gender? = Gender.entries.find {
            it.initValue == value
        }
    }


}


fun determineGender(): Gender {
    return Gender.MALE
}

fun main() {
    val allGenders = Gender.entries // list
    println(allGenders)


    println(Gender.fromInt(0))
    println(Gender.fromInt(1))
    println(Gender.fromInt(1000000))

    println("----")

    println("property is ${determineGender().property}")
    println("isMale is ${determineGender().isMale}")
    println("isFemale is ${determineGender().isFemale}")
}