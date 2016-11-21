package io.github.gcapizzi.klient.request

import io.github.gcapizzi.klient.http.HttpRequest
import io.github.gcapizzi.klient.util.Result

interface RequestBuilder {
    fun build(args: Array<String>): Result<HttpRequest>
}