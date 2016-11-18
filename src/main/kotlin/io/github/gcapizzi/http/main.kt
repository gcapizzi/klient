package io.github.gcapizzi.http

import io.github.gcapizzi.http.client.OkHttp3Client

fun main(args: Array<String>) {
    val httpClient = OkHttp3Client()
    val httpApp = HttpApp(httpClient)

    println(httpApp.run(args))
}
