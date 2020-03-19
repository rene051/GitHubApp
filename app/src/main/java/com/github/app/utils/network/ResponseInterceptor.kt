package com.github.app.utils.network

import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception

class ResponseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
        val response = chain.proceed(chain.request())
        val responseCode = response.code

            if(responseCode != 200) {
                throw NetworkException(response)
            }
            return response
        } catch (e: NetworkException) {
            throw e
        }
    }
}