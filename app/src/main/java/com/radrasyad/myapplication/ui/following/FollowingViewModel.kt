package com.radrasyad.myapplication.ui.following

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radrasyad.myapplication.api.RetrofitClient
import com.radrasyad.myapplication.data.data.model.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {

    var listFollowing = MutableLiveData<ArrayList<Users>>()

    fun setListFollowing(username: String) {
        RetrofitClient.apiInstance
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<Users>>{
                override fun onResponse(
                    call: Call<ArrayList<Users>>,
                    response: Response<ArrayList<Users>>
                ) {
                    if (response.isSuccessful){
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<Users>>, t: Throwable) {
                    Log.d("Failed to get following", t.message!!)
                }
            })
    }

    @JvmName("getListFollowing1")
    fun getListFollowing() : MutableLiveData<ArrayList<Users>>{
        return listFollowing
    }
}