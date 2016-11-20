package io.github.gcapizzi.klient.requestbuilder

import io.github.gcapizzi.klient.http.HttpRequest
import io.github.gcapizzi.klient.util.Result

class DefaultRequestBuilder : RequestBuilder {
    override fun build(args: Array<String>): Result<HttpRequest> {
        if (args.size < 2) {
            return Result.Error(Exception("Too few arguments"))
        }

        val method = args[0]
        val url = args[1]
        val dataFields = args.drop(2)
        val body = buildBody(dataFields)
        return Result.Ok(HttpRequest(method, url, body))
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