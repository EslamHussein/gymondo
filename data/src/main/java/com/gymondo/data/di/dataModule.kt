package com.gymondo.data.di

import com.gymondo.app.domain.GitHubRepository
import com.gymondo.data.mapper.OwnerMapper
import com.gymondo.data.mapper.RepositoryMapper
import com.gymondo.data.mapper.SearchResponseMapper
import com.gymondo.data.repository.GitHubRepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    single { OwnerMapper() }
    single { RepositoryMapper(get()) }
    single { SearchResponseMapper(get()) }
    single { GitHubRepositoryImpl(get(), get(), get()) } bind GitHubRepository::class
}