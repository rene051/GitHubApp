package com.github.app.view.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.app.R
import com.github.app.data.models.SearchRepositoryItems
import com.github.app.view.holders.SearchRepositoryViewHolder
import com.github.app.view.listeners.SearchRepositoryClickListener

class SearchRepositoryAdapter(
    private var activity: Activity,
    private var searchRepoList: ArrayList<SearchRepositoryItems>,
    private var searchItemListener: SearchRepositoryClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchRepositoryViewHolder(
            LayoutInflater.from(activity).inflate(R.layout.item_repository, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return searchRepoList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as SearchRepositoryViewHolder
        val item = searchRepoList[position]

        Glide.with(activity).load(item.owner?.avatarUrl).centerCrop().placeholder(R.drawable.ic_file_download).into(holder.userImage)
        holder.repoName.text = item.name
        holder.ownerName.text = item.owner?.login
        holder.repoLanguage.text = item.language
        holder.repoScore.text = String.format("%.2f", item.score)
        holder.repoDescTxt.text = item.description
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