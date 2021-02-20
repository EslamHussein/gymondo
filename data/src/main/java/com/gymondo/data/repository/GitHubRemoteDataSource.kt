package com.gymondo.data.repository

import com.gymondo.data.model.RepositoryEntity
import com.gymondo.data.model.SearchResponseEntity
import kotlinx.coroutines.flow.Flow


interface GitHubRemoteDataSource {
    suspend fun searchRepositories(
        query: String,
        pageNumber: Int,
        itemsPerPage: Int
    ): Flow<SearchResponseEntity>

    suspend fun getRepoDetails(
        user: String,
        repoName: String
    ): Flow<RepositoryEntity>
}
