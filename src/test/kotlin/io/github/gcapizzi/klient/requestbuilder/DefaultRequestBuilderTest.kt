package io.github.gcapizzi.klient.requestbuilder

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.github.gcapizzi.klient.http.HttpRequest
import io.github.gcapizzi.klient.util.Result
import org.junit.Test

class DefaultRequestBuilderTest {
    private val requestBuilder = DefaultRequestBuilder()

    @Test
    fun itReturnsAnErrorIfTooFewArguments() {
        val errorWithNoArgs = requestBuilder.build(arrayOf()) as? Result.Error
        assertThat(errorWithNoArgs!!.value.message, equalTo("Too few arguments"))

        val errorWithOneArg = requestBuilder.build(arrayOf("METHOD")) as? Result.Error
        assertThat(errorWithOneArg!!.value.message, equalTo("Too few arguments"))
    }

    @Test
    fun itBuildsAnHttpRequestFromTheListOfCommandLineArgs() {
        val httpRequest = requestBuilder.build(arrayOf("METHOD", "http://url")).unwrap()
        assertThat(httpRequest, equalTo(HttpRequest("METHOD", "http://url")))
    }

    @Test
    fun itAllowsToSpecifyDataFields() {
        val args = arrayOf("METHOD", "http://url", "foo=1", "bar=2", "baz=3")
        val httpRequest = requestBuilder.build(args).unwrap()

        assertThat(httpRequest.method, equalTo("METHOD"))
        assertThat(httpRequest.url, equalTo("http://url"))
        assertThat(httpRequest.body, equalTo(mapOf("foo" to "1", "bar" to "2", "baz" to "3")))
    }
}