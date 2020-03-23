package com.github.app.view.activities

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.app.R
import com.github.app.common.AppConst.Companion.FORKS
import com.github.app.common.AppConst.Companion.NO_FILTER
import com.github.app.common.AppConst.Companion.STARS
import com.github.app.common.AppConst.Companion.UPDATED
import com.github.app.data.models.RepoOwner
import com.github.app.data.models.SearchRepository
import com.github.app.data.models.SearchRepositoryItems
import com.github.app.utils.helpers.observe
import com.github.app.utils.network.InternetConnectionManager
import com.github.app.view.ActivityManager
import com.github.app.view.BaseActivity
import com.github.app.view.adapters.SearchRepositoryAdapter
import com.github.app.view.listeners.SearchRepositoryClickListener
import com.github.app.viewmodel.SearchRepoViewModel
import kotlinx.android.synthetic.main.activity_search_repository.*
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import java.util.*
import kotlin.collections.ArrayList

class SearchRepositoryActivity : BaseActivity(), SearchView.OnQueryTextListener,
    AdapterView.OnItemSelectedListener, SearchRepositoryClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    private val activityManager: ActivityManager by inject { parametersOf(this) }
    private val internetConnectionManager: InternetConnectionManager by inject()

    private lateinit var searchRepoViewModel: SearchRepoViewModel
    private lateinit var searchRepoAdapter: SearchRepositoryAdapter
    private lateinit var searchRepoList: ArrayList<SearchRepositoryItems>
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var response: SearchRepository
    private var loading = false
    private var page = 1
    private var searchQuery: String = ""
    private var sortQuery: String = ""
    private val filterOptions = arrayOf(NO_FILTER, STARS, FORKS, UPDATED)

    override fun onResume() {
        super.onResume()

        searchRepositoryRecyclerView.requestFocus()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_repository)

        init()
        setViewModel()
        clickListeners()
        observeViewModel()
        setAdapter()
        pagination()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchView = (menu.findItem(R.id.search).actionView as SearchView)
        searchView.queryHint = getString(R.string.search_repositories)
        searchView.setOnQueryTextListener(this)

        return true
    }

    override fun onRefresh() {
        initSearchRepositories()
    }

    private fun init() {
        searchRepoList = ArrayList()

        searchRepoAdapter = SearchRepositoryAdapter(this, searchRepoList, this)

        filterSpinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, filterOptions)
    }

    private fun setViewModel() {
        searchRepoViewModel = ViewModelProvider(this).get(SearchRepoViewModel::class.java)
    }

    private fun clickListeners() {
        filterSpinner.onItemSelectedListener = this
        searchRepoSwipeToRefresh.setOnRefreshListener(this)
    }

    private fun observeViewModel() {
        searchRepoViewModel.error.observe(this) {
            searchRepoResponseLayout(null)
            handleError(it)
        }

        searchRepoViewModel.repoFetched.observe(this) {
            searchRepoResponseLayout(it)
        }
    }

    private fun setAdapter() {
        mLayoutManager = LinearLayoutManager(this)
        with(searchRepositoryRecyclerView) {
            layoutManager = mLayoutManager
            adapter = searchRepoAdapter
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
        sortQuery = if (position == 0) "" else filterOptions[position].toLowerCase(Locale.ENGLISH)
        initSearchRepositories()
    }

    override fun onQueryTextSubmit(searchQuery: String?): Boolean {
        this.searchQuery = searchQuery!!
        initSearchRepositories()
        hideKeyboard()
        return true
    }

    override fun onQueryTextChange(searchQuery: String?): Boolean {
        if (searchQuery.isNullOrEmpty()) this.searchQuery = ""
        return true
    }

    override fun onAvatarClicked(repoOwner: RepoOwner) {
        activityManager.openUserDetailActivity(repoOwner)
    }

    override fun onRepoClicked(searchRepoItem: SearchRepositoryItems) {
        activityManager.openRepositoryDetailActivity(searchRepoItem)
    }

    private fun searchRepoApiCall() {
        searchRepoViewModel.searchRepositories(searchQuery, sortQuery, page)
    }

    private fun initSearchRepositories() {
        if (searchQuery.isNotEmpty()) {
            page = 1
            setInitLayout()
            searchRepoApiCall()
        } else {
            searchRepoSwipeToRefresh.isRefreshing = false
        }
    }

    private fun setInitLayout() {
        searchRepoList.clear()
        searchRepositoryRecyclerView.recycledViewPool.clear()
        searchRepoAdapter.notifyDataSetChanged()
        loadingProgressBar.show()
        emptyListMessageTxt.visibility = View.GONE
        searchRepositoryRecyclerView.visibility = View.GONE
    }

    private fun searchRepoResponseLayout(repo: SearchRepository?) {
        loadingProgressBar.hide()
        searchRepoSwipeToRefresh.isRefreshing = false
        loading = false

        if(page != 1) searchRepoAdapter.removeLoadingFooter()

        if (!repo?.items.isNullOrEmpty()) {
            response = repo!!
            searchRepoList.addAll(repo.items!!)
            searchRepositoryRecyclerView.visibility = View.VISIBLE
            searchRepoAdapter.notifyDataSetChanged()
        } else {
            emptyListMessageTxt.visibility = View.VISIBLE
        }
    }

    private fun pagination() {
        searchRepositoryRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition()
                val myTotalCount = searchRepoList.size - 10

                if (dy > 0) {
                    if(internetConnectionManager.hasInternetConnection()) {
                        if ((lastVisibleItemPosition >= myTotalCount) && searchRepoList.size != response.totalCount) {
                            if (!loading) {
                                loading = true
                                page += 1
                                searchRepoApiCall()
                                searchRepoAdapter.addLoadingFooter(searchRepositoryRecyclerView)
                            }
                        }
                    }
                }
            }
        })
    }

}
