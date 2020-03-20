package com.github.app.view.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import com.bumptech.glide.Glide
import com.github.app.R
import com.github.app.common.AppConst.Companion.REPO_EXTRA
import com.github.app.data.models.SearchRepositoryItems
import com.github.app.utils.helpers.convertDate
import com.github.app.utils.helpers.openInBrowser
import com.github.app.view.ActivityManager
import com.github.app.view.BaseActivity
import kotlinx.android.synthetic.main.activity_repository_detail.*
import org.koin.core.inject
import org.koin.core.parameter.parametersOf


class RepositoryDetailActivity : BaseActivity() {

    private val activityManager: ActivityManager by inject { parametersOf(this) }

    private lateinit var repo: SearchRepositoryItems

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_detail)

        getExtras()
        setAppBar()
        setOwnerCardLayout()
        setRepoInfoLayout()
        clickListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.browser_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.openInBrowser -> repo.htmlUrl?.openInBrowser(this)
        }
        return true
    }

    private fun getExtras() {
        repo = intent.extras?.getParcelable(REPO_EXTRA)!!
    }

    private fun setAppBar() {
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.title = repo.name
    }

    private fun setOwnerCardLayout() {
        Glide.with(this).load(repo.owner?.avatarUrl).centerCrop()
            .placeholder(R.drawable.ic_file_download).into(ownerImage)
        ownerName.text = repo.owner?.login
        ownerTypeText.text = repo.owner?.type
    }

    private fun setRepoInfoLayout() {
        fullNameTxt.text = repo.fullName
        descriptionTxt.text = repo.description
        languageTxt.text = repo.language
        createDateTxt.text = repo.createdAt?.convertDate("yyyy-MM-dd'T'hh:mm:ss'Z'", "dd MMM yyyy")
        updateDateTxt.text = repo.updatedAt?.convertDate("yyyy-MM-dd'T'hh:mm:ss'Z'", "dd MMM yyyy")
        pushedDateTxt.text = repo.pushedAt?.convertDate("yyyy-MM-dd'T'hh:mm:ss'Z'", "dd MMM yyyy")
        watchersTxt.text = repo.watchersCount.toString()
        forksTxt.text = repo.forkCount.toString()
        issuesTxt.text = repo.openIssuesCount.toString()
    }

    private fun clickListeners() {
        ownerCard.setOnClickListener {
            activityManager.openUserDetailActivity()
            finish()
        }
    }

}