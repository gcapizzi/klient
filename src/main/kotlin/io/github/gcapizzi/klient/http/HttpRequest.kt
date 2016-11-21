package io.github.gcapizzi.klient.http

import io.github.gcapizzi.klient.http.HttpMethod

data class HttpRequest(val method: HttpMethod, val url: String, val body: String? = null)