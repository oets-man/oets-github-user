package com.example.githubuser.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.internet.ApiConfig
import com.example.githubuser.model.UserResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    private val following = MutableLiveData<ArrayList<UserResponseItem>>()
    private val isLoading = MutableLiveData<Boolean>()

    fun loadData(login: String) {
        isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(login)
        client.enqueue(object : Callback<ArrayList<UserResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<UserResponseItem>>,
                response: Response<ArrayList<UserResponseItem>>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    following.value = responseBody ?: ArrayList()
                } else {
                    Log.e("responnotsuccess", "Mungin kena limit")
                }
            }

            override fun onFailure(call: Call<ArrayList<UserResponseItem>>, t: Throwable) {
                isLoading.value = false
                Log.e("x", t.message.toString())
            }
        })
    }

    fun isLoading() : LiveData<Boolean> {
        return isLoading
    }

    fun getFollowing() : LiveData<ArrayList<UserResponseItem>> {
        return following
    }
}