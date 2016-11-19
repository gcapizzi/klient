package io.github.gcapizzi.klient

import io.github.gcapizzi.klient.http.HttpClient
import io.github.gcapizzi.klient.formatter.ResponseFormatter

class KlientApp(val httpClient: HttpClient, val responseFormatter: ResponseFormatter) {
    fun run(args: Array<String>): String {
        if (args.isEmpty()) {
            return "klient: error: too few arguments"
        }

        val (method, url) = args
        val response = httpClient.call(HttpRequest(method, url))!!

        return responseFormatter.format(response)
    }
}
