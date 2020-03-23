package com.github.app.data.api

import com.github.app.data.models.RepoOwner
import com.github.app.data.models.SearchRepository
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface NetworkApi {

    fun searchRepositoriesAsync(query: String, sort: String, page: Int): Deferred<Response<SearchRepository>>

    fun getUserAsync(url: String): Deferred<Response<RepoOwner>>

}