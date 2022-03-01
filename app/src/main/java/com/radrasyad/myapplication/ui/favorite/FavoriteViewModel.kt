package com.radrasyad.myapplication.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.radrasyad.myapplication.data.model.Favorite
import com.radrasyad.myapplication.repository.FavoriteRepository


class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository?

    init {
        mFavoriteRepository = FavoriteRepository(application)
    }

    fun getAllFavorites(): LiveData<List<Favorite>>? = mFavoriteRepository?.getAllFavorites()
}