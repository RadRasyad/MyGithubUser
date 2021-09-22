package com.radrasyad.myapplication.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.radrasyad.myapplication.api.RetrofitClient
import com.radrasyad.myapplication.data.data.model.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application): AndroidViewModel(application) {

    val user = MutableLiveData<DetailUserResponse>()

        fun setUserDetail(username: String){
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse>{
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Failed to get data", t.message!!)
                }
            })
    }

    fun getUserDetail() : MutableLiveData<DetailUserResponse> {
        return user
    }

}

