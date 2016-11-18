package io.github.gcapizzi.klient

import io.github.gcapizzi.klient.http.HttpClient
import io.github.gcapizzi.klient.formatter.ResponseFormatter

class KlientApp(val httpClient: HttpClient, val responseFormatter: ResponseFormatter) {
    fun run(args: Array<String>): String {
        if (args.isEmpty()) {
            return "klient: error: too few arguments"
        }

        val url = args[1]
        val response = httpClient.get(url)!!

        return responseFormatter.format(response)
    }
}
