package com.example.githubuser.main

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.Event
import com.example.githubuser.internet.ApiConfig
import com.example.githubuser.model.UserSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val users = MutableLiveData<UserSearchResponse>()
    private val isLoading = MutableLiveData<Boolean>()

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    fun showUsersBy(query: String) {

        isLoading.value = true
        val client = ApiConfig.getApiService().getUserBy(query)
        client.enqueue(object : Callback<UserSearchResponse> {
            override fun onResponse(
                call: Call<UserSearchResponse>,
                response: Response<UserSearchResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    users.value = response.body()
                    _snackbarText.value = Event("Hasil query dari $query")

                    if (users.value?.items?.size == 0) {
                        _snackbarText.value = Event("Tidak hasil. Coba kata kunci yang lain...")
                    }
                }
                isLoading.value = false
            }

            override fun onFailure(call: Call<UserSearchResponse>, t: Throwable) {
                isLoading.value = false
                Log.e("x", t.message.toString())
            }
        })
    }

    fun getUsers(): LiveData<UserSearchResponse> {
        return users
    }

    fun isLoading(): LiveData<Boolean> {
        return isLoading
    }
}