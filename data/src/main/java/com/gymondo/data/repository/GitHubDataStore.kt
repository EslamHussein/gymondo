package com.gymondo.data.repository

import com.gymondo.data.model.DataResult
import com.gymondo.data.model.RepositoryEntity

interface GitHubDataStore {

    suspend fun searchRepositories(
        query: String,
        pageNumber: Int,
        itemsPerPage: Int
    ): RepositoryEntity

    suspend fun getRepoDetails(
        user: String,
        repoName: String
    ): DataResult<RepositoryEntity>
}