package io.github.gcapizzi.klient.http

import com.beust.klaxon.JsonObject
import io.github.gcapizzi.klient.util.Result
import okhttp3.*

class OkHttp3Client : HttpClient {
    val client = OkHttpClient()

    override fun call(request: HttpRequest): Result<HttpResponse> {
        return buildOkHttpRequest(request).andThen { okHttpRequest ->
            executeOkHttpRequest(okHttpRequest)
        }.map { okHttpResponse ->
            buildHttpResponse(okHttpResponse)
        }
    }

    private fun executeOkHttpRequest(okHttpRequest: Request): Result<Response> {
        try {
            return Result.Ok(client.newCall(okHttpRequest).execute())
        } catch (e: Exception) {
            return Result.Error(Exception("Error executing the HTTP request: ${e.message}"))
        }
    }

    private fun buildOkHttpRequest(request: HttpRequest): Result<Request> {
        return normalizeUrl(request.url).map { url ->
            if (request.method == "GET") {
                buildGetOkHttpRequest(url)
            } else {
                buildPostOkHttpRequest(url, request.body)
            }
        }
    }

    private fun normalizeUrl(url: String): Result<HttpUrl> {
        val normalizedUrl = HttpUrl.parse(url) ?: HttpUrl.parse("http:$url") ?: HttpUrl.parse("http://$url")
        return Result.of(normalizedUrl, Exception("Invalid URL"))
    }

    private fun buildGetOkHttpRequest(url: HttpUrl): Request {
        return Request.Builder()
                .url(url)
                .get()
                .build()
    }

    private fun buildPostOkHttpRequest(url: HttpUrl, body: Map<String, String>?): Request {
        val requestBody = RequestBody.create(MediaType.parse("application/json"), toJson(body))
        return Request.Builder()
                .url(url)
                .post(requestBody)
                .build()
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
