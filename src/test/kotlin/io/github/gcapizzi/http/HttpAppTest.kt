package io.github.gcapizzi.http

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.github.gcapizzi.http.client.HttpClient
import io.github.gcapizzi.http.client.HttpResponse
import io.github.gcapizzi.http.formatter.ResponseFormatter
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

class HttpAppTest {
    private val httpClient = mock(HttpClient::class.java)
    private val responseFormatter = mock(ResponseFormatter::class.java)
    private val httpApp = HttpApp(httpClient, responseFormatter)

    @Test
    fun itReturnsAnErrorWhenCalledWithNoArguments() {
        val output = httpApp.run(arrayOf())
        assertThat(output, equalTo("http: error: too few arguments"))
    }

    @Test
    fun itMakesAnHttpGetRequestAndReturnsTheResults() {
        val response = HttpResponse(404, mapOf(), "body")
        given(httpClient.get("http://example.com")).willReturn(response)
        given(responseFormatter.format(response)).willReturn("the output")

        assertThat(httpApp.run(arrayOf("GET", "http://example.com")), equalTo("the output"))
    }
}
