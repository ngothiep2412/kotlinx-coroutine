package lazy

import kotlin.random.Random


fun main() {
    // LazyThreadMode
    // default is LazyThreadSafetyMode.SYNCHRONIZED
    // LazyThreadSafetyMode.SYNCHRONIZED -> chỉ xảy ra 1 lần: double checked locking singleton pattern
                                            // - thread-safe (in multi-threaded environment)
                                            // - initializer is called only once
    // LazyThreadSafetyMode.PUBLICATION -> compareAndSet
    //                                      thread-safe (in-multi-threaded environment)
                                            // - initializer is called more than once
    // LazyThreadSafetyMode.NONE -> not thread-safe (in-multi-threaded environment)
                                        // no synchronization
                                        // .value can be undefined
    // NOTE
    // single thread - NONE
    // android: main thread (single thread) -> NONE -> best performance
    // multi-threaded environment -> SYNCHRONIZED or PUBLICATION, don't use NONE

    val lazyValue: Lazy<String> = lazy {
        println("computed!")
        "Hello World ${Random.nextInt()}"
    }

    lazy(LazyThreadSafetyMode.SYNCHRONIZED) {"Hello"}
    println(lazyValue::class.java)

//    repeat(10) {
//        println(lazyValue.value)
//    }
}