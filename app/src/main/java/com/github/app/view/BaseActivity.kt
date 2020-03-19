package com.github.app.view

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.github.app.R
import com.github.app.utils.dialogs.DialogManager
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.parameter.parametersOf

abstract class BaseActivity : AppCompatActivity(), KoinComponent {

    private var dialogManager: DialogManager? = null

    protected open fun showError(error: String) {
        getDialogManager().openOneButtonDialog(
            R.string.ok,
            getString(R.string.error_has_occurred),
            error,
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