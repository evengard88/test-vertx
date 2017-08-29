package com.advertex

import org.springframework.stereotype.Component

@Component
class Greeter {

    fun sayHello(name: String): String {
        return "Hello " + name
    }
}