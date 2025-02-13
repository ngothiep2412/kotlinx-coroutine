package design_pattern.observer

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

interface Observer {
    fun update(message: String)
}

class Subject {
    private val observers = mutableListOf<Observer>()

    fun registerObserver(observer: Observer) {
        observers.add(observer)
    }

    fun unregisterObserver(observer: Observer) {
        observers.remove(observer)
    }

    fun notifyObserver(message: String) {
        observers.forEach{
            it.update(message)
        }
    }
}

class ConcreteObserver(private val name: String): Observer {
    override fun update(message: String) {
        println("$name received message: $message")
    }
}

//fun main() {
//    val subject = Subject()
//    val observer1 = ConcreteObserver("Observer 1")
//    val observer2 = ConcreteObserver("Observer 2")
//
//    // register observers
//    subject.registerObserver(observer1)
//    subject.registerObserver(observer2)
//
//    subject.notifyObserver("Hello World")
//}

fun main()  = runBlocking{
    val subject = Subject()
    val observer1 = ConcreteObserver("Observer 1")
    val observer2 = ConcreteObserver("Observer 2")

    // register observers
    subject.registerObserver(observer1)
    subject.registerObserver(observer2)

    launch {
        delay(2000)
        subject.unregisterObserver(observer1)
        subject.notifyObserver("Hello World")
    }

    subject.notifyObserver("Hello Kotlin")
}