package io.github.gcapizzi.klient

import io.github.gcapizzi.klient.formatter.ResponseFormatter
import io.github.gcapizzi.klient.http.HttpClient

class KlientApp(val httpClient: HttpClient, val responseFormatter: ResponseFormatter) {
    fun run(args: Array<String>): String {
        if (args.isEmpty()) {
            return "klient: error: too few arguments"
        }

        val request = buildRequest(args)
        val response = httpClient.call(request)!!
        return responseFormatter.format(response)
    }

    private fun buildRequest(args: Array<String>): HttpRequest {
        val method = args[0]
        val url = args[1]
        val dataFields = args.drop(2)
        val body = buildBody(dataFields)
        val request = HttpRequest(method, url, body)
        return request
    }

    private fun buildBody(dataFields: List<String>): Map<String, String>? {
        if (dataFields.isEmpty()) {
            return null
        }

        return dataFields.map { buildPair(it) }.toMap()
    }

    private fun buildPair(string: String): Pair<String, String> {
        val (key, value) = string.split("=")
        return Pair(key, value)
    }
}
