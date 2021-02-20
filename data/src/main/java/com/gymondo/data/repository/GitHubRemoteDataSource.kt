package com.gymondo.data.repository

import com.gymondo.data.model.DataResult
import com.gymondo.data.model.RepositoryEntity
import com.gymondo.data.model.SearchResponseEntity


interface GitHubRemoteDataSource {
    suspend fun searchRepositories(
        query: String,
        pageNumber: Int,
        itemsPerPage: Int
    ): DataResult<SearchResponseEntity>

    suspend fun getRepoDetails(
        user: String,
        repoName: String
    ): DataResult<RepositoryEntity>
}
