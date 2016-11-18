package io.github.gcapizzi.klient.http

data class HttpResponse(val status: Int, val headers: Map<String, List<String>>, val body: String)
