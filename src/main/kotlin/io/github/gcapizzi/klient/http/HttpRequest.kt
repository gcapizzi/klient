package io.github.gcapizzi.klient.http

data class HttpRequest(val method: String, val url: String, val body: Map<String, String>? = null)