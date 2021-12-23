package com.example.githubuser.internet

import com.example.githubuser.BuildConfig
import com.example.githubuser.model.UserDetailResponse
import com.example.githubuser.model.UserResponseItem
import com.example.githubuser.model.UserSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getUserBy(@Query("q") query: String): Call<UserSearchResponse>

    @GET("users/{login}")
    fun getUserDetail(@Path("login") login: String): Call<UserDetailResponse>

    @GET("users/{login}/followers")
    fun getFollowers(
        @Path("login") login: String
    ): Call<ArrayList<UserResponseItem>>

    @GET("users/{login}/following")
    fun getFollowing(
        @Path("login") login: String
    ): Call<ArrayList<UserResponseItem>>

}