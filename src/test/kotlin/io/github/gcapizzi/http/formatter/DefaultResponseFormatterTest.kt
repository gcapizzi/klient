package io.github.gcapizzi.http.formatter

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.github.gcapizzi.http.client.HttpResponse
import org.junit.Test

class DefaultResponseFormatterTest {
    private val formatter = DefaultResponseFormatter()

    @Test
    fun itFormatsAnHttpResponse() {
        val headers = mapOf("Foo-Header" to listOf("foo"), "Bar-Header" to listOf("bar", "baz"))
        val response = HttpResponse(404, headers, "the response body")

        val output = formatter.format(response)
        val (preambleOutput, bodyOutput) = output.split("\n\n")
        val (statusLineOutput, headersOutput) = preambleOutput.split("\n", limit = 2)

        assertThat(statusLineOutput, equalTo("HTTP/1.1 404 Not Found"))
        assertThat(headersOutput, equalTo("foo-header: foo\nbar-header: bar, baz"))
        assertThat(bodyOutput, equalTo("the response body"))
    }
}