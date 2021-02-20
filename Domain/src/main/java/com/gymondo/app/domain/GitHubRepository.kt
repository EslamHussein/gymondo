package com.gymondo.app.domain

import com.gymondo.app.domain.dto.Repository
import com.gymondo.app.domain.dto.SearchResponse
import kotlinx.coroutines.flow.Flow

interface GitHubRepository {

    suspend fun searchRepositories(
        query: String,
        pageNumber: Int,
        itemsPerPage: Int
    ): Flow<SearchResponse>

    suspend fun getRepoDetails(
        user: String,
        repoName: String
    ): Flow<Repository>
}