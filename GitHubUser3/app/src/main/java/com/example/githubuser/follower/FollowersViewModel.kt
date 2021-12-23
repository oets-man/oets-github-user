package com.example.githubuser.follower

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.internet.ApiConfig
import com.example.githubuser.model.UserResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    private val followers = MutableLiveData<ArrayList<UserResponseItem>>()
    private val isLoading = MutableLiveData<Boolean>()

    fun loadFollowers(login: String?) {
        isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(login ?: "")
        client.enqueue(object : Callback<ArrayList<UserResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<UserResponseItem>>,
                response: Response<ArrayList<UserResponseItem>>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    followers.value = responseBody ?: ArrayList()
                } else {
                    Log.e("responnotsuccess", "Mungkin kena limit")
                }
            }

            override fun onFailure(call: Call<ArrayList<UserResponseItem>>, t: Throwable) {
                isLoading.value = false
                Log.e("x", "sialan")
            }
        })
    }

    fun getFollowers() : LiveData<ArrayList<UserResponseItem>> {
        return followers
    }

    fun isLoading() : LiveData<Boolean> {
        return isLoading
    }
}