package io.github.gcapizzi.klient.request

class FormDataEncoder : DataEncoder {
    override fun encode(data: Map<String, String>): String {
        return data
                .map { "${it.key}=${it.value}" }
                .sorted()
                .joinToString("&")
    }
}