package io.github.gcapizzi.klient.request

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class FormDataEncoderTest {
    private val formDataEncoder = FormDataEncoder()

    @Test
    fun itEncodesDataToFormSortingByKey() {
        val output = formDataEncoder.encode(mapOf("foo" to "1", "bar" to "2", "baz" to "3"))
        assertThat(output, equalTo("bar=2&baz=3&foo=1"))
    }
}