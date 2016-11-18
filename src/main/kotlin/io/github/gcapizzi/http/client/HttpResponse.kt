package io.github.gcapizzi.http.client

data class HttpResponse(val status: Int, val headers: Map<String, List<String>>, val body: String)
