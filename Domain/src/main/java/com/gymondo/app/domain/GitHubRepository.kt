package com.gymondo.app.domain

import com.gymondo.app.domain.dto.Repository
import com.gymondo.app.domain.dto.SearchResponse
import com.gymondo.app.domain.usecases.GetRepositoryDetailsUseCase
import com.gymondo.app.domain.usecases.GitHubSearchUseCase
import kotlinx.coroutines.flow.Flow

interface GitHubRepository {

    suspend fun searchRepositories(
        params: GitHubSearchUseCase.Params
    ): Flow<SearchResponse>

    suspend fun getRepoDetails(
        params: GetRepositoryDetailsUseCase.Params
    ): Flow<Repository>
}