package io.github.gcapizzi.klient

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.github.gcapizzi.klient.http.HttpClient
import io.github.gcapizzi.klient.http.HttpMethod
import io.github.gcapizzi.klient.http.HttpRequest
import io.github.gcapizzi.klient.http.HttpResponse
import io.github.gcapizzi.klient.request.RequestBuilder
import io.github.gcapizzi.klient.response.ResponseFormatter
import io.github.gcapizzi.klient.util.Result
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

class KlientAppTest {
    private val httpClient = mock(HttpClient::class.java)
    private val requestBuilder = mock(RequestBuilder::class.java)
    private val responseFormatter = mock(ResponseFormatter::class.java)
    private val klientApp = KlientApp(httpClient, requestBuilder, responseFormatter)

    @Test
    fun itMakesAnHttpRequestAndReturnsTheResults() {
        val args = arrayOf("the", "cli", "args")
        val request = HttpRequest(HttpMethod.GET, "http://url")
        val response = HttpResponse(200, mapOf(), "body")

        given(requestBuilder.build(args)).willReturn(Result.Ok(request))
        given(httpClient.call(request)).willReturn(Result.Ok(response))
        given(responseFormatter.format(response)).willReturn("the output")

        val outcome = klientApp.run(args)

        assertThat(outcome.output, equalTo("the output"))
        assertThat(outcome.status, equalTo(0))
    }

    @Test
    fun itReturnsAnErrorWhenInvokedIncorrectly() {
        val args = arrayOf("wrong", "cli", "args")

        given(requestBuilder.build(args)).willReturn(Result.Error(Exception("ERROR!")))

        val outcome = klientApp.run(args)

        assertThat(outcome.output, equalTo("ERROR!"))
        assertThat(outcome.status, equalTo(1))
    }

    @Test
    fun itReturnsAnErrorWhenTheHttpRequestFails() {
        val args = arrayOf("the", "cli", "args")
        val request = HttpRequest(HttpMethod.GET, "http://url")

        given(requestBuilder.build(args)).willReturn(Result.Ok(request))
        given(httpClient.call(request)).willReturn(Result.Error(Exception("ERROR!")))

        val outcome = klientApp.run(args)

        assertThat(outcome.output, equalTo("ERROR!"))
        assertThat(outcome.status, equalTo(1))
    }
}
