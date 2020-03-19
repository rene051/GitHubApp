package com.github.app.utils.network

import okhttp3.Response
import java.io.IOException

class NetworkException(response: Response?) : IOException() {

    private val localResponse: Response? = response

    override var message: String = localResponse?.let { getMessage(localResponse) } ?: "Error has occurred"

    private fun getMessage(response: Response): String? {
        return try {
            "${response.code} ${response.message}"
        } catch (e: Exception) {
            null
        }
    }
}