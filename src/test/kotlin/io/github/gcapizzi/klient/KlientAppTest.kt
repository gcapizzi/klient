package io.github.gcapizzi.klient

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.github.gcapizzi.klient.http.HttpClient
import io.github.gcapizzi.klient.http.HttpResponse
import io.github.gcapizzi.klient.requestbuilder.RequestBuilder
import io.github.gcapizzi.klient.responseformatter.ResponseFormatter
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

class KlientAppTest {
    private val httpClient = mock(HttpClient::class.java)
    private val requestBuilder = mock(RequestBuilder::class.java)
    private val responseFormatter = mock(ResponseFormatter::class.java)
    private val klientApp = KlientApp(httpClient, requestBuilder, responseFormatter)

    @Test
    fun itReturnsAnErrorWhenCalledWithNoArguments() {
        val output = klientApp.run(arrayOf())
        assertThat(output, equalTo("klient: error: too few arguments"))
    }

    @Test
    fun itMakesAnHttpRequestAndReturnsTheResults() {
        val args = arrayOf("the", "cli", "args")
        val request = HttpRequest("METHOD", "http://url")
        val response = HttpResponse(200, mapOf(), "body")

        given(requestBuilder.build(args)).willReturn(request)
        given(httpClient.call(request)).willReturn(response)
        given(responseFormatter.format(response)).willReturn("the output")

        assertThat(klientApp.run(args), equalTo("the output"))
    }
}
