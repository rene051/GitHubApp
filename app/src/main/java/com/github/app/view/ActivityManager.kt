package com.github.app.view

import com.github.app.data.models.RepoOwner
import com.github.app.data.models.SearchRepositoryItems

interface ActivityManager {

    fun openRepositoryDetailActivity(repo: SearchRepositoryItems)

    fun openUserDetailActivity(owner: RepoOwner)
}