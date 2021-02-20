package com.gymondo.presentaion.state

sealed class ResourceState {
    object Success : ResourceState()
    object Error : ResourceState()
    object Loading : ResourceState()
    object Ideal : ResourceState()
}