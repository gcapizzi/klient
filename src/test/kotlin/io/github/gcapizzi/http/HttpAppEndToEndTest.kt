package io.github.gcapizzi.http

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.containsSubstring
import com.natpryce.hamkrest.equalTo
import io.github.gcapizzi.http.client.OkHttp3Client
import org.junit.Test

class HttpAppEndToEndTest {
    private val httpClient = OkHttp3Client()
    private val httpApp = HttpApp(httpClient)

    @Test
    fun itMakesAnHttpRequestAndPrintsTheResults() {
        val output = httpApp.run(arrayOf("GET", "https://api.github.com/users/gcapizzi"))
        val (preamble, body) = output.split("\n\n")
        val (statusLine, headers) = preamble.split("\n", limit = 2)

        assertThat(statusLine, equalTo("HTTP/1.1 200 OK"))
        assertThat(headers, containsSubstring("content-type: application/json; charset=utf-8"))
        assertThat(body, containsSubstring("\"login\":\"gcapizzi\""))
    }
}
