package io.github.gcapizzi.klient

import io.github.gcapizzi.klient.http.HttpClient
import io.github.gcapizzi.klient.requestbuilder.RequestBuilder
import io.github.gcapizzi.klient.responseformatter.ResponseFormatter

class KlientApp(val httpClient: HttpClient, val requestBuilder: RequestBuilder, val responseFormatter: ResponseFormatter) {
    fun run(args: Array<String>): String {
        if (args.isEmpty()) {
            return "klient: error: too few arguments"
        }

        val request = requestBuilder.build(args)
        val response = httpClient.call(request)!!
        return responseFormatter.format(response)
    }
}
