package com.github.app.data.repositories.impl

import com.github.app.data.api.NetworkApi
import com.github.app.data.models.RepoOwner
import com.github.app.data.repositories.UserDetailRepository
import com.github.app.utils.Response
import com.github.app.utils.ResponseError
import com.github.app.utils.ResponseSuccess
import com.github.app.utils.helpers.RequestExecutor

class UserDetailsRepositoryImpl(private val networkApi: NetworkApi) : UserDetailRepository {
    override suspend fun userDetail(url: String): Response<RepoOwner> {
        return when (val response = RequestExecutor.execute(networkApi.getUserAsync(url))) {
            is ResponseSuccess -> ResponseSuccess(response.data.body()!!)
            is ResponseError -> ResponseError(response.t)
        }
    }


}