package com.github.app.view.activities

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.app.R
import com.github.app.data.models.SearchRepositoryItems
import com.github.app.utils.observe
import com.github.app.view.ActivityManager
import com.github.app.view.BaseActivity
import com.github.app.view.adapters.SearchRepositoryAdapter
import com.github.app.viewmodel.SearchRepoViewModel
import kotlinx.android.synthetic.main.activity_search_repository.*
import org.koin.core.inject
import org.koin.core.parameter.parametersOf


class SearchRepositoryActivity : BaseActivity(), SearchView.OnQueryTextListener {

    private val activityManager: ActivityManager by inject { parametersOf(this) }

    private lateinit var searchRepoViewModel: SearchRepoViewModel
    private lateinit var searchRepoAdapter: SearchRepositoryAdapter
    private lateinit var searchRepoList: ArrayList<SearchRepositoryItems>
    private var searchQuery: String = ""
    private var sortQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_repository)

        init()
        setViewModel()
        observeViewModel()
        setAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchView = (menu.findItem(R.id.search).actionView as SearchView)
        searchView.queryHint = getString(R.string.search_repositories)
        searchView.setOnQueryTextListener(this)

        return true
    }

    private fun init() {
        searchRepoList = ArrayList()
        searchRepoAdapter = SearchRepositoryAdapter(this, searchRepoList)
    }

    private fun setViewModel() {
        searchRepoViewModel = ViewModelProvider(this).get(SearchRepoViewModel::class.java)
    }

    private fun observeViewModel() {
        searchRepoViewModel.repoFetched.observe(this) {
            loadingProgressBar.hide()

            if(!it!!.items.isNullOrEmpty()) {
                with(searchRepoList) {
                    clear()
                    addAll(it.items!!)
                }
                searchRepositoryRecyclerView.visibility = View.VISIBLE
                searchRepoAdapter.notifyDataSetChanged()
            } else {
                emptyListMessageTxt.visibility = View.VISIBLE
            }
        }
    }

    private fun setAdapter() {
        with(searchRepositoryRecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = searchRepoAdapter
        }
    }

    override fun onQueryTextSubmit(searchQuery: String?): Boolean {
        this.searchQuery = searchQuery!!
        searchRepositories()
        hideKeyboard()
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return false
    }

    private fun searchRepositories() {
        searchRepoViewModel.searchRepositories(searchQuery, sortQuery)
        loadingProgressBar.show()
        emptyListMessageTxt.visibility = View.GONE
        searchRepositoryRecyclerView.visibility = View.GONE
    }

}
