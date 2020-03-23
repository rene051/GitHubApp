package com.github.app.view.listeners

import com.github.app.data.models.RepoOwner
import com.github.app.data.models.SearchRepositoryItems

interface SearchRepositoryClickListener {

    fun onAvatarClicked(repoOwner: RepoOwner)

    fun onRepoClicked(searchRepoItem: SearchRepositoryItems)
}