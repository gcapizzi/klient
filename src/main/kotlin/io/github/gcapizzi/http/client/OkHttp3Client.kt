package io.github.gcapizzi.http.client

import okhttp3.OkHttpClient
import okhttp3.Request

class OkHttp3Client : HttpClient {
    val client = OkHttpClient()

    override fun get(url: String): String? {
        val request = Request.Builder().get().url(url).build()
        val response = client.newCall(request).execute()
        return response.body().string()
    }
}
