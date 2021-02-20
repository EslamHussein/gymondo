package com.gymondo.data.repository

import com.gymondo.app.domain.GitHubRepository
import com.gymondo.app.domain.dto.Repository
import com.gymondo.app.domain.dto.SearchResponse
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
    override suspend fun searchRepositories(
        query: String,
        pageNumber: Int,
        itemsPerPage: Int
    ): Flow<SearchResponse> {
        return gitHubRemoteDataSource.searchRepositories(query, pageNumber, itemsPerPage)
            .map { searchResponseMapper.mapFromEntity(it) }
    }

    override suspend fun getRepoDetails(user: String, repoName: String): Flow<Repository> {
        return gitHubRemoteDataSource.getRepoDetails(user, repoName)
            .map { repositoryMapper.mapFromEntity(it) }
    }
}