package com.github.app.utils.network

import okhttp3.Interceptor
import okhttp3.Response

class ResponseInterceptor(private val internetConnectionManager: InternetConnectionManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            if (!internetConnectionManager.hasInternetConnection()) {
                throw InternetConnectionException()
            }

            val response = chain.proceed(chain.request())
            val responseCode = response.code

            if (responseCode != 200) {
                throw NetworkException(response)
            }
            return response
        } catch (e: NetworkException) {
            throw e
        }
    }

}