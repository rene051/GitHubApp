package com.github.app.common

interface AppConst {

    companion object {

        //Filter options
        const val NO_FILTER = "No filter"
        const val STARS = "Stars"
        const val FORKS = "Forks"
        const val UPDATED = "Updated"

        //Extras
        const val REPO_EXTRA = "repoExtra"
        const val REPO_OWNER_EXTRA = "repoOwnerExtra"

        //Helpers
        const val NOT_AVAILABLE = "n/a"
        const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss'Z'"
        const val VISIBLE_DATE_FORMAT = "dd MMM yyyy"

        //ViewHolder type
        const val ITEM_TYPE = 0
        const val LOADING_TYPE = 1

        //Error
        const val DEFAULT_ERROR_MESSAGE = "Error has occurred"
        const val CHECK_INTERNET_CONNECTION = "Check your internet connection!"

    }

}