package com.example.githubuser

import retrofit2.Call
import retrofit2.http.*
//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
interface ApiService {
    @GET("users")
    @Headers("Authorization: token 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")
    fun getUsers(
    ): Call<List<UserResponseItem>>

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUserBy(@Query("q") query: String): Call<UserSearchResponse>

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