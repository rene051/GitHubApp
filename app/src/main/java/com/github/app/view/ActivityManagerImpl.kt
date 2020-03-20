package com.github.app.view

import android.app.Activity
import android.content.Intent
import com.github.app.common.AppConst.Companion.REPO_EXTRA
import com.github.app.common.AppConst.Companion.REPO_OWNER_EXTRA
import com.github.app.data.models.RepoOwner
import com.github.app.data.models.SearchRepositoryItems
import com.github.app.view.activities.RepositoryDetailActivity
import com.github.app.view.activities.UserDetailActivity

class ActivityManagerImpl(private val activity: Activity) : ActivityManager {

    override fun openRepositoryDetailActivity(repo: SearchRepositoryItems) {
        activity.startActivity(
            Intent(activity, RepositoryDetailActivity::class.java)
                .putExtra(REPO_EXTRA, repo)
        )
    }

    override fun openUserDetailActivity(owner: RepoOwner) {
        activity.startActivity(
            Intent(activity, UserDetailActivity::class.java)
                .putExtra(REPO_OWNER_EXTRA, owner)
        )
    }

}