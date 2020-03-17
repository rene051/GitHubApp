package com.github.app.di

import android.app.Activity
import com.github.app.view.ActivityManagerImpl
import org.koin.dsl.module

val AppModule = module {

    factory { (activity: Activity) -> ActivityManagerImpl(activity)}

}