package com.advertex

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import io.vertx.core.Verticle
import io.vertx.core.spi.VerticleFactory
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component


@Component
class SpringVerticleFactory : VerticleFactory, ApplicationContextAware {

    private var applicationContext: ApplicationContext? = null

    override fun blockingCreate(): Boolean {
        // Usually verticle instantiation is fast but since our verticles are Spring Beans,
        // they might depend on other beans/resources which are slow to build/lookup.
        return true
    }

    override fun prefix(): String {
        // Just an arbitrary string which must uniquely identify the verticle factory
        return "myapp"
    }

    @Throws(Exception::class)
    override fun createVerticle(verticleName: String, classLoader: ClassLoader): Verticle? {
        // Our convention in this example is to give the class name as verticle name
        val clazz = VerticleFactory.removePrefix(verticleName)
        return applicationContext!!.getBean(Class.forName(clazz)) as? Verticle
    }

    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }
}