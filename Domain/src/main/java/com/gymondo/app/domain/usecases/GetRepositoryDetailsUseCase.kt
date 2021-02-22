package com.gymondo.app.domain.usecases

import com.gymondo.app.domain.GitHubRepository
import com.gymondo.app.domain.UseCase
import com.gymondo.app.domain.dto.Repository
import kotlinx.coroutines.flow.Flow

open class GetRepositoryDetailsUseCase(private val gitHubRepository: GitHubRepository) :
    UseCase<GetRepositoryDetailsUseCase.Params, Flow<Repository>> {

    override suspend fun execute(params: Params): Flow<Repository> {
        return gitHubRepository.getRepoDetails(params)
    }

    data class Params(
        val user: String,
        val repoName: String
    ) {
        companion object {
            fun create(
                user: String,
                repoName: String
            ): Params {
                return Params(user, repoName)
            }
        }
    }
}