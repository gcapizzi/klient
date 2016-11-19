package io.github.gcapizzi.klient

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.containsSubstring
import com.natpryce.hamkrest.startsWith
import io.github.gcapizzi.klient.http.OkHttp3Client
import io.github.gcapizzi.klient.requestbuilder.DefaultRequestBuilder
import io.github.gcapizzi.klient.responseformatter.DefaultResponseFormatter
import org.junit.Test

class KlientAppEndToEndTest {
    private val httpClient = OkHttp3Client()
    private val requestBuilder = DefaultRequestBuilder()
    private val responseFormatter = DefaultResponseFormatter()
    private val klientApp = KlientApp(httpClient, requestBuilder, responseFormatter)

    @Test
    fun itMakesAnHttpRequestAndPrintsTheResults() {
        val output = klientApp.run(arrayOf("GET", "api.github.com/users/gcapizzi"))
        val (headers, body) = output.split("\n\n")

        assertThat(headers, startsWith("HTTP/1.1 200 OK\n"))
        assertThat(headers, containsSubstring("content-type: application/json; charset=utf-8\n"))
        assertThat(body, containsSubstring("\"login\":\"gcapizzi\""))
    }
}
