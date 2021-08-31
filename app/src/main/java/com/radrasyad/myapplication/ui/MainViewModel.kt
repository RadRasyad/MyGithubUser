package com.radrasyad.myapplication.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radrasyad.myapplication.api.RetrofitClient
import com.radrasyad.myapplication.data.model.UserResponse
import com.radrasyad.myapplication.data.model.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<Users>>()

    fun setSearchUsers(query: String){
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>?,
                    response: Response<UserResponse>?
                ) {
                    listUsers.postValue(response?.body()?.items)
                }

                override fun onFailure(call: Call<UserResponse>?, t: Throwable?) {
                    if (t != null) {
                        Log.d("Failure", t.message!!)
                    }
                }
            })
    }

    fun getSearchUsers(): LiveData<ArrayList<Users>>{
        return listUsers
    }
}