package io.github.gcapizzi.klient.requestbuilder

import io.github.gcapizzi.klient.HttpRequest

class DefaultRequestBuilder : RequestBuilder {
    override fun build(args: Array<String>): HttpRequest {
        val method = args[0]
        val url = normalizeUrl(args[1])
        val dataFields = args.drop(2)
        val body = buildBody(dataFields)
        return HttpRequest(method, url, body)
    }

    private fun normalizeUrl(url: String): String {
        if (url.contains("://")) {
            return url
        } else {
            return "http://$url"
        }
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