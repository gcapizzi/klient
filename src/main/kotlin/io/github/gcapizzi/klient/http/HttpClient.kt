package io.github.gcapizzi.klient.http

interface HttpClient {
    fun get(url: String): HttpResponse?
}
