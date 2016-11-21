package io.github.gcapizzi.klient.http

data class HttpRequest(val method: HttpMethod, val url: String, val body: String? = null)