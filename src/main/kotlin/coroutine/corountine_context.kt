package org.example.coroutine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@OptIn(ExperimentalStdlibApi::class)
suspend fun printCurrentCoroutineContext() {
    val context = currentCoroutineContext()
    println("Current context is $context")

    val dispatcher = context[CoroutineDispatcher]
    println("Current dispatcher is $dispatcher")

    val count = context.fold(initial = 0) {acc, element ->
        println(" >>> Element: $element")
        acc + 1
    }
    println("Context elements count: $count")

    val withoutDispatcher = context.minusKey(CoroutineDispatcher)
    println("Context without dispatcher: $withoutDispatcher")

    EmptyCoroutineContext // ~ emptyMap()
    println("context + EmptyCoroutineContext: ${context + EmptyCoroutineContext}")
    println("EmptyCoroutineContext + context: ${ EmptyCoroutineContext + context}")
}

// Element == Singleton Context
class MyDemoCoroutineContext (
    private val name: String
) : AbstractCoroutineContextElement(key = MyDemoCoroutineContext) {
    companion object Key: CoroutineContext.Key<MyDemoCoroutineContext>

    override fun toString(): String {
        return "MyDemoCoroutineContext(name='$name')"
    }
}

suspend fun printDemo() {
    val ctx = currentCoroutineContext()
    val myDemo = ctx[MyDemoCoroutineContext]
    println(">>> ctx is $ctx")
    println(">>> myDemo is $myDemo")
    println("-".repeat(80))
}


fun main(): Unit = runBlocking{
    printDemo()

    // withContext: currentContext + new context arg => "new context"
    withContext(MyDemoCoroutineContext(name = "Outer 1")) {
        printDemo()
        withContext(MyDemoCoroutineContext(name = "Inner 1")) {
            printDemo()
            withContext(MyDemoCoroutineContext(name = "Inner 2")) {
                printDemo()
            }
        }
    }
}