package io.github.gcapizzi.klient.http

import com.beust.klaxon.JsonObject
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
        var okHttpRequestBuilder = Request.Builder().url(request.url)

        if (request.method == "GET") {
            okHttpRequestBuilder = okHttpRequestBuilder.get()
        } else {
            val requestBody = RequestBody.create(MediaType.parse("application/json"), toJson(request.body))
            okHttpRequestBuilder = okHttpRequestBuilder.post(requestBody)
        }

        return okHttpRequestBuilder.build()
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
