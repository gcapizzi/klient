package io.github.gcapizzi.klient

data class HttpRequest(val method: String, val url: String, val body: Map<String, String>? = null)