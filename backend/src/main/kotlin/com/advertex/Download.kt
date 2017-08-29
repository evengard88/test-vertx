package com.advertex

import io.vertx.core.streams.Pump
import io.vertx.core.file.AsyncFile
import io.vertx.core.file.OpenOptions
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.Vertx
import io.vertx.core.AbstractVerticle
import io.vertx.core.Handler
import io.vertx.core.http.HttpMethod
import io.vertx.core.http.HttpServerRequest
import io.vertx.ext.web.Router


class Download : AbstractVerticle() {
    @Throws(Exception::class)
    override fun start() {
        val router = Router.router(vertx)

        router.route(HttpMethod.GET, "/download2").handler({ rtx ->
            println("download started")
            val response = rtx.response()
            response.setChunked(true)
            response.sendFile("/root/Downloads/Death.Note.2017.1080p.WEBRip.x264.NNMCLUB.mkv");
            // C:/Anil/dev_config.json
//          C  vertx.fileSystem().open("C:/Anil/dev_config.json", OpenOptions()) { file ->
//                val asyncFile = file.result()
//                val pump = Pump.pump<Buffer>(asyncFile, response)
//                response.closeHandler({ res -> println("completed") })
//                response.exceptionHandler({ ex ->
//                    System.err.println("exception")
//                    ex.printStackTrace()
//                })
//                pump.start()
//            }
        })
        vertx.createHttpServer().requestHandler(Handler<HttpServerRequest> { router.accept(it) }).listen(8087) { res ->
            if (res.succeeded()) {
                println("listenting to port 8080")
            } else {
                println("port is busy")
            }
        }
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val vertx = Vertx.vertx()
            vertx.deployVerticle(Download())
        }
    }
}