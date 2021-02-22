package com.gymondo.app.remote

import com.gymondo.app.remote.mapper.RepositoryMapper
import com.gymondo.app.remote.mapper.SearchResponseMapper
import com.gymondo.data.model.RepositoryEntity
import com.gymondo.data.model.SearchResponseEntity
import com.gymondo.data.repository.GitHubRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GitHubRemoteDataSourceImpl(
    private val gitHubApi: GitHubApi,
    private val searchResponseMapper: SearchResponseMapper,
    private val repositoryMapper: RepositoryMapper
) : GitHubRemoteDataSource {

    override suspend fun searchRepositories(
        query: String,
        pageNumber: Int,
        itemsPerPage: Int
    ): Flow<SearchResponseEntity> {
        return flow {
            emit(
                searchResponseMapper.mapFromModel(
                    gitHubApi.searchRepositories(
                        query,
                        pageNumber,
                        itemsPerPage
                    )
                )
            )

        }
    }

    override suspend fun getRepoDetails(
        user: String,
        repoName: String
    ): Flow<RepositoryEntity> {
        return flow {
            emit(
                repositoryMapper.mapFromModel(
                    gitHubApi.getRepoDetails(
                        user,
                        repoName
                    )
                )
            )
        }
    }
}