package com.gymondo.presentaion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gymondo.app.domain.usecases.GetRepositoryDetailsUseCase
import com.gymondo.app.domain.usecases.GitHubSearchUseCase
import com.gymondo.presentaion.error.ErrorHandler
import com.gymondo.presentaion.mapper.RepositoryMapper
import com.gymondo.presentaion.mapper.SearchResponseMapper
import com.gymondo.presentaion.model.SearchResponseView
import com.gymondo.presentaion.state.RepositoriesListState
import com.gymondo.presentaion.state.RepositoryDetailsState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GitHubViewModel(
    private val gitHubSearchUseCase: GitHubSearchUseCase,
    private val getRepositoryDetailsUseCase: GetRepositoryDetailsUseCase,
    private val searchResponseMapper: SearchResponseMapper,
    private val repositoryMapper: RepositoryMapper,
    private val errorHandler: ErrorHandler

) : ViewModel() {

    private val repositoriesMutableStateFlow: MutableStateFlow<RepositoriesListState> by lazy {
        MutableStateFlow(RepositoriesListState.Idle)
    }

    private val repositoryDetailsMutableStateFlow: MutableStateFlow<RepositoryDetailsState> by lazy {
        MutableStateFlow(RepositoryDetailsState.Idle)
    }
    val repositoryDetailsStateFlow: StateFlow<RepositoryDetailsState> =
        repositoryDetailsMutableStateFlow

    private var searchResponse: SearchResponseView = SearchResponseView(null, emptyList())
    val repositoriesStateFlow: StateFlow<RepositoriesListState> = repositoriesMutableStateFlow

    private var searchKeyWord: String? = null
    fun search(query: String, nextPage: Int = 1) {
        if (query != searchKeyWord) {
            searchKeyWord = query
            searchResponse = SearchResponseView(null, emptyList())
        }
        viewModelScope.launch {
            gitHubSearchUseCase.execute(
                GitHubSearchUseCase.Params.create(
                    query,
                    searchResponse.totalCount, nextPage,
                    20
                )
            ).onStart {
                repositoriesMutableStateFlow.value = RepositoriesListState.Loading
            }.catch {
                repositoriesMutableStateFlow.value =
                    RepositoriesListState.Failure(errorHandler.getErrorMessage(it))
            }.map {
                SearchResponseView(
                    it.totalCount,
                    searchResponse.repositories.plus(searchResponseMapper.mapToView(it).repositories)
                )
            }.collect {
                searchResponse = it
                repositoriesMutableStateFlow.value = RepositoriesListState.Success(
                    searchResponse.repositories
                )
            }
        }
    }

    fun loadMore(nextPage: Int) {
        searchKeyWord?.let { search(it, nextPage) }
    }

    fun getRepositoryDetails(userName: String, repositoryName: String) {
        viewModelScope.launch {
            getRepositoryDetailsUseCase.execute(
                GetRepositoryDetailsUseCase.Params.create(
                    userName,
                    repositoryName
                )
            ).onStart {
                repositoryDetailsMutableStateFlow.value = RepositoryDetailsState.Loading
            }.catch {
                repositoryDetailsMutableStateFlow.value =
                    RepositoryDetailsState.Failure(errorHandler.getErrorMessage(it))
            }.collect {
                repositoryDetailsMutableStateFlow.value =
                    RepositoryDetailsState.Success(repositoryMapper.mapToView(it))
            }
        }
    }

}