package io.github.gcapizzi.klient

import io.github.gcapizzi.klient.http.OkHttp3Client
import io.github.gcapizzi.klient.request.CliRequestBuilder
import io.github.gcapizzi.klient.request.JsonDataEncoder
import io.github.gcapizzi.klient.response.DefaultResponseFormatter
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val httpClient = OkHttp3Client()
    val dataEncoder = JsonDataEncoder()
    val requestBuilder = CliRequestBuilder(dataEncoder)
    val responseFormatter = DefaultResponseFormatter()
    val klientApp = KlientApp(httpClient, requestBuilder, responseFormatter)

    val outcome = klientApp.run(args)

    println(outcome.output)
    exitProcess(outcome.status)
}
