package com.gymondo.app.remote.di

import com.google.gson.GsonBuilder
import com.gymondo.app.remote.GitHubApi
import com.gymondo.app.remote.GitHubRemoteDataSource
import com.gymondo.app.remote.GitHubRemoteDataSourceImpl
import com.gymondo.app.remote.core.RemoteConfig
import okhttp3.OkHttpClient
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val remoteModule = module {

    single {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(RemoteConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(RemoteConfig.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(RemoteConfig.WRITE_TIMEOUT, TimeUnit.SECONDS)
        builder.build()
    }
    single { GsonBuilder().create() }
    single {
        Retrofit.Builder()
            .baseUrl(RemoteConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
    }
    factory {
        get<Retrofit>().create(GitHubApi::class.java)
    }

    factory { GitHubRemoteDataSourceImpl(get()) } bind GitHubRemoteDataSource::class

}
