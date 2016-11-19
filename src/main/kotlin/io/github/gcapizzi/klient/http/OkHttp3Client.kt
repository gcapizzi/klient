package io.github.gcapizzi.klient.http

import io.github.gcapizzi.klient.HttpRequest
import okhttp3.*

class OkHttp3Client : HttpClient {
    val client = OkHttpClient()

    override fun call(request: HttpRequest): HttpResponse? {
        val okHttpRequest = buildOkHttpRequest(request)
        val okHttpResponse = client.newCall(okHttpRequest).execute()
        return buildHttpResponse(okHttpResponse)
    }

    private fun buildOkHttpRequest(request: HttpRequest): Request? {
        val normalizedUrl = normalizeUrl(request.url)
        var okHttpRequestBuilder = Request.Builder().url(normalizedUrl)

        if (request.method == "GET") {
            okHttpRequestBuilder = okHttpRequestBuilder.get()
        } else {
            val requestBody = RequestBody.create(MediaType.parse("application/json"), "")
            okHttpRequestBuilder = okHttpRequestBuilder.post(requestBody)
        }

        val okHttpRequest = okHttpRequestBuilder.build()
        return okHttpRequest
    }

    private fun buildHttpResponse(okHttpResponse: Response): HttpResponse {
        val status = okHttpResponse.code()
        val headers = okHttpResponse.headers().toMultimap()
        val body = okHttpResponse.body().string()
        return HttpResponse(status, headers, body)
    }

    private fun normalizeUrl(url: String): String {
        if (url.contains("://")) {
            return url
        } else {
            return "http://$url"
        }
    }
}