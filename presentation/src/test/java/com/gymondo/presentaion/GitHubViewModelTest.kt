package com.gymondo.presentaion

import com.gymondo.app.domain.dto.SearchResponse
import com.gymondo.app.domain.usecases.GetRepositoryDetailsUseCase
import com.gymondo.app.domain.usecases.GitHubSearchUseCase
import com.gymondo.presentaion.error.ErrorHandler
import com.gymondo.presentaion.factory.makeRepository
import com.gymondo.presentaion.factory.makeRepositoryView
import com.gymondo.presentaion.mapper.RepositoryMapper
import com.gymondo.presentaion.mapper.SearchResponseMapper
import com.gymondo.presentaion.model.SearchResponseView
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class GitHubViewModelTest {

    private val gitHubSearchUseCase = mock<GitHubSearchUseCase>()
    private val getRepositoryDetailsUseCase = mock<GetRepositoryDetailsUseCase>()
    private val searchResponseMapper = mock<SearchResponseMapper>()
    private val repositoryMapper = mock<RepositoryMapper>()
    private val errorHandler = mock<ErrorHandler>()
    private val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher()

    lateinit var viewModel: GitHubViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = GitHubViewModel(
            gitHubSearchUseCase,
            getRepositoryDetailsUseCase,
            searchResponseMapper,
            repositoryMapper,
            errorHandler,
            dispatcher
        )
        whenever(searchResponseMapper.mapFromView(any())) doReturn SearchResponse(
            1, listOf(
                makeRepository()
            )
        )
        whenever(searchResponseMapper.mapToView(any())) doReturn SearchResponseView(
            1, listOf(
                makeRepositoryView()
            )
        )
        whenever(repositoryMapper.mapFromView(any())) doReturn makeRepository()

        whenever(repositoryMapper.mapToView(any())) doReturn makeRepositoryView()
    }


    @Test
    fun testSearch() = runBlockingTest {

        whenever(
            gitHubSearchUseCase.execute(
                GitHubSearchUseCase.Params.create(
                    "",
                    1,
                    10,
                    10
                )
            )
        ) doReturn flow {
            emit(SearchResponse(1, listOf(makeRepository())))
        }
        viewModel.search("search", 1)

        verify(viewModel, times(1)).search("search", 1)
    }

}