package io.github.gcapizzi.klient.request

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.github.gcapizzi.klient.http.HttpMethod
import io.github.gcapizzi.klient.http.HttpRequest
import io.github.gcapizzi.klient.util.Result
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

class CliRequestBuilderTest {
    private val dataEncoder = mock(DataEncoder::class.java)
    private val requestBuilder = CliRequestBuilder(dataEncoder)

    @Test
    fun itReturnsAnErrorIfNoArguments() {
        val errorWithNoArgs = requestBuilder.build(arrayOf()) as? Result.Error
        assertThat(errorWithNoArgs!!.value.message, equalTo("Too few arguments"))
    }

    @Test
    fun itBuildsAnHttpRequestFromTheListOfCommandLineArgs() {
        val httpRequest = requestBuilder.build(arrayOf("GET", "http://url")).unwrap()
        assertThat(httpRequest, equalTo(HttpRequest(HttpMethod.GET, "http://url")))
    }

    @Test
    fun itUsesGetAsTheDefaultMethodWhenNoDataFields() {
        val httpRequest = requestBuilder.build(arrayOf("http://url")).unwrap()
        assertThat(httpRequest, equalTo(HttpRequest(HttpMethod.GET, "http://url")))
    }

    @Test
    fun itAllowsToSpecifyDataFields() {
        given(dataEncoder.encode(mapOf("foo" to "1", "bar" to "2", "baz" to "3"))).willReturn("ENCODED_DATA")

        val args = arrayOf("GET", "http://url", "foo=1", "bar=2", "baz=3")
        val httpRequest = requestBuilder.build(args).unwrap()

        assertThat(httpRequest.method, equalTo(HttpMethod.GET))
        assertThat(httpRequest.url, equalTo("http://url"))
        assertThat(httpRequest.body, equalTo("ENCODED_DATA"))
    }

    @Test
    fun itUsesPostAsTheDefaultMethodWhenDataFields() {
        given(dataEncoder.encode(mapOf("foo" to "bar"))).willReturn("ENCODED_DATA")
        val httpRequest = requestBuilder.build(arrayOf("http://url", "foo=bar")).unwrap()
        assertThat(httpRequest, equalTo(HttpRequest(HttpMethod.POST, "http://url", "ENCODED_DATA")))
    }
}
