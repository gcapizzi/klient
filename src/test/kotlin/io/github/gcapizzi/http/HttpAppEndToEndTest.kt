package io.github.gcapizzi.http

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.containsSubstring
import io.github.gcapizzi.http.client.OkHttp3Client
import org.junit.Test

class HttpAppEndToEndTest {
    private val httpClient = OkHttp3Client()
    private val httpApp = HttpApp(httpClient)

    @Test
    fun itMakesAnHttpRequestAndPrintsTheResults() {
        val output = httpApp.run(arrayOf("GET", "https://api.github.com/users/gcapizzi"))
        assertThat(output, containsSubstring("\"login\":\"gcapizzi\""))
    }
}
