package com.example.githubuser.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoritesDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDatabase.getDatabase(application)
        mFavoritesDao = db.favoriteDao()
    }

    fun getAllFavorites(): LiveData<List<FavoriteEntity>> = mFavoritesDao.getAllFavorites()

    fun insert(favoriteEntity: FavoriteEntity) {
        executorService.execute { mFavoritesDao.insert(favoriteEntity) }
    }

    fun delete(favoriteEntity: FavoriteEntity) {
        executorService.execute { mFavoritesDao.delete(favoriteEntity) }
    }

    fun update(favoriteEntity: FavoriteEntity) {
        executorService.execute { mFavoritesDao.update(favoriteEntity) }
    }

    fun getUserFavoriteById(id: Long): LiveData<List<FavoriteEntity>> =
        mFavoritesDao.getUserFavoriteById(id)

}