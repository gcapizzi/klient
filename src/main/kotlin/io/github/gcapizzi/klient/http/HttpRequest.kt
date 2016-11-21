package io.github.gcapizzi.klient.http

import io.github.gcapizzi.klient.HttpMethod

data class HttpRequest(val method: HttpMethod, val url: String, val body: Map<String, String>? = null)