package com.gymondo.app.remote

import com.gymondo.app.remote.mapper.RepositoryMapper
import com.gymondo.app.remote.mapper.SearchResponseMapper
import com.gymondo.data.model.DataResult
import com.gymondo.data.model.RepositoryEntity
import com.gymondo.data.model.SearchResponseEntity
import com.gymondo.data.repository.GitHubRemoteDataSource

class GitHubRemoteDataSourceImpl(
    private val gitHubApi: GitHubApi,
    private val searchResponseMapper: SearchResponseMapper,
    private val repositoryMapper: RepositoryMapper
) :
    GitHubRemoteDataSource {
    override suspend fun searchRepositories(
        query: String,
        pageNumber: Int,
        itemsPerPage: Int
    ): DataResult<SearchResponseEntity> {
        return try {
            DataResult.Success(
                searchResponseMapper.mapFromModel(
                    gitHubApi.searchRepositories(
                        query,
                        pageNumber,
                        itemsPerPage
                    )
                )
            )
        } catch (ex: Exception) {
            DataResult.Error(ex)
        }
    }

    override suspend fun getRepoDetails(
        user: String,
        repoName: String
    ): DataResult<RepositoryEntity> {

        return try {
            DataResult.Success(
                repositoryMapper.mapFromModel(
                    gitHubApi.getRepoDetails(
                        user,
                        repoName
                    )
                )
            )
        } catch (ex: Exception) {
            DataResult.Error(ex)
        }
    }
}