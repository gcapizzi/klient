package io.github.gcapizzi.http.client

import okhttp3.OkHttpClient
import okhttp3.Request

class OkHttp3Client : HttpClient {
    val client = OkHttpClient()

    override fun get(url: String): HttpResponse? {
        val normalizedUrl = normalizeUrl(url)
        val request = Request.Builder().get().url(normalizedUrl).build()
        val response = client.newCall(request).execute()
        val status = response.code()
        val headers = response.headers().toMultimap()
        val body = response.body().string()
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
