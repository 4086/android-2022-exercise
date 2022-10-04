package com.example.internetaddex

import retrofit2.http.GET
import retrofit2.http.Path

data class Owner(val login: String)
data class Repo(val name: String, val owner: Owner, val url: String)
data class Contributor(val login: String, val contributions: Int)

interface RestApi {
    @GET("users/{user}/repos")
    suspend fun listRepos(@Path("user") user: String): List<Repo>

    @GET("/repos/{owner}/{repo}/contributors")
    suspend fun contributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<Contributor>
}