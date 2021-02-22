package com.gymondo.app.domain.usecases

import app.cash.turbine.test
import com.gymondo.app.domain.GitHubRepository
import com.gymondo.app.domain.usecases.factory.makeRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.time.ExperimentalTime

class GetRepositoryDetailsUseCaseTest {
    private val gitHubRepository = mock<GitHubRepository>()
    private val getRepositoryDetailsUseCase: GetRepositoryDetailsUseCase =
        GetRepositoryDetailsUseCase(gitHubRepository)

    @ExperimentalCoroutinesApi
    private val coroutineTest = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @ExperimentalTime
    @Test
    fun `test get repo details with success`() = coroutineTest.runBlockingTest {
        whenever(
            gitHubRepository.getRepoDetails(any())
        ) doReturn flow {
            emit(makeRepository())
        }

        getRepositoryDetailsUseCase.execute(
            GetRepositoryDetailsUseCase.Params.create("any", "any")
        ).test {
            expectItem()
            expectComplete()
        }
    }

    @ExperimentalCoroutinesApi
    @ExperimentalTime
    @Test
    fun `test get repo details with error`() = coroutineTest.runBlockingTest {
        whenever(
            gitHubRepository.searchRepositories(any())
        ) doReturn flow {
            throw Exception()
        }

        getRepositoryDetailsUseCase.execute(
            GetRepositoryDetailsUseCase.Params.create("any", "any")
        ).test {
            expectError()
        }
    }
}