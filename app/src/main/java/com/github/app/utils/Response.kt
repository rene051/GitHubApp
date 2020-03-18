package com.github.app.utils

sealed class Response<T>

data class ResponseSuccess<T>(val data: T) : Response<T>()

data class ResponseError<T>(val t: Throwable) : Response<T>()