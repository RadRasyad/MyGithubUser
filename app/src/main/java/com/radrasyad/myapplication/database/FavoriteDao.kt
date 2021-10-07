package com.radrasyad.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.radrasyad.myapplication.data.data.model.Favorite

@Dao
interface FavoriteDao {
    @Insert
    fun insert(favorite: Favorite)

    @Query("SELECT count(*) FROM favorite WHERE favorite.id = :id")
    fun check(id: Int): Int

    @Query("DELETE FROM favorite WHERE favorite.id = :id")
    fun delete(id: Int): Int

    @Query("SELECT * from favorite")
    fun getAllFavorites(): LiveData<List<Favorite>>

}