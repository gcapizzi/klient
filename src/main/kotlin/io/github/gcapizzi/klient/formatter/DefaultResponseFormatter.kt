package io.github.gcapizzi.klient.formatter

import io.github.gcapizzi.klient.http.HttpResponse

class DefaultResponseFormatter : ResponseFormatter {
    override fun format(response: HttpResponse): String {
        val statusLine = renderStatusLine(response.status)
        val headers = renderHeaders(response.headers)
        val output = "$statusLine\n$headers\n\n${response.body}"
        return output
    }

    private fun renderStatusLine(statusCode: Int): String {
        val reasonPhrase = httpStatuses[statusCode]
        return "HTTP/1.1 $statusCode $reasonPhrase"
    }

    private fun renderHeaders(headers: Map<String, List<String>>): String {
        return headers.map { "${it.key.toLowerCase()}: ${renderHeaderValues(it.value)}" }.joinToString("\n")
    }

    private fun renderHeaderValues(values: List<String>): String {
        return values.joinToString(", ")
    }

    private val httpStatuses = mapOf(
            100 to "Continue",
            101 to "Switching Protocols",
            102 to "Processing",
            103 to "Checkpoint",

            // 2xx Success

            200 to "OK",
            201 to "Created",
            202 to "Accepted",
            203 to "Non-Authoritative Information",
            204 to "No Content",
            205 to "Reset Content",
            206 to "Partial Content",
            207 to "Multi-Status",
            208 to "Already Reported",
            226 to "IM Used",

            // 3xx Redirection

            300 to "Multiple Choices",
            301 to "Moved Permanently",
            302 to "Found",
            303 to "See Other",
            304 to "Not Modified",
            305 to "Use Proxy",
            307 to "Temporary Redirect",
            308 to "Permanent Redirect",

            // 4xx Client Error

            400 to "Bad Request",
            401 to "Unauthorized",
            402 to "Payment Required",
            403 to "Forbidden",
            404 to "Not Found",
            405 to "Method Not Allowed",
            406 to "Not Acceptable",
            407 to "Proxy Authentication Required",
            408 to "Request Timeout",
            409 to "Conflict",
            410 to "Gone",
            411 to "Length Required",
            412 to "Precondition Failed",
            413 to "Payload Too Large",
            414 to "URI Too Long",
            415 to "Unsupported Media Type",
            416 to "Requested range not satisfiable",
            417 to "Expectation Failed",
            418 to "I'm a teapot",
            420 to "Method Failure",
            421 to "Destination Locked",
            422 to "Unprocessable Entity",
            423 to "Locked",
            424 to "Failed Dependency",
            426 to "Upgrade Required",
            428 to "Precondition Required",
            429 to "Too Many Requests",
            431 to "Request Header Fields Too Large",
            451 to "Unavailable For Legal Reasons",

            // 5xx Server Error

            500 to "Internal Server Error",
            501 to "Not Implemented",
            502 to "Bad Gateway",
            503 to "Service Unavailable",
            504 to "Gateway Timeout",
            505 to "HTTP Version not supported",
            506 to "Variant Also Negotiates",
            507 to "Insufficient Storage",
            508 to "Loop Detected",
            509 to "Bandwidth Limit Exceeded",
            510 to "Not Extended",
            511 to "Network Authentication Required"
    )
}