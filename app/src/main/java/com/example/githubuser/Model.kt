package com.example.githubuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Model : ViewModel() {
    private lateinit var name: MutableLiveData<String>

    fun getName(): LiveData<String> {
        name = MutableLiveData()
        return name
    }
}