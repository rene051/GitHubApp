package com.github.app.di

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import com.github.app.utils.dialogs.DialogManager
import com.github.app.utils.dialogs.impl.DialogManagerImpl
import com.github.app.view.ActivityManager
import com.github.app.view.ActivityManagerImpl
import org.koin.dsl.module

val AppModule = module {

    factory { (activity: Activity) -> ActivityManagerImpl(activity) as ActivityManager}

    factory { (activity: FragmentActivity) -> DialogManagerImpl(activity) as DialogManager }

}