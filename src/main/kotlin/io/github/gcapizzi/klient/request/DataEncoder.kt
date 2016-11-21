package io.github.gcapizzi.klient.request

interface DataEncoder {
    fun encode(data: Map<String, String>): String
}