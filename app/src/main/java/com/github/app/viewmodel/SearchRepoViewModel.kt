package com.github.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.app.data.models.SearchRepository
import com.github.app.data.repositories.SearchRepoRepository
import com.github.app.utils.ResponseError
import com.github.app.utils.ResponseSuccess
import kotlinx.coroutines.GlobalScope
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlinx.coroutines.launch

class SearchRepoViewModel : BaseViewModel(), KoinComponent {

    private val searchRepoRepository: SearchRepoRepository by inject()

    private val _repoFetched = MutableLiveData<SearchRepository>()
    val repoFetched: LiveData<SearchRepository> get() = _repoFetched

    fun searchRepositories(query: String, sort: String) {
        GlobalScope.launch {
            when (val response = searchRepoRepository.searchDetails(query, sort)) {
                is ResponseSuccess -> _repoFetched.postValue(response.data)
                is ResponseError -> handleError(response.t)
            }
        }
    }

}