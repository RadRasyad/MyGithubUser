package com.radrasyad.myapplication.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radrasyad.myapplication.api.RetrofitClient
import com.radrasyad.myapplication.data.model.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {

    var listFollowers = MutableLiveData<ArrayList<Users>>()

    fun setListFollowers(username: String) {
        RetrofitClient.apiInstance
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<Users>>{
                override fun onResponse(
                    call: Call<ArrayList<Users>>,
                    response: Response<ArrayList<Users>>
                ) {
                    if (response.isSuccessful){
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<Users>>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }

            })
    }

    @JvmName("getListFollowers1")
    fun getListFollowers() : MutableLiveData<ArrayList<Users>>{
        return listFollowers
    }
}