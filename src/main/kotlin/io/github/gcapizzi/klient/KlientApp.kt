package io.github.gcapizzi.klient

import io.github.gcapizzi.klient.http.HttpClient
import io.github.gcapizzi.klient.requestbuilder.RequestBuilder
import io.github.gcapizzi.klient.responseformatter.ResponseFormatter
import io.github.gcapizzi.klient.util.Result

class KlientApp(val httpClient: HttpClient, val requestBuilder: RequestBuilder, val responseFormatter: ResponseFormatter) {
    fun run(args: Array<String>): ExecutionOutcome {
        val response = requestBuilder.build(args).andThen { request ->
            httpClient.call(request)
        }

        return when (response) {
            is Result.Ok -> {
                val output = responseFormatter.format(response.value)
                ExecutionOutcome(output, 0)
            }
            is Result.Error -> {
                val errorMessage = errorMessage(response.value)
                ExecutionOutcome(errorMessage, 1)
            }
        }
    }

    private fun errorMessage(error: Throwable) = error.message ?: "An error occurred"
}
