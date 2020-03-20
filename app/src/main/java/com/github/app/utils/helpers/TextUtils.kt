package com.github.app.utils.helpers

import android.content.Context
import android.content.Intent
import android.net.Uri
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun String.convertDate(oldDateFormat: String, newDateFormat: String) : String {
    val form = SimpleDateFormat(oldDateFormat, Locale.forLanguageTag("en"))
    var date: Date? = null
    try {
        date = form.parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val postFormat = SimpleDateFormat(newDateFormat, Locale.forLanguageTag("en"))
    return postFormat.format(date!!)
}

fun String.openInBrowser(context: Context) {
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(this)))
}
