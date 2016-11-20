package io.github.gcapizzi.klient.http

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.github.gcapizzi.klient.util.Result
import org.junit.Test

class OkHttp3ClientTest {
    private val okHttpClient = OkHttp3Client()

    @Test
    fun itExecutesAGetRequest() {
        val response = okHttpClient.call(HttpRequest("GET", "http://httpbin.org/get")).unwrap()

        assertThat(response.status, equalTo(200))
        assertThat(response.headers["content-type"]?.first(), equalTo("application/json"))
        val parsedOutput = parseJson(response.body)
        assertThat(parsedOutput["url"] as String, equalTo("http://httpbin.org/get"))
    }

    @Test
    fun itUsesHttpAsTheDefaultSchema() {
        val response = okHttpClient.call(HttpRequest("GET", "httpbin.org/get")).unwrap()
        val parsedOutput = parseJson(response.body)
        assertThat(parsedOutput["url"] as String, equalTo("http://httpbin.org/get"))
    }

    @Test
    fun itSupportsAddressesStartingWithADoubleSlash() {
        val response = okHttpClient.call(HttpRequest("GET", "//httpbin.org/get")).unwrap()
        val parsedOutput = parseJson(response.body)
        assertThat(parsedOutput["url"] as String, equalTo("http://httpbin.org/get"))
    }

    @Test
    fun itReturnsAnErrorIfTheUrlIsInvalid() {
        val error = okHttpClient.call(HttpRequest("GET", "foo:bar.com")) as? Result.Error
        assertThat(error!!.value.message, equalTo("Invalid URL"))
    }

    @Test
    fun itReturnsAnErrorIfTheRequestFails() {
        val error = okHttpClient.call(HttpRequest("GET", "foo")) as? Result.Error
        assertThat(error!!.value.message, equalTo("Error executing the HTTP request: foo: unknown error"))
    }

    @Test
    fun itExecutesAPostRequest() {
        val response = okHttpClient.call(HttpRequest("POST", "http://httpbin.org/post", mapOf("foo" to "bar"))).unwrap()

        assertThat(response.status, equalTo(200))
        assertThat(response.headers["content-type"]?.first(), equalTo("application/json"))
        val parsedOutput = parseJson(response.body)
        assertThat(parsedOutput["url"] as String, equalTo("http://httpbin.org/post"))
        val json = parsedOutput["json"] as JsonObject
        assertThat(json["foo"] as String, equalTo("bar"))
    }

    private fun parseJson(jsonString: String): JsonObject {
        return Parser().parse(jsonString.byteInputStream()) as JsonObject
    }
}
