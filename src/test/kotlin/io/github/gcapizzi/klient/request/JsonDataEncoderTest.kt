package io.github.gcapizzi.klient.request

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class JsonDataEncoderTest {
    private val jsonDataEncoder = JsonDataEncoder()

    @Test
    fun itEncodesAMapToJson() {
        val data = mapOf("foo" to "bar")
        val json = jsonDataEncoder.encode(data)

        assertThat(parseJson(json), equalTo(JsonObject(data)))
    }

    private fun parseJson(jsonString: String): JsonObject {
        return Parser().parse(jsonString.byteInputStream()) as JsonObject
    }
}