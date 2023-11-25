package com.example.domain.exceptions

class ServerError(
    val status: String,
    val serverMessage: String,
    val statusCode: Int
) : Exception(serverMessage) {
}