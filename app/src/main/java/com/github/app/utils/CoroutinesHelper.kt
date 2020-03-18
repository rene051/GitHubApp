package com.github.app.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.Deferred

fun <T> LiveData<T>.observe(owner: LifecycleOwner, f: (T?) -> Unit) {
    observe(owner, Observer {
        f(it)
    })
}

object RequestExecutor {

    suspend fun <T> execute(
        request: Deferred<T>,
        onSuccess: (T) -> Unit,
        onError: ((Throwable) -> Unit)? = null
    ) {
        try {
            val response = request.await()
            onSuccess.invoke(response)
        } catch (e: Exception) {
            onError?.invoke(e)
        }
    }

    suspend fun <T> execute(request: Deferred<T>): Response<T> {
        return try {
            val response = request.await()
            ResponseSuccess(response)
        } catch (e: Exception) {
            ResponseError(e)
        }
    }
}