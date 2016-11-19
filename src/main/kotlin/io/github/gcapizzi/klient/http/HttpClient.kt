package io.github.gcapizzi.klient.http

import io.github.gcapizzi.klient.HttpRequest

interface HttpClient {
    fun call(request: HttpRequest): HttpResponse?
}
