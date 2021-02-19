package com.gymondo.app.remote.dto

import com.google.gson.annotations.SerializedName

class SearchResponseModel(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("items") val repositories: List<RepositoryModel>
)