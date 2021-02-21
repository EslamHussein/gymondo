package com.gymondo.presentaion.di

import com.gymondo.presentaion.GitHubViewModel
import com.gymondo.presentaion.error.DefaultErrorHandler
import com.gymondo.presentaion.error.ErrorHandler
import com.gymondo.presentaion.mapper.OwnerMapper
import com.gymondo.presentaion.mapper.RepositoryMapper
import com.gymondo.presentaion.mapper.SearchResponseMapper
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module


val presentationModule = module {
    single { OwnerMapper() }
    single { RepositoryMapper(get()) }
    single { SearchResponseMapper(get()) }
    viewModel {
        GitHubViewModel(get(), get(), get(), get(), get())
    }
    single { DefaultErrorHandler(androidContext().resources) } bind ErrorHandler::class
}