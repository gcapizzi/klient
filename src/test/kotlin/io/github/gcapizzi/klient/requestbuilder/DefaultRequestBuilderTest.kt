package io.github.gcapizzi.klient.requestbuilder

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.github.gcapizzi.klient.HttpRequest
import org.junit.Test

class DefaultRequestBuilderTest {
    private val requestBuilder = DefaultRequestBuilder()

    @Test
    fun itBuildsAnHttpRequestFromTheListOfCommandLineArgs() {
        val httpRequest = requestBuilder.build(arrayOf("METHOD", "http://url"))
        assertThat(httpRequest, equalTo(HttpRequest("METHOD", "http://url")))
    }

    @Test
    fun itUsesHttpAsTheDefaultSchema() {
        val httpRequest = requestBuilder.build(arrayOf("METHOD", "url"))
        assertThat(httpRequest, equalTo(HttpRequest("METHOD", "http://url")))
    }

    @Test
    fun itAllowsToSpecifyDataFields() {
        val args = arrayOf("METHOD", "http://url", "foo=1", "bar=2", "baz=3")
        val httpRequest = requestBuilder.build(args)

        assertThat(httpRequest.method, equalTo("METHOD"))
        assertThat(httpRequest.url, equalTo("http://url"))
        assertThat(httpRequest.body, equalTo(mapOf("foo" to "1", "bar" to "2", "baz" to "3")))
    }
}