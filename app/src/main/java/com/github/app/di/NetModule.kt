package com.github.app.di

import com.github.app.data.api.ApiConst.Companion.BASE_URL
import com.github.app.data.api.GitHubApi
import com.github.app.data.api.NetworkApi
import com.github.app.data.api.NetworkApiImpl
import com.github.app.data.repositories.SearchRepoRepository
import com.github.app.data.repositories.impl.SearchRepoRepositoryImpl
import com.github.app.utils.network.InternetConnectionManager
import com.github.app.utils.network.InternetConnectionManagerImpl
import com.github.app.utils.network.ResponseInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val NetModule = module {

    single {
        OkHttpClient.Builder()
            .connectTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .addInterceptor(ResponseInterceptor(get()))
            .build()
    }

    single {
        (Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(GitHubApi::class.java)) as GitHubApi
    }

    single { NetworkApiImpl(get()) as NetworkApi}

    factory { InternetConnectionManagerImpl(get()) as InternetConnectionManager }

    single { SearchRepoRepositoryImpl(get()) as SearchRepoRepository }

}