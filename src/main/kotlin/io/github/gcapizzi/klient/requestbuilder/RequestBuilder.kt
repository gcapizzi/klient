package io.github.gcapizzi.klient.requestbuilder

import io.github.gcapizzi.klient.http.HttpRequest

interface RequestBuilder {
    fun build(args: Array<String>): HttpRequest
}