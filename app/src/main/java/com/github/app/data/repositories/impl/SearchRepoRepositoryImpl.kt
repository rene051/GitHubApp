package com.github.app.data.repositories.impl

import com.github.app.data.api.NetworkApi
import com.github.app.data.models.SearchRepository
import com.github.app.data.repositories.SearchRepoRepository
import com.github.app.utils.RequestExecutor
import com.github.app.utils.Response
import com.github.app.utils.ResponseError
import com.github.app.utils.ResponseSuccess

class SearchRepoRepositoryImpl(private val networkApi: NetworkApi) : SearchRepoRepository {

    override suspend fun searchDetails(query: String, sort: String): Response<SearchRepository> {
        return when (val response = RequestExecutor.execute(networkApi.searchRepositoriesAsync(query, sort))) {
            is ResponseSuccess -> ResponseSuccess(response.data.body()!!)
            is ResponseError -> ResponseError(response.t)
        }
    }
}