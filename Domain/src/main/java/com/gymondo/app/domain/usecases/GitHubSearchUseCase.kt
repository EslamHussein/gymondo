package com.gymondo.app.domain.usecases

import com.gymondo.app.domain.GitHubRepository
import com.gymondo.app.domain.UseCase
import com.gymondo.app.domain.dto.SearchResponse
import kotlinx.coroutines.flow.Flow

class GitHubSearchUseCase(private val gitHubRepository: GitHubRepository) :
    UseCase<GitHubSearchUseCase.Params, Flow<SearchResponse>> {

    override suspend fun execute(params: Params): Flow<SearchResponse> {

        return gitHubRepository.searchRepositories(params)
    }

    data class Params(
        val query: String,
        val pageNumber: Int,
        val itemsPerPage: Int
    ) {
        companion object {
            fun create(
                query: String,
                pageNumber: Int,
                itemsPerPage: Int
            ): Params {
                return Params(query, pageNumber, itemsPerPage)
            }
        }
    }
}