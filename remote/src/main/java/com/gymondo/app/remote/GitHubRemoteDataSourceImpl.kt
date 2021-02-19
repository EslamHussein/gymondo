package com.gymondo.app.remote

import com.gymondo.app.remote.core.dto.ApiResult
import com.gymondo.app.remote.dto.RepositoryModel
import com.gymondo.app.remote.dto.SearchResponseModel

class GitHubRemoteDataSourceImpl(private val gitHubApi: GitHubApi) :
    GitHubRemoteDataSource {
    override suspend fun searchRepositories(
        query: String,
        pageNumber: Int,
        itemsPerPage: Int
    ): ApiResult<SearchResponseModel> {
        return try {
            ApiResult.Success(gitHubApi.searchRepositories(query, pageNumber, itemsPerPage))
        } catch (ex: Exception) {
            ApiResult.Error(ex)
        }
    }

    override suspend fun getRepoDetails(
        user: String,
        repoName: String
    ): ApiResult<RepositoryModel> {
        return try {
            ApiResult.Success(gitHubApi.getRepoDetails(user, repoName))
        } catch (ex: Exception) {
            ApiResult.Error(ex)
        }
    }
}