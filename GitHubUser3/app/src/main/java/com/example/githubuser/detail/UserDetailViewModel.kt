package com.example.githubuser.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.internet.ApiConfig
import com.example.githubuser.model.UserDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel : ViewModel() {
    private val detailUser = MutableLiveData<UserDetailResponse>()

    fun loadDetailUser(login: String) {
        ApiConfig.getApiService().getUserDetail(login).enqueue(object :
            Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        detailUser.value = data
                    }
                } else {
                    Log.e("user_detail_activity", response.message())
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                Log.e("user_detail_activity", t.message.toString())
            }

        })
    }

    fun getDetailUser() : LiveData<UserDetailResponse> {
        return detailUser
    }
}