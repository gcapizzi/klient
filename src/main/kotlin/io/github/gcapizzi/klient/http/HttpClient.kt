package io.github.gcapizzi.klient.http

import io.github.gcapizzi.klient.util.Result

interface HttpClient {
    fun call(request: HttpRequest): Result<HttpResponse>
}
