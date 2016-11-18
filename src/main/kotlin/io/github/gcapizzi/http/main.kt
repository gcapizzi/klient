package io.github.gcapizzi.http

import io.github.gcapizzi.http.client.OkHttp3Client
import io.github.gcapizzi.http.formatter.DefaultResponseFormatter

fun main(args: Array<String>) {
    val httpClient = OkHttp3Client()
    val responseFormatter = DefaultResponseFormatter()
    val httpApp = HttpApp(httpClient, responseFormatter)

    println(httpApp.run(args))
}
