package com.example.githubuser.favorite

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg favorite: FavoriteEntity)

    @Update
    fun update(favoriteEntity: FavoriteEntity)

    @Delete
    fun delete(favoriteEntity: FavoriteEntity)

    @Query("SELECT * from FavoriteEntity ORDER BY id ASC")
    fun getAllFavorites(): LiveData<List<FavoriteEntity>>

    @Query("SELECT  * from FavoriteEntity WHERE id = :id")
    fun getUserFavoriteById(id: Long): LiveData<List<FavoriteEntity>>

    @Query("DELETE from FavoriteEntity WHERE id = :id")
    fun deleteById(id: Long)
}
