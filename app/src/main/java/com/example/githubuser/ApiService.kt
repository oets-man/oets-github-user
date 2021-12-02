package com.example.githubuser

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users")
    @Headers("Authorization: token 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")
    fun getUsers(
    ): Call<List<UserResponseItem>>

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUserBy(@Query("q") query: String): Call<UserSearchResponse>

    //tes
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUserBy2(@Query("q") query: String): Call<List<UserResponseItem>>

    @GET("users/{login}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("login") login: String
    ): Call<UserDetailResponse>

    //    https://api.github.com/users/{wycats}/followers
    @GET("users/{login}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("login") login: String
    ): Call<List<UserResponseItem>>

    //    https://api.github.com/users/{wycats}/following
    @GET("users/{login}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("login") login: String
    ): Call<List<UserResponseItem>>

}