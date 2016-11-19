package io.github.gcapizzi.klient

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.github.gcapizzi.klient.http.HttpClient
import io.github.gcapizzi.klient.http.HttpResponse
import io.github.gcapizzi.klient.formatter.ResponseFormatter
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

class KlientAppTest {
    private val httpClient = mock(HttpClient::class.java)
    private val responseFormatter = mock(ResponseFormatter::class.java)
    private val klientApp = KlientApp(httpClient, responseFormatter)

    @Test
    fun itReturnsAnErrorWhenCalledWithNoArguments() {
        val output = klientApp.run(arrayOf())
        assertThat(output, equalTo("klient: error: too few arguments"))
    }

    @Test
    fun itMakesAnHttpRequestAndReturnsTheResults() {
        val response = HttpResponse(200, mapOf(), "body")
        given(httpClient.call(HttpRequest("METHOD", "http://url"))).willReturn(response)
        given(responseFormatter.format(response)).willReturn("the output")

        assertThat(klientApp.run(arrayOf("METHOD", "http://url")), equalTo("the output"))
    }

    @Test
    fun itAllowsToSpecifyDataFields() {
        val response = HttpResponse(200, mapOf(), "body")
        val expectedBody = mapOf("foo" to "1", "bar" to "2", "baz" to "3")
        given(httpClient.call(HttpRequest("METHOD", "http://url", body = expectedBody))).willReturn(response)
        given(responseFormatter.format(response)).willReturn("the output")

        val output = klientApp.run(arrayOf("METHOD", "http://url", "foo=1", "bar=2", "baz=3"))

        assertThat(output, equalTo("the output"))
    }
}
