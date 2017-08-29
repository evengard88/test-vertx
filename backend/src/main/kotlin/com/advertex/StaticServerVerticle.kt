package com.advertex

import com.fasterxml.jackson.databind.ObjectMapper
import io.vertx.core.AbstractVerticle
import io.vertx.core.Handler
import io.vertx.core.http.HttpServerFileUpload
import io.vertx.core.http.HttpServerRequest
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.auth.oauth2.AccessToken
import io.vertx.ext.auth.oauth2.OAuth2Auth
import io.vertx.ext.auth.oauth2.OAuth2ClientOptions
import io.vertx.ext.auth.oauth2.OAuth2FlowType
import io.vertx.ext.auth.oauth2.providers.GoogleAuth

import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.OAuth2AuthHandler
import io.vertx.ext.web.handler.SessionHandler
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.ext.web.sstore.LocalSessionStore
import io.vertx.kotlin.core.json.JsonArray
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import org.springframework.stereotype.Component


@Component
class StaticServerVerticle : AbstractVerticle() {

    companion object {
        private val LOG = LoggerFactory.getLogger(GreetingVerticle::class.java)
    }

    @Throws(Exception::class)
    override fun start() {
        val router = Router.router(vertx)

        // Serve the static pages
//        router.route().handler(StaticHandler.create())
        router.route().handler({rc->
            println( "all request handler")
            println("${rc.session()}")
            println("data ${rc.session()?.data()}")
            println("id ${rc.session()?.id()}")
            println("old ${rc.session()?.oldId()}")
            println("${rc.user()}")

            println("${rc.bodyAsString}")
            println("${rc.bodyAsJson}")
            println("${rc.parsedHeaders()}")
            println("${rc.request().headers()}")
            println("${rc.request().absoluteURI()}")
            println("${rc.request().path()}")
            println("${rc.normalisedPath()}")
            println("${rc.request().params()}")
            println("${rc.pathParams()}")
            println("${rc.pathParams()}")
            rc.next()

        })
//        router.route().handler(CookieHandler.create());
//        router.route().handler(BodyHandler.create());
//        router.route("/*").handler(LoggerHandlerImpl(true, LoggerFormat.DEFAULT));

        // protect everything under /protected
        router.route("/protected/*").handler(oauthProviderHandler(router))
// mount some handler under the protected zone
        router.route("/protected/somepage").handler { rc ->
            val accessToken = rc.user() as AccessToken
            println( "all request handler")
            println(accessToken.principal())
            println(rc.session().data())
            println(rc.session())
            rc.response().putHeader("content-type", "text/plain");
            rc.response().end("Welcome to the protected resource!");
            rc.next()
        }

//        router.route("/callback").handler { event ->
//
//            val response = event.response()
//            response.putHeader("Content-Type", "text/plain; charset=utf-8")
//
//            // Write to the response and end it
//            response.end("Hello World2!");
//        }
//        router.route("/callback_ya").handler { event ->
//
//            val response = event.response()
//            response.putHeader("Content-Type", "text/plain; charset=utf-8")
//
//            // Write to the response and end it
//            response.end("Hello World2!");
//        }
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
        val mapper = ObjectMapper()
        vertx.createHttpServer()
                .requestHandler(Handler<HttpServerRequest> {
                    router.accept(it)
                })
                .listen(80)
    }

    fun oauthProviderHandler(router: Router): Handler<RoutingContext> {

//        var authProvider = GoogleAuth.create(vertx, "157504429567-8gmniduq38752dbe7gdo67snp39d0um4.apps.googleusercontent.com", "UbK-GEUbjPh2X1bQ8gxiMOlN")
        var authProvider = OAuth2Auth.create(vertx, OAuth2FlowType.AUTH_CODE,
                OAuth2ClientOptions()
                        .setSite("https://oauth.yandex.ru/")
                        .setAuthorizationPath("/authorize")
                        .setTokenPath("/token")
//                        .setIntrospectionPath("/authorize")
                        .setScopeSeparator(" ")
                        .setClientID("a5899154ff094a61ac49c1c9f3e17377")
                        .setClientSecret("ad121ce793d54294a4dbc8db02cf2e2c")
                )

//        var authProvider = OAuth2Auth.create(vertx, OAuth2FlowType.AUTH_CODE, OAuth2ClientOptions()
//                .setSite("https://accounts.google.com")
//                .setTokenPath("https://www.googleapis.com/oauth2/v3/token&scope=ee")
//                .setAuthorizationPath("/o/oauth2/auth")
//                .setIntrospectionPath("https://www.googleapis.com/oauth2/v3/tokeninfo")
//                .setScopeSeparator(" ")
//                .setClientID("157504429567-8gmniduq38752dbe7gdo67snp39d0um4.apps.googleusercontent.com")
//                .setClientSecret("UbK-GEUbjPh2X1bQ8gxiMOlN")
//        );


// create a oauth2 handler on our running server
// the second argument is the full url to the callback as you entered in your provider management console.
        val oauth2 = OAuth2AuthHandler.create(authProvider, "http://94.130.57.71/callback_ya")
//        oauth2.addAuthority("https://www.googleapis.com/auth/adwords")
//        oauth2.addAuthority("profile")

// setup the callback handler for receiving the GitHub callback
//        oauth2.setupCallback(router.get("/callback_ya2"))

        return oauth2
    }
}