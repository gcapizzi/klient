package io.github.gcapizzi.http.client

interface HttpClient {
    fun get(url: String): String? {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
