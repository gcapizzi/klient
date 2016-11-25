package io.github.gcapizzi.klient

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.containsSubstring
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.startsWith
import io.github.gcapizzi.klient.http.OkHttp3Client
import io.github.gcapizzi.klient.request.CliRequestBuilder
import io.github.gcapizzi.klient.request.JsonDataEncoder
import io.github.gcapizzi.klient.response.DefaultResponseFormatter
import org.junit.Test

class KlientAppEndToEndTest {
    private val httpClient = OkHttp3Client()
    private val dataEncoder = JsonDataEncoder()
    private val requestBuilder = CliRequestBuilder(dataEncoder)
    private val responseFormatter = DefaultResponseFormatter()
    private val klientApp = KlientApp(httpClient, requestBuilder, responseFormatter)

    @Test
    fun itMakesAnHttpRequestAndPrintsTheResults() {
        val outcome = klientApp.run(arrayOf("POST", "httpbin.org/post", "foo=bar"))

        assertThat(outcome.status, equalTo(0))

        val (headers, body) = outcome.output.split("\n\n")

        assertThat(headers, startsWith("HTTP/1.1 200 OK\n"))
        assertThat(headers, containsSubstring("content-type: application/json\n"))

        val parsedBody = parseJson(body)

        assertThat(parsedBody["json"] as JsonObject, equalTo(JsonObject(mapOf("foo" to "bar"))))
    }

    private fun parseJson(jsonString: String): JsonObject {
        return Parser().parse(jsonString.byteInputStream()) as JsonObject
    }
}
