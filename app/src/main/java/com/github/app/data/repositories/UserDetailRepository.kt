package com.github.app.data.repositories

import com.github.app.data.models.RepoOwner
import com.github.app.utils.Response

interface UserDetailRepository {

    suspend fun userDetail(url: String) : Response<RepoOwner>

}