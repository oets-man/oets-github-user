package com.example.githubuser.favorite

import android.app.Application
import androidx.lifecycle.ViewModel

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

//    fun getAllFavorites(): LiveData<List<FavoriteEntity>> {
//        return mFavoriteRepository.getAllFavorites()
//    }

    fun insert(favorite: FavoriteEntity) {
        mFavoriteRepository.insert(favorite)
    }

    fun update(favorite: FavoriteEntity) {
        mFavoriteRepository.update(favorite)
    }

    fun delete(favorite: FavoriteEntity) {
        mFavoriteRepository.delete(favorite)
    }
}