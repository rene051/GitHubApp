package com.github.app.data.api

import com.github.app.data.api.ApiConst.Companion.ACCEPT_HEADER
import com.github.app.data.api.ApiConst.Companion.SEARCH_REPOSITORIES
import com.github.app.data.models.RepoOwner
import com.github.app.data.models.SearchRepository
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

interface GitHubApi {

    @Headers(ACCEPT_HEADER)
    @GET(SEARCH_REPOSITORIES)
    fun searchRepositoriesAsync(@Query("q") query: String, @Query("sort") sort: String, @Query("page") page: Int) : Deferred<Response<SearchRepository>>

    @Headers(ACCEPT_HEADER)
    @GET
    fun getUserAsync(@Url url: String) : Deferred<Response<RepoOwner>>
}