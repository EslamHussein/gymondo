package com.gymondo.app.domain.dto

class SearchResponse(
    val totalCount: Int,
    val repositories: List<Repository>
)