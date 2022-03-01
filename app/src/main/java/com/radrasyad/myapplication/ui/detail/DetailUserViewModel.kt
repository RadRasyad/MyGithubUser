package com.radrasyad.myapplication.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.radrasyad.myapplication.api.RetrofitClient
import com.radrasyad.myapplication.data.model.DetailUserResponse
import com.radrasyad.myapplication.data.model.Favorite
import com.radrasyad.myapplication.repository.FavoriteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {

    val user = MutableLiveData<DetailUserResponse>()
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Failed to get data", t.message.toString())
                }
            })
    }

    fun getUserDetail(): MutableLiveData<DetailUserResponse> {
        return user
    }

    fun checkUser(id: Int) = mFavoriteRepository.check(id)

    fun addToFavorite(username: String, id: Int, avatarUrl: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = Favorite(
                id,
                avatarUrl,
                username
            )
            mFavoriteRepository.insert(user)
        }
    }

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            mFavoriteRepository.delete(id)
        }
    }

}

