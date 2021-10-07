package com.radrasyad.myapplication.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.radrasyad.myapplication.data.data.model.Favorite
import com.radrasyad.myapplication.database.FavoriteDao
import com.radrasyad.myapplication.database.FavoriteRoomDatabase


class FavoriteRepository(application: Application) {

    private val mFavoriteDao: FavoriteDao

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteDao.getAllFavorites()

    fun check(id: Int) = mFavoriteDao.check(id)

    fun insert(favorite: Favorite) = mFavoriteDao.insert(favorite)

    fun delete(id: Int) = mFavoriteDao.delete(id)
}