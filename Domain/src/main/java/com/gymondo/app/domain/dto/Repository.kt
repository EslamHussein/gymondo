package com.gymondo.app.domain.dto

class Repository(
    val id: Int,
    val name: String, val owner: Owner, val description: String?,
    val watchersCount: Int = 0,
    val stargazersCount: Int = 0,
    val language: String?,
    val forksCount: Int = 0,
    val url: String
)