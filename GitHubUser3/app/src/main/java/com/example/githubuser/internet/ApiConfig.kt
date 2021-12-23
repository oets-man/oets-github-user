package com.example.githubuser.internet

import com.example.githubuser.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request: Request = chain.request()
                    val newRequest: Request = request.newBuilder()
                        .addHeader("Authorization", BuildConfig.API_KEY)
                        .build()

                    chain.proceed(newRequest)
                }
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}


