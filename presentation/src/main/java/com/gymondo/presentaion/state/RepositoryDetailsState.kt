package com.gymondo.presentaion.state

import com.gymondo.presentaion.model.RepositoryView

sealed class RepositoryDetailsState {
    object Idle : RepositoryDetailsState() // just I need this one for default state-flow
    object Loading : RepositoryDetailsState()
    data class Success(val data: RepositoryView) : RepositoryDetailsState()
    data class Failure(val failureMessage: String) : RepositoryDetailsState()
}