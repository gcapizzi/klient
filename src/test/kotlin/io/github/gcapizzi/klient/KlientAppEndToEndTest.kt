package io.github.gcapizzi.klient

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.containsSubstring
import com.natpryce.hamkrest.equalTo
import io.github.gcapizzi.klient.http.OkHttp3Client
import io.github.gcapizzi.klient.formatter.DefaultResponseFormatter
import org.junit.Test

class KlientAppEndToEndTest {
    private val httpClient = OkHttp3Client()
    private val responseFormatter = DefaultResponseFormatter()
    private val klientApp = KlientApp(httpClient, responseFormatter)

    @Test
    fun itMakesAnHttpRequestAndPrintsTheResults() {
        val output = klientApp.run(arrayOf("GET", "https://api.github.com/users/gcapizzi"))
        val (preamble, body) = output.split("\n\n")
        val (statusLine, headers) = preamble.split("\n", limit = 2)

        assertThat(statusLine, equalTo("HTTP/1.1 200 OK"))
        assertThat(headers, containsSubstring("content-type: application/json; charset=utf-8"))
        assertThat(body, containsSubstring("\"login\":\"gcapizzi\""))
    }
}
