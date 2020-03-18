package com.github.app.data.repositories

import com.github.app.data.models.SearchRepository
import com.github.app.utils.Response

interface SearchRepoRepository {

    suspend fun searchDetails(query: String, sort: String) : Response<SearchRepository>

}