package com.gymondo.data.repository

import app.cash.turbine.test
import com.gymondo.app.domain.dto.SearchResponse
import com.gymondo.app.domain.usecases.GetRepositoryDetailsUseCase
import com.gymondo.app.domain.usecases.GitHubSearchUseCase
import com.gymondo.data.mapper.RepositoryMapper
import com.gymondo.data.mapper.SearchResponseMapper
import com.gymondo.data.model.SearchResponseEntity
import com.gymondo.data.test.factory.makeRepository
import com.gymondo.data.test.factory.makeRepositoryEntity
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlin.time.ExperimentalTime

class GitHubRepositoryImplTest {

    private val gitHubRemoteDataSource = mock<GitHubRemoteDataSource>()
    private val searchResponseMapper = mock<SearchResponseMapper>()
    private val repositoryMapper = mock<RepositoryMapper>()

    private val repository =
        GitHubRepositoryImpl(gitHubRemoteDataSource, searchResponseMapper, repositoryMapper)

    private val coroutineTest = TestCoroutineDispatcher()

    @Before
    fun setup() {
        whenever(repositoryMapper.mapFromEntity(any())) doReturn makeRepository()
        whenever(repositoryMapper.mapToEntity(any())) doReturn makeRepositoryEntity()

        whenever(searchResponseMapper.mapToEntity(any())) doReturn SearchResponseEntity(
            1, listOf(makeRepositoryEntity())
        )

        whenever(searchResponseMapper.mapFromEntity(any())) doReturn SearchResponse(
            1, listOf(makeRepository())
        )

    }

    @ExperimentalTime
    @Test
    fun `test search repos with success`() = coroutineTest.runBlockingTest {
        whenever(
            gitHubRemoteDataSource.searchRepositories(
                any(),
                any(),
                any()
            )
        ) doReturn flow {
            emit(
                SearchResponseEntity(
                    1, listOf(makeRepositoryEntity())
                )
            )
        }

        repository.searchRepositories(
            GitHubSearchUseCase.Params.create("any", 1, 1, 1)
        ).test {
            expectItem()
            expectComplete()
        }
    }


    @ExperimentalTime
    @Test
    fun `test search repos with exception`() = coroutineTest.runBlockingTest {
        whenever(
            gitHubRemoteDataSource.searchRepositories(
                any(),
                any(),
                any()
            )
        ) doReturn flow {
            throw IOException()
        }

        repository.searchRepositories(
            GitHubSearchUseCase.Params.create("any", 1, 1, 1)
        ).test {
            expectError()
        }
    }


    @ExperimentalTime
    @Test
    fun `test get repo details with success`() = coroutineTest.runBlockingTest {
        whenever(
            gitHubRemoteDataSource.getRepoDetails(
                any(),
                any()
            )
        ) doReturn flow {
            emit(makeRepositoryEntity())
        }

        repository.getRepoDetails(
            GetRepositoryDetailsUseCase.Params.create("any", "any")
        ).test {
            expectItem()
            expectComplete()
        }
    }


    @ExperimentalTime
    @Test
    fun `test get repo details with exception`() = coroutineTest.runBlockingTest {
        whenever(
            gitHubRemoteDataSource.getRepoDetails(
                any(),
                any()
            )
        ) doReturn flow {
            throw IOException()
        }

        repository.getRepoDetails(
            GetRepositoryDetailsUseCase.Params.create("any", "any")
        ).test {
            expectError()
        }
    }
}