package io.github.gcapizzi.http.client

import okhttp3.OkHttpClient
import okhttp3.Request

class OkHttp3Client : HttpClient {
    val client = OkHttpClient()

    override fun get(url: String): HttpResponse? {
        val request = Request.Builder().get().url(url).build()
        val response = client.newCall(request).execute()
        val status = response.code()
        val headers = response.headers().toMultimap()
        val body = response.body().string()
        return HttpResponse(status, headers, body)
    }
}
