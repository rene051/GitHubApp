package com.github.app.view.holders

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_repository.view.*

class SearchRepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var itemRelativeLayout: RelativeLayout = itemView.itemRepoRelativeLayout
    var userImage: ImageView = itemView.userImage
    var repoName: TextView = itemView.repoName
    var ownerName: TextView = itemView.ownerName
    var repoLanguage: TextView = itemView.repoLanguageNameTxt
    var repoScore: TextView = itemView.repoScoreNameTxt
    var repoDescTxt: TextView = itemView.repoDescTxt
    var watcherCount: TextView = itemView.watcherCount
    var forkCount: TextView = itemView.forkCount
    var issueCount: TextView = itemView.issueCount

}