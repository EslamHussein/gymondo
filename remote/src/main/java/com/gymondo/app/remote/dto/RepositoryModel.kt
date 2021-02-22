package com.gymondo.app.remote.dto

import com.google.gson.annotations.SerializedName

data class RepositoryModel(val id: Int,
                           val name: String, val owner: OwnerModel, val description: String?,
                           @SerializedName("watchers_count") val watchersCount: Int = 0,
                           @SerializedName("stargazers_count") val stargazersCount: Int = 0,
                           @SerializedName("language") val language: String?,
                           @SerializedName("forks_count") val forksCount: Int = 0,
                           @SerializedName("html_url") val url: String)