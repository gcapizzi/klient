package io.github.gcapizzi.http

import io.github.gcapizzi.http.client.HttpClient
import io.github.gcapizzi.http.formatter.ResponseFormatter

class HttpApp(val httpClient: HttpClient, val responseFormatter: ResponseFormatter) {
    fun run(args: Array<String>): String {
        if (args.isEmpty()) {
            return "http: error: too few arguments"
        }

        val url = args[1]
        val response = httpClient.get(url)!!

        return responseFormatter.format(response)
    }
}
