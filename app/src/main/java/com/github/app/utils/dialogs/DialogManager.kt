package com.github.app.utils.dialogs

import com.github.app.R

interface DialogManager {

    fun openOneButtonDialog(
        buttonTextId: Int = R.string.ok,
        title: String,
        message: String,
        cancelable: Boolean = false,
        onClickOk: (() -> Unit)? = null
    )
}