package com.advertex

import io.vertx.core.DeploymentOptions
import io.vertx.core.spi.VerticleFactory
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.ApplicationContext
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.shell.ShellService
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import io.vertx.ext.shell.term.HttpTermOptions
import io.vertx.ext.shell.ShellServiceOptions
import io.vertx.ext.shell.term.TermServer


@ComponentScan("com.advertex")
@Component
object Application{
    val vertx = Vertx.vertx()
    var context:AnnotationConfigApplicationContext?=null;
    @JvmStatic fun main(args:Array<String>){

        this.context= AnnotationConfigApplicationContext(Application::class.java)

        val verticleFactory = context?.getBean(SpringVerticleFactory::class.java)

        // The verticle factory is registered manually because it is created by the Spring container
        vertx.registerVerticleFactory(verticleFactory)

       }

    @PostConstruct
    fun test(){

        val verticleFactory = context?.getBean(SpringVerticleFactory::class.java)
        val options = DeploymentOptions().setInstances(5)
//        vertx.deployVerticle(verticleFactory?.prefix() + ":" + GreetingVerticle::class.java!!.getName(), options)
        vertx.deployVerticle(verticleFactory?.prefix() + ":" + StaticServerVerticle::class.java!!.getName())


        vertx.deployVerticle( "io.vertx.ext.shell.ShellVerticle",
                 DeploymentOptions().setConfig( JsonObject().
                        put("httpOptions",  JsonObject().
                                put("host", "localhost").
                                put("port", 8080))

//                                put("keyPairOptions",  JsonObject().
//                                        put("path", "src/test/resources/server-keystore.jks").
//                                        put("password", "wibble")).
//                                put("authOptions",  JsonObject().
//                                        put("provider", "shiro").
//                                        put("config",  JsonObject().
//                                                put("properties_path", "file:/path/to/my/auth.properties"))))
                )
        );


}}
