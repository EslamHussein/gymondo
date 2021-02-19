package com.gymondo.app.remote

import com.gymondo.app.remote.core.dto.ApiResult
import com.gymondo.app.remote.dto.RepositoryModel
import com.gymondo.app.remote.dto.SearchResponseModel

interface GitHubRemoteDataSource {
    suspend fun searchRepositories(
        query: String,
        pageNumber: Int,
        itemsPerPage: Int
    ): ApiResult<SearchResponseModel>

    suspend fun getRepoDetails(
        user: String,
        repoName: String
    ): ApiResult<RepositoryModel>
}
