package io.github.gcapizzi.klient.response

import io.github.gcapizzi.klient.http.HttpResponse

interface ResponseFormatter {
    fun format(response: HttpResponse): String
}
