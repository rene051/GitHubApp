package com.github.app.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchRepository(

    @SerializedName("total_count")
    var totalCount: Int? = null,

    @SerializedName("incomplete_results")
    var incompleteResults: Boolean? = false,

    @SerializedName("items")
    var items: ArrayList<SearchRepositoryItems>? = null

) :Parcelable

@Parcelize
data class SearchRepositoryItems(

    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("full_name")
    var fullName: String? = null,

    @SerializedName("owner")
    var owner: RepoOwner? = null,

    @SerializedName("html_url")
    var htmlUrl: String? = null,

    @SerializedName("created_at")
    var createdAt: String? = null,

    @SerializedName("updated_at")
    var updatedAt: String? = null,

    @SerializedName("pushed_at")
    var pushedAt: String? = null,

    @SerializedName("default_branch")
    var defaultBranch: String? = null,

    @SerializedName("size")
    var size: Int? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("watchers_count")
    var watchersCount: Int? = null,

    @SerializedName("language")
    var language: String? = null,

    @SerializedName("forks_count")
    var forkCount: Int? = null,

    @SerializedName("open_issues_count")
    var openIssuesCount: Int? = null,

    @SerializedName("score")
    var score: Double? = null

) : Parcelable

@Parcelize
data class RepoOwner(

    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("login")
    var login: String? = null,

    @SerializedName("avatar_url")
    var avatarUrl: String? = null,

    @SerializedName("url")
    var url: String? = null,

    @SerializedName("type")
    var type: String? = null,

    @SerializedName("html_url")
    var htmlUrl: String? = null,

    @SerializedName("company")
    var company: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("email")
    var email: String? = null,

    @SerializedName("public_repos")
    var publicRepos: Int? = null,

    @SerializedName("followers")
    var followers: Int? = null,

    @SerializedName("created_at")
    var createdAt: String? = null,

    @SerializedName("updated_at")
    var updatedAt: String? = null

) : Parcelable