package com.github.app.utils.dialogs.impl

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.github.app.utils.dialogs.DialogManager

class DialogManagerImpl(private val context: Context) : DialogManager {

    override fun openOneButtonDialog(
        buttonTextId: Int,
        title: String,
        message: String,
        cancelable: Boolean,
        onClickOk: (() -> Unit)?
    ) {
        openOneButtonDialogInternal(buttonTextId, title, message, cancelable, onClickOk)
    }

    private fun openOneButtonDialogInternal(
        buttonTextId: Int,
        title: String?,
        message: String,
        cancelable: Boolean,
        onClickOk: (() -> Unit)?
    ) {
        val dialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(context.getString(buttonTextId)) { dialog, _ ->
                dialog.dismiss()
                onClickOk?.invoke()
            }
            .setCancelable(cancelable)
            .create()

        dialog.show()
    }

}