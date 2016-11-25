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
        given(dataEncoder.encode(mapOf("foo" to "1", "bar" to "2", "baz" to "3"))).willReturn("JSON_ENCODED_DATA")

        val args = arrayOf("GET", "http://url", "foo=1", "bar=2", "baz=3")
        val httpRequest = requestBuilder.build(args).unwrap()

        assertThat(httpRequest.method, equalTo(HttpMethod.GET))
        assertThat(httpRequest.url, equalTo("http://url"))
        assertThat(httpRequest.body, equalTo("JSON_ENCODED_DATA"))
    }
}
