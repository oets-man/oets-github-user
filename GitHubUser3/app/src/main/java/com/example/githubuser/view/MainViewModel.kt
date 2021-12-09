package com.example.githubuser.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.internet.ApiConfig
import com.example.githubuser.model.UserResponseItem
import com.example.githubuser.model.UserSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val users = MutableLiveData<UserSearchResponse>()
    private val isLoading = MutableLiveData<Boolean>()

    fun showUsersBy(query: String) {
        isLoading.value = true
        val client = ApiConfig.getApiService().getUserBy(query)
        client.enqueue(object : Callback<UserSearchResponse> {
            override fun onResponse(
                call: Call<UserSearchResponse>,
                response: Response<UserSearchResponse>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        users.value = responseBody
                    }
                }
            }

            override fun onFailure(call: Call<UserSearchResponse>, t: Throwable) {
                isLoading.value = false
                Log.e("x", t.message.toString())
            }
        })
    }

    fun getUsers() : LiveData<UserSearchResponse> {
        return users
    }

    fun isLoading() : LiveData<Boolean> {
        return isLoading
    }
}