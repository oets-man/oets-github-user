package com.example.githubuser.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class FavoriteViewFactory private constructor(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: FavoriteViewFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavoriteViewFactory {
            if (INSTANCE == null) {
                synchronized(FavoriteViewFactory::class.java) {
                    INSTANCE = FavoriteViewFactory(application)
                }
            }
            return INSTANCE as FavoriteViewFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name} ")
    }

}