package flow

import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

interface WebSocketCallback {
    fun receiveData(message: Int)
}

class ServiceTest {
    private var callback: WebSocketCallback? = null

    fun unRegister() {
        callback = null
        println("Unregistered")
    }

    fun register(callback: WebSocketCallback) {
        println("Registered")
        this.callback = callback
    }

    fun execute(value: Int) {
        callback?.receiveData(value)
    }
}


fun main() = runBlocking {
    launch {
        createCallbackFlowSocket()
            .cancellable()
            .collect{
                if (it == 3) {
                    cancel()
                }
                println("Collected $it")
            }
    }
    Unit
}

fun createCallbackFlowSocket() = callbackFlow {
    val serviceTest = ServiceTest()

    val callback = object : WebSocketCallback {
        override fun receiveData(message: Int) {
            trySend(message)
        }
    }

    serviceTest.register(callback)

    serviceTest.execute(1)
    serviceTest.execute(2)
    serviceTest.execute(3)


    awaitClose {
        serviceTest.unRegister()
    }
}