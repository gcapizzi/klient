package io.github.gcapizzi.klient.request

import io.github.gcapizzi.klient.http.HttpMethod
import io.github.gcapizzi.klient.http.HttpRequest
import io.github.gcapizzi.klient.util.Result

class CliRequestBuilder(val dataEncoder: DataEncoder) : RequestBuilder {
    override fun build(args: Array<String>): Result<HttpRequest> {
        if (args.size < 2) {
            return Result.Error(Exception("Too few arguments"))
        }

        val method = HttpMethod.valueOf(args[0])
        val url = args[1]
        val dataFields = args.drop(2)
        val body = buildBody(dataFields)
        return Result.Ok(HttpRequest(method, url, body))
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
