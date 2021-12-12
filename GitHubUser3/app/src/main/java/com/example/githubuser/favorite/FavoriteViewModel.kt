package com.example.githubuser.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavRepository: FavoriteRepository = FavoriteRepository(application)

//    fun getAllFavorites(): LiveData<List<FavoriteEntity>> {
//        return mFavRepository.getAllFavorites()
//    }
//
//    fun insert(favorite: FavoriteEntity) {
//        mFavRepository.insert(favorite)
//    }
//    fun update(favorite: FavoriteEntity) {
//        mFavRepository.update(favorite)
//    }
//    fun delete(favorite: FavoriteEntity) {
//        mFavRepository.delete(favorite)
//    }
}