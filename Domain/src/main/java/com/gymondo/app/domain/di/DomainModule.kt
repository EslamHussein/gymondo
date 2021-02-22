package com.gymondo.app.domain.di

import com.gymondo.app.domain.usecases.GetRepositoryDetailsUseCase
import com.gymondo.app.domain.usecases.GitHubSearchUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { GetRepositoryDetailsUseCase(get()) }
    factory { GitHubSearchUseCase(get()) }
}