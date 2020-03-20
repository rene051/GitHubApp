package com.github.app.view.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.github.app.R
import com.github.app.common.AppConst.Companion.DEFAULT_DATE_FORMAT
import com.github.app.common.AppConst.Companion.REPO_OWNER_EXTRA
import com.github.app.common.AppConst.Companion.VISIBLE_DATE_FORMAT
import com.github.app.data.models.RepoOwner
import com.github.app.utils.helpers.convertDate
import com.github.app.utils.helpers.observe
import com.github.app.utils.helpers.openInBrowser
import com.github.app.utils.helpers.showErrorOrText
import com.github.app.view.BaseActivity
import com.github.app.viewmodel.UserDetailViewModel
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var userDetailsViewModel: UserDetailViewModel
    private lateinit var owner: RepoOwner
    private lateinit var browseUserMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        getExtra()
        setAppBar()
        setViewModel()
        initApiCall()
        listeners()
        observeViewModel()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        browseUserMenuItem = menu!!.findItem(R.id.openInBrowser)
        browseUserMenuItem.isVisible = false

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.browser_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.openInBrowser -> owner.htmlUrl?.openInBrowser(this)
        }
        return true
    }

    override fun onRefresh() {
        initApiCall()
    }

    private fun getExtra() {
        owner = intent.extras?.getParcelable(REPO_OWNER_EXTRA)!!
    }

    private fun setAppBar() {
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.title = owner.login
    }

    private fun setViewModel() {
        userDetailsViewModel = ViewModelProvider(this).get(UserDetailViewModel::class.java)
    }

    private fun initApiCall() {
        userDetailProgressBar.show()
        userDetailsNestedScroll.visibility = View.GONE
        userDetailsViewModel.userDetail(owner.url!!)
    }

    private fun listeners() {
        userDetailsSwipeRefresh.setOnRefreshListener(this)
    }

    private fun observeViewModel() {
        userDetailsViewModel.error.observe(this) {
            userDetailsSwipeRefresh.isRefreshing = false
            handleError(it)
        }

        userDetailsViewModel.repoOwnerFetched.observe(this) {
            userDetailsSwipeRefresh.isRefreshing = false
            owner = it!!
            setUserDetailsLayout()
        }
    }

    private fun setUserDetailsLayout() {
        userDetailProgressBar.hide()
        userDetailsNestedScroll.visibility = View.VISIBLE
        browseUserMenuItem.isVisible = true

        Glide.with(this).load(owner.avatarUrl).centerCrop()
            .placeholder(R.drawable.ic_file_download).into(ownerImage)
        fullNameTxt.text = owner.name.showErrorOrText()
        companyTxt.text = owner.company.showErrorOrText()
        typeTxt.text = owner.type.showErrorOrText()
        emailTxt.text = owner.email.showErrorOrText()
        publicRepoTxt.text = owner.publicRepos.toString()
        followersTxt.text = owner.followers.toString()
        createdTxt.text =
            owner.createdAt?.convertDate(DEFAULT_DATE_FORMAT, VISIBLE_DATE_FORMAT).showErrorOrText()
        updateTxt.text =
            owner.updatedAt?.convertDate(DEFAULT_DATE_FORMAT, VISIBLE_DATE_FORMAT).showErrorOrText()
    }
}