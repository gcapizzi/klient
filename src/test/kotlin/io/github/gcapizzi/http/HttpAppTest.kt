package io.github.gcapizzi.http

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.github.gcapizzi.http.client.HttpClient
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

class HttpAppTest {
    private val httpClient = mock(HttpClient::class.java)
    private val httpApp = HttpApp(httpClient)

    @Test
    fun itReturnsAnErrorWhenCalledWithNoArguments() {
        val output = httpApp.run(arrayOf())
        assertThat(output, equalTo("http: error: too few arguments"))
    }

    @Test
    fun itMakesAnHttpGetRequestAndReturnsTheResults() {
        given(httpClient.get("http://example.com")).willReturn("output")
        val output = httpApp.run(arrayOf("GET", "http://example.com"))
        assertThat(output, equalTo("output"))
    }
}
