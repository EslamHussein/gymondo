package com.gymondo.presentaion.state

import com.gymondo.presentaion.model.RepositoryView

sealed class RepositoriesListState {
    object Idle : RepositoriesListState()
    object Loading : RepositoriesListState()
    data class Success(val data: List<RepositoryView>) : RepositoriesListState()
    data class Failure(val failureMessage: String) : RepositoriesListState()
}