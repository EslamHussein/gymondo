package com.gymondo.presentaion.model

data class RepositoryView(
    val id: Int,
    val name: String, val owner: OwnerView, val description: String?,
    val watchersCount: Int = 0,
    val stargazersCount: Int = 0,
    val language: String?,
    val forksCount: Int = 0,
    val url: String
)