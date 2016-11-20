package io.github.gcapizzi.klient.http

interface HttpClient {
    fun call(request: HttpRequest): HttpResponse?
}
