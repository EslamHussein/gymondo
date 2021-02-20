package com.gymondo.app.remote

import com.gymondo.app.remote.dto.RepositoryModel
import com.gymondo.app.remote.dto.SearchResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

    @GET("search/repositoriess")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): SearchResponseModel

    @GET("repos/{user}/{repo_name}")
    suspend fun getRepoDetails(
        @Path("user") user: String,
        @Path("repo_name") repoName: String
    ): RepositoryModel

}