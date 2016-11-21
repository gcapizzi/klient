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

    private fun buildOkHttpRequest(request: HttpRequest): Result<Request> {
        return normalizeUrl(request.url).andThen { url ->
            val okHttpRequest = when (request.method) {
                "GET" -> Request.Builder().get()
                "POST" -> Request.Builder().post(jsonRequestBody(request.body))
                "PUT" -> Request.Builder().put(jsonRequestBody(request.body))
                "PATCH" -> Request.Builder().patch(jsonRequestBody(request.body))
                "DELETE" -> Request.Builder().delete(jsonRequestBody(request.body))
                else -> Request.Builder()
            }.url(url).build()

            Result.Ok(okHttpRequest)
        }
    }

    private fun jsonRequestBody(body: Map<String, String>?): RequestBody? {
        return RequestBody.create(MediaType.parse("application/json"), toJson(body))
    }

    private fun normalizeUrl(url: String): Result<HttpUrl> {
        val normalizedUrl = HttpUrl.parse(url) ?: HttpUrl.parse("http:$url") ?: HttpUrl.parse("http://$url")
        return Result.of(normalizedUrl, Exception("Invalid URL"))
    }

    private fun toJson(body: Map<String, String>?): String {
        return body?.let { JsonObject(body).toJsonString() } ?: ""
    }

    private fun executeOkHttpRequest(okHttpRequest: Request): Result<Response> {
        try {
            return Result.Ok(client.newCall(okHttpRequest).execute())
        } catch (e: Exception) {
            return Result.Error(Exception("Error executing the HTTP request: ${e.message}"))
        }
    }

    private fun buildHttpResponse(okHttpResponse: Response): HttpResponse {
        val status = okHttpResponse.code()
        val headers = okHttpResponse.headers().toMultimap()
        val body = okHttpResponse.body().string()
        return HttpResponse(status, headers, body)
    }
}
