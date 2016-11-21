package io.github.gcapizzi.klient.request

import com.beust.klaxon.JsonObject

class JsonDataEncoder : DataEncoder {
    override fun encode(data: Map<String, String>): String {
        return JsonObject(data).toJsonString()
    }
}