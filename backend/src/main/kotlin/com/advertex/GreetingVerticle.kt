package com.advertex

import org.springframework.beans.factory.annotation.Autowired
import io.vertx.core.logging.LoggerFactory
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component


@Component
    // Prototype scope is needed as multiple instances of this verticle will be deployed
    @Scope(SCOPE_PROTOTYPE)
class GreetingVerticle : AbstractVerticle() {

        @Autowired
        internal var greeter: Greeter? = null

        @Throws(Exception::class)
        override fun start(startFuture: Future<Void>) {
            vertx.createHttpServer().requestHandler { request ->
                val name = request.getParam("name")
                LOG.info("Got request for name: " + name)
                if (name == null) {
                    request.response().setStatusCode(400).end("Missing name")
                } else {
                    // It's fine to call the greeter from the event loop as it's not blocking
                    request.response().end(greeter!!.sayHello(name))
                }
            }.listen(8080) { ar ->
                if (ar.succeeded()) {
                    LOG.info("GreetingVerticle started: @" + this.hashCode())
                    startFuture.complete()
                } else {
                    startFuture.fail(ar.cause())
                }
            }
        }

        companion object {
            private val LOG = LoggerFactory.getLogger(GreetingVerticle::class.java)
        }
    }