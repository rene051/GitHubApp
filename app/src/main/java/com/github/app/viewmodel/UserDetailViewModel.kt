package com.github.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.app.data.models.RepoOwner
import com.github.app.data.repositories.UserDetailRepository
import com.github.app.utils.ResponseError
import com.github.app.utils.ResponseSuccess
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class UserDetailViewModel: BaseViewModel(), KoinComponent {

    private val userDetailRepository: UserDetailRepository by inject()

    private val _repoOwnerFetched = MutableLiveData<RepoOwner>()
    val repoOwnerFetched: LiveData<RepoOwner> get() = _repoOwnerFetched

    fun userDetail(url: String) {
        GlobalScope.launch {
            when (val response = userDetailRepository.userDetail(url)) {
                is ResponseSuccess -> _repoOwnerFetched.postValue(response.data)
                is ResponseError -> handleError(response.t)
            }
        }
    }

}