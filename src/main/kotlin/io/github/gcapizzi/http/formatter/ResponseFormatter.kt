package io.github.gcapizzi.http.formatter

import io.github.gcapizzi.http.client.HttpResponse

interface ResponseFormatter {
    fun format(response: HttpResponse): String
}
