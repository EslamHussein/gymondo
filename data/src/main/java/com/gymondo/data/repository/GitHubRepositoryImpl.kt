package com.gymondo.data.repository

import com.gymondo.app.domain.GitHubRepository
import com.gymondo.app.domain.dto.Repository
import com.gymondo.app.domain.dto.SearchResponse
import com.gymondo.app.domain.usecases.GetRepositoryDetailsUseCase
import com.gymondo.app.domain.usecases.GitHubSearchUseCase
import com.gymondo.data.mapper.RepositoryMapper
import com.gymondo.data.mapper.SearchResponseMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GitHubRepositoryImpl(
    private val gitHubRemoteDataSource: GitHubRemoteDataSource,
    private val searchResponseMapper: SearchResponseMapper,
    private val repositoryMapper: RepositoryMapper
) :
    GitHubRepository {
    override suspend fun searchRepositories(params: GitHubSearchUseCase.Params): Flow<SearchResponse> {
        return gitHubRemoteDataSource.searchRepositories(
            params.query,
            params.nextPage,
            params.itemsPerPage
        ).map { searchResponseMapper.mapFromEntity(it) }
    }

    override suspend fun getRepoDetails(params: GetRepositoryDetailsUseCase.Params): Flow<Repository> {
        return gitHubRemoteDataSource.getRepoDetails(params.user, params.repoName)
            .map { repositoryMapper.mapFromEntity(it) }
    }
}