package io.github.gcapizzi.klient.formatter

import io.github.gcapizzi.klient.http.HttpResponse

interface ResponseFormatter {
    fun format(response: HttpResponse): String
}
