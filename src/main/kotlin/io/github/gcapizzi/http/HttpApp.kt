package io.github.gcapizzi.http

import io.github.gcapizzi.http.client.HttpClient

class HttpApp(val httpClient: HttpClient) {
    fun run(args: Array<String>): String {
        if (args.isEmpty()) {
            return "http: error: too few arguments"
        }

        val url = args[1]
        return httpClient.get(url) ?: ""
    }
}
