package io.github.gcapizzi.klient

import io.github.gcapizzi.klient.http.OkHttp3Client
import io.github.gcapizzi.klient.requestbuilder.DefaultRequestBuilder
import io.github.gcapizzi.klient.responseformatter.DefaultResponseFormatter

fun main(args: Array<String>) {
    val httpClient = OkHttp3Client()
    val requestBuilder = DefaultRequestBuilder()
    val responseFormatter = DefaultResponseFormatter()
    val klientApp = KlientApp(httpClient, requestBuilder, responseFormatter)

    println(klientApp.run(args))
}
