package lesson

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


// extend func
fun String.addExclamation():String {
    return "$this!"
}

val String.lastChar: Char
    get() =
        this[this.length - 1]


var StringBuilder.firstChar: Char
    get() = this[0]
    set(value) {
        this.setCharAt(0, value)
    }


fun main() = runBlocking{
//    val text = "Hello".addExclamation()
//    println(text.lastChar)
//
//    val sb = StringBuilder("Hello")
//    println(sb.firstChar)
//    sb.firstChar = 'Y'
//    println(sb)

    val scope = CoroutineScope(Dispatchers.Default)
    runBlocking {
        scope.launch {

                println("Thread running - ${currentCoroutineContext().javaClass}")

        }
        println("Thread running - ${currentCoroutineContext().javaClass}")
    }
    Unit
}