package io.github.gcapizzi.klient.request

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.github.gcapizzi.klient.HttpMethod
import io.github.gcapizzi.klient.http.HttpRequest
import io.github.gcapizzi.klient.util.Result
import org.junit.Test

class DefaultRequestBuilderTest {
    private val requestBuilder = DefaultRequestBuilder()

    @Test
    fun itReturnsAnErrorIfTooFewArguments() {
        val errorWithNoArgs = requestBuilder.build(arrayOf()) as? Result.Error
        assertThat(errorWithNoArgs!!.value.message, equalTo("Too few arguments"))

        val errorWithOneArg = requestBuilder.build(arrayOf("GET")) as? Result.Error
        assertThat(errorWithOneArg!!.value.message, equalTo("Too few arguments"))
    }

    @Test
    fun itBuildsAnHttpRequestFromTheListOfCommandLineArgs() {
        val httpRequest = requestBuilder.build(arrayOf("GET", "http://url")).unwrap()
        assertThat(httpRequest, equalTo(HttpRequest(HttpMethod.GET, "http://url")))
    }

    @Test
    fun itAllowsToSpecifyDataFields() {
        val args = arrayOf("GET", "http://url", "foo=1", "bar=2", "baz=3")
        val httpRequest = requestBuilder.build(args).unwrap()

        assertThat(httpRequest.method, equalTo(HttpMethod.GET))
        assertThat(httpRequest.url, equalTo("http://url"))
        assertThat(httpRequest.body, equalTo(mapOf("foo" to "1", "bar" to "2", "baz" to "3")))
    }
}