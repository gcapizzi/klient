package io.github.gcapizzi.http

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.github.gcapizzi.http.client.HttpClient
import io.github.gcapizzi.http.client.HttpResponse
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

class HttpAppTest {
    private val httpClient = mock(HttpClient::class.java)
    private val httpApp = HttpApp(httpClient)

    @Test
    fun itReturnsAnErrorWhenCalledWithNoArguments() {
        val output = httpApp.run(arrayOf())
        assertThat(output, equalTo("http: error: too few arguments"))
    }

    @Test
    fun itMakesAnHttpGetRequestAndReturnsTheResults() {
        val headers = mapOf("Foo-Header" to listOf("foo"), "Bar-Header" to listOf("bar", "baz"))
        val response = HttpResponse(404, headers, "the response body")
        given(httpClient.get("http://example.com")).willReturn(response)

        val output = httpApp.run(arrayOf("GET", "http://example.com"))
        val (preambleOutput, bodyOutput) = output.split("\n\n")
        val (statusLineOutput, headersOutput) = preambleOutput.split("\n", limit = 2)

        assertThat(statusLineOutput, equalTo("HTTP/1.1 404 Not Found"))
        assertThat(headersOutput, equalTo("foo-header: foo\nbar-header: bar, baz"))
        assertThat(bodyOutput, equalTo("the response body"))
    }
}
