package com.github.app.data.api

import com.github.app.data.models.SearchRepository
import kotlinx.coroutines.Deferred
import retrofit2.Response

class NetworkApiImpl(private val gitHubApi: GitHubApi) : NetworkApi {

    override fun searchRepositoriesAsync(query: String, sort: String):
            Deferred<Response<SearchRepository>> = gitHubApi.searchRepositoriesAsync(query, sort)

}