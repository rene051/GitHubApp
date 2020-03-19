package com.github.app.data.api

interface ApiConst {

    companion object {
        const val BASE_URL = "https://api.github.com"

        const val ACCEPT_HEADER = "Accept: application/vnd.github.mercy-preview+json"

        const val SEARCH_REPOSITORIES = "/search/repositories"
    }
}