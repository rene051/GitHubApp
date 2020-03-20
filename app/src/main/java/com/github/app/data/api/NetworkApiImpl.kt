package com.github.app.data.api

import com.github.app.data.models.RepoOwner
import com.github.app.data.models.SearchRepository
import kotlinx.coroutines.Deferred
import retrofit2.Response

class NetworkApiImpl(private val gitHubApi: GitHubApi) : NetworkApi {

    override fun searchRepositoriesAsync(query: String, sort: String, page: Int):
            Deferred<Response<SearchRepository>> = gitHubApi.searchRepositoriesAsync(query, sort, page)

    override fun getUserAsync(url: String):
            Deferred<Response<RepoOwner>> = gitHubApi.getUserAsync(url)

}