package com.github.app.view

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.github.app.R
import com.github.app.common.AppConst.Companion.CHECK_INTERNET_CONNECTION
import com.github.app.common.AppConst.Companion.DEFAULT_ERROR_MESSAGE
import com.github.app.utils.dialogs.DialogManager
import com.github.app.utils.network.InternetConnectionException
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.parameter.parametersOf

abstract class BaseActivity : AppCompatActivity(), KoinComponent {

    private var dialogManager: DialogManager? = null

    protected open fun handleError(throwable: Throwable?) {
        if(throwable is InternetConnectionException) {
            showError(CHECK_INTERNET_CONNECTION)
        } else {
            showError(throwable?.message!!)
        }

    }

    private fun showError(errorMessage: String) {
        getDialogManager().openOneButtonDialog(
            R.string.ok,
            DEFAULT_ERROR_MESSAGE,
            errorMessage,
            true
        )
    }

    fun hideKeyboard() {
        val imm: InputMethodManager =
            this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = this.currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun getDialogManager(): DialogManager {
        if (dialogManager == null) {
            dialogManager = get { parametersOf(this) }
        }

        return dialogManager!!
    }

}