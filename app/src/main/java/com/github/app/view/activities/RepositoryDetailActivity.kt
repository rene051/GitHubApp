package com.github.app.view.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.github.app.R
import com.github.app.common.AppConst.Companion.DEFAULT_DATE_FORMAT
import com.github.app.common.AppConst.Companion.REPO_EXTRA
import com.github.app.common.AppConst.Companion.VISIBLE_DATE_FORMAT
import com.github.app.data.models.SearchRepositoryItems
import com.github.app.utils.helpers.convertDate
import com.github.app.utils.helpers.openInBrowser
import com.github.app.utils.helpers.showErrorOrText
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

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(!repo.htmlUrl.isNullOrEmpty()) menu!!.findItem(R.id.openInBrowser).isVisible = true

        return super.onPrepareOptionsMenu(menu)
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
        fullNameTxt.text = repo.fullName.showErrorOrText()
        descriptionTxt.text = repo.description.showErrorOrText()
        languageTxt.text = repo.language.showErrorOrText()
        createDateTxt.text =
            repo.createdAt?.convertDate(DEFAULT_DATE_FORMAT, VISIBLE_DATE_FORMAT).showErrorOrText()
        updateDateTxt.text =
            repo.updatedAt?.convertDate(DEFAULT_DATE_FORMAT, VISIBLE_DATE_FORMAT).showErrorOrText()
        pushedDateTxt.text =
            repo.pushedAt?.convertDate(DEFAULT_DATE_FORMAT, VISIBLE_DATE_FORMAT).showErrorOrText()
        watchersTxt.text = repo.watchersCount.toString()
        forksTxt.text = repo.forkCount.toString()
        issuesTxt.text = repo.openIssuesCount.toString()
    }

    private fun clickListeners() {
        ownerCard.setOnClickListener {
            activityManager.openUserDetailActivity(repo.owner!!)
            finish()
        }
    }

}