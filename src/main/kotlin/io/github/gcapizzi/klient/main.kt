package io.github.gcapizzi.klient

import io.github.gcapizzi.klient.http.OkHttp3Client
import io.github.gcapizzi.klient.formatter.DefaultResponseFormatter

fun main(args: Array<String>) {
    val httpClient = OkHttp3Client()
    val responseFormatter = DefaultResponseFormatter()
    val klientApp = KlientApp(httpClient, responseFormatter)

    println(klientApp.run(args))
}
