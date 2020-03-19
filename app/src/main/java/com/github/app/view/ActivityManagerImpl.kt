package com.github.app.view

import android.app.Activity
import android.content.Intent
import com.github.app.view.activities.RepositoryDetailActivity
import com.github.app.view.activities.UserDetailActivity

class ActivityManagerImpl(private val activity: Activity) : ActivityManager {

    override fun openRepositoryDetailActivity() {
        activity.startActivity(Intent( activity, RepositoryDetailActivity::class.java))
    }

    override fun openUserDetailActivity() {
        activity.startActivity(Intent( activity, UserDetailActivity::class.java))
    }

}