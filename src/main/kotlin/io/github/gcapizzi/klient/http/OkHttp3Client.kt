package io.github.gcapizzi.klient.http

import com.beust.klaxon.JsonObject
import okhttp3.*

class OkHttp3Client : HttpClient {
    val client = OkHttpClient()

    override fun call(request: HttpRequest): HttpResponse? {
        val okHttpRequest = buildOkHttpRequest(request)
        val okHttpResponse = client.newCall(okHttpRequest).execute()
        return buildHttpResponse(okHttpResponse)
    }

    private fun buildOkHttpRequest(request: HttpRequest): Request? {
        val url = normalizeUrl(request.url) ?: return null
        var okHttpRequestBuilder = Request.Builder().url(url)

        if (request.method == "GET") {
            okHttpRequestBuilder = okHttpRequestBuilder.get()
        } else {
            val requestBody = RequestBody.create(MediaType.parse("application/json"), toJson(request.body))
            okHttpRequestBuilder = okHttpRequestBuilder.post(requestBody)
        }

        return okHttpRequestBuilder.build()
    }

    private fun normalizeUrl(url: String): HttpUrl? {
        return HttpUrl.parse(url) ?: HttpUrl.parse("http:$url") ?: HttpUrl.parse("http://$url")
    }

    private fun toJson(body: Map<String, String>?): String {
        return JsonObject(body.orEmpty()).toJsonString()
    }

    private fun buildHttpResponse(okHttpResponse: Response): HttpResponse {
        val status = okHttpResponse.code()
        val headers = okHttpResponse.headers().toMultimap()
        val body = okHttpResponse.body().string()
        return HttpResponse(status, headers, body)
    }
}
