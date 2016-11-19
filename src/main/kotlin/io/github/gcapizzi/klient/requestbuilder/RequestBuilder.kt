package io.github.gcapizzi.klient.requestbuilder

import io.github.gcapizzi.klient.HttpRequest

interface RequestBuilder {
    fun build(args: Array<String>): HttpRequest
}