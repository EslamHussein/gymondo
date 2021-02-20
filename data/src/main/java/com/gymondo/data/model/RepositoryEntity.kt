package com.gymondo.data.model

class RepositoryEntity(
    val id: Int,
    val name: String, val owner: OwnerEntity, val description: String?,
    val watchersCount: Int = 0,
    val stargazersCount: Int = 0,
    val language: String?,
    val forksCount: Int = 0,
    val url: String
)