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
    fun itMakesAnHttpGetRequestAndReturnsTheResults() {
        val response = HttpResponse(404, mapOf(), "body")
        given(httpClient.get("http://example.com")).willReturn(response)
        given(responseFormatter.format(response)).willReturn("the output")

        assertThat(klientApp.run(arrayOf("GET", "http://example.com")), equalTo("the output"))
    }
}
