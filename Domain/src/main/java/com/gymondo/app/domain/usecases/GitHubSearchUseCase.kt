package com.gymondo.app.domain.usecases

import com.gymondo.app.domain.GitHubRepository
import com.gymondo.app.domain.UseCase
import com.gymondo.app.domain.dto.SearchResponse
import com.gymondo.app.domain.error.NoMoreDataFoundException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GitHubSearchUseCase(private val gitHubRepository: GitHubRepository) :
    UseCase<GitHubSearchUseCase.Params, Flow<SearchResponse>> {

    override suspend fun execute(params: Params): Flow<SearchResponse> {
        return if (params.totalNumberOfPages != null && params.nextPage > params.totalNumberOfPages)
            flow { throw NoMoreDataFoundException() }
        else {
            gitHubRepository.searchRepositories(params)
        }
    }

    data class Params(
        val query: String,
        val totalNumberOfPages: Int? = null,
        val nextPage: Int,
        val itemsPerPage: Int
    ) {
        companion object {
            fun create(
                query: String,
                totalItemsCounter: Int? = null,
                nextPage: Int = 1,
                itemsPerPage: Int
            ): Params {
                val totalNumberOfPages = if (totalItemsCounter == null)
                    null
                else
                    (totalItemsCounter / itemsPerPage) + 1
                return Params(query, totalNumberOfPages, nextPage, itemsPerPage)
            }
        }
    }
}