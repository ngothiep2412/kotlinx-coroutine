package delegation

import kotlin.reflect.KProperty

private class DemoClass {
    val fieldProperty = System.currentTimeMillis()
    val getterProperty get() = System.currentTimeMillis()

    val delegatedGetter: String by DelegatedGetter()

}


private class DelegatedGetter {
     var count = 0

    operator fun getValue(demoClass: DemoClass, property: KProperty<*>): String {
        println(">>> getValue is called for property: ${property.name} and demoClass: $demoClass")
        return "Hello World! ${count++}"
    }
}

fun main() {
    val demoClass = DemoClass()
    repeat(3) {
        println("Access delegateGetter: ${demoClass.delegatedGetter}")
    }
}