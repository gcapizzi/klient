package io.github.gcapizzi.klient.request

import io.github.gcapizzi.klient.http.HttpMethod
import io.github.gcapizzi.klient.http.HttpRequest
import io.github.gcapizzi.klient.util.Result

class CliRequestBuilder(val dataEncoder: DataEncoder) : RequestBuilder {
    override fun build(args: Array<String>): Result<HttpRequest> {
        if (args.isEmpty()) {
            return Result.Error(Exception("Too few arguments"))
        }

        val method: HttpMethod
        val url: String
        val dataFields: List<String>

        val maybeMethod = parseMethod(args[0])
        if (maybeMethod == null) {
            url = args[0]
            dataFields = args.drop(1)
            method = if (dataFields.isEmpty()) {
                HttpMethod.GET
            } else {
                HttpMethod.POST
            }
        } else {
            url = args[1]
            dataFields = args.drop(2)
            method = maybeMethod
        }

        val body = buildBody(dataFields)

        return Result.Ok(HttpRequest(method, url, body))
    }

    private fun parseMethod(firstArg: String): HttpMethod? {
        try {
            return HttpMethod.valueOf(firstArg)
        } catch (e: IllegalArgumentException) {
            return null
        }
    }

    private fun buildBody(dataFields: List<String>): String? {
        if (dataFields.isEmpty()) {
            return null
        }

        val dataMap = dataFields.map { parseDataField(it) }.toMap()
        return dataEncoder.encode(dataMap)
    }

    private fun parseDataField(string: String): Pair<String, String> {
        val (key, value) = string.split("=")
        return Pair(key, value)
    }
}
