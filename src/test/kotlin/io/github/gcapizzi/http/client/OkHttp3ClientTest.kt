package io.github.gcapizzi.http.client

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class OkHttp3ClientTest {
    private val okHttpClient = OkHttp3Client()

    @Test
    fun itExecutesAGetRequest() {
        val response = okHttpClient.get("http://httpbin.org/get")!!

        assertThat(response.status, equalTo(200))
        assertThat(response.headers["content-type"]?.first(), equalTo("application/json"))
        val parsedOutput = parseJson(response.body)
        assertThat(parsedOutput["url"] as String, equalTo("http://httpbin.org/get"))
    }

    private fun parseJson(jsonString: String): JsonObject {
        return Parser().parse(jsonString.byteInputStream()) as JsonObject
    }
}
