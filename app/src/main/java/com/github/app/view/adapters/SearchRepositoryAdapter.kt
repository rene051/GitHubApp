package com.github.app.view.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.app.R
import com.github.app.common.AppConst.Companion.ITEM_TYPE
import com.github.app.common.AppConst.Companion.LOADING_TYPE
import com.github.app.data.models.SearchRepositoryItems
import com.github.app.utils.helpers.showErrorOrText
import com.github.app.view.holders.LoadingViewHolder
import com.github.app.view.holders.SearchRepositoryViewHolder
import com.github.app.view.listeners.SearchRepositoryClickListener

class SearchRepositoryAdapter(
    private var activity: Activity,
    private var searchRepoList: ArrayList<SearchRepositoryItems>,
    private var searchItemListener: SearchRepositoryClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoadingAdded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE) {
            SearchRepositoryViewHolder(
                LayoutInflater.from(activity).inflate(R.layout.item_repository, parent, false)
            )
        } else {
            LoadingViewHolder(
                LayoutInflater.from(activity).inflate(R.layout.item_loading_progress, parent, false)
            )
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (searchRepoList[position].id != null) {
            ITEM_TYPE
        } else {
            LOADING_TYPE
        }
    }

    override fun getItemCount(): Int {
        return searchRepoList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            ITEM_TYPE -> {
                holder as SearchRepositoryViewHolder
                val item = searchRepoList[position]

                Glide.with(activity).load(item.owner?.avatarUrl).centerCrop()
                    .placeholder(R.drawable.ic_file_download).into(holder.userImage)
                holder.repoName.text = item.name.showErrorOrText()
                holder.ownerName.text = item.owner?.login.showErrorOrText()
                holder.repoLanguage.text = item.language.showErrorOrText()
                holder.repoScore.text = String.format("%.2f", item.score)
                holder.repoDescTxt.text = item.description.showErrorOrText()
                holder.watcherCount.text = item.watchersCount.toString()
                holder.forkCount.text = item.forkCount.toString()
                holder.issueCount.text = item.openIssuesCount.toString()

                holder.userImage.setOnClickListener {
                    searchItemListener.onAvatarClicked(item.owner!!)
                }

                holder.itemRelativeLayout.setOnClickListener {
                    searchItemListener.onRepoClicked(item)
                }
            }
        }

    }

    private fun add() {
        searchRepoList.add(SearchRepositoryItems())
        notifyItemInserted(searchRepoList.size - 1)
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add()
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false

        val position = if (searchRepoList.size > 0) searchRepoList.lastIndex else 0

        searchRepoList.removeAt(position)
        notifyItemRemoved(position)

    }


}