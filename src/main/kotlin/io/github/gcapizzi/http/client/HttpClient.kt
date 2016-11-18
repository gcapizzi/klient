package io.github.gcapizzi.http.client

interface HttpClient {
    fun get(url: String): HttpResponse?
}
