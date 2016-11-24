package io.github.gcapizzi.klient.util

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class ResultTest {

    @Test
    fun testMapOnAnOk() {
        assertThat(Result.Ok("foo").map(String::toUpperCase), equalTo(Result.Ok("FOO") as Result<String>))
    }

    @Test
    fun testMapOnAnError() {
        val error = Result.Error<String>(Exception())
        assertThat(error.map(String::toUpperCase), equalTo(error as Result<String>))
    }

    @Test
    fun testAndThenOnAnOk() {
        val parsedIntResult = Result.Ok("123").andThen { s -> parseInt(s) }
        assertThat(parsedIntResult, equalTo(Result.Ok(123) as Result<Int>))
    }

    @Test
    fun testAndThenOnAnError() {
        val error = Result.Error<String>(Exception())
        assertThat(error.andThen { s -> parseInt(s) }, equalTo(error as Result<*>))
    }

    @Test
    fun testUnwrapAnOk() {
        assertThat(Result.Ok("foo").unwrap(), equalTo("foo"))
    }

    @Test(expected = NumberFormatException::class)
    fun testUnwrapAnError() {
        Result.Error<Int>(NumberFormatException()).unwrap()
    }

    private fun parseInt(s: String): Result<Int> {
        try {
            val i = Integer.parseInt(s)
            return Result.Ok(i)
        } catch (e: NumberFormatException) {
            return Result.Error(e)
        }
    }
}