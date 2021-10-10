package com.radrasyad.myapplication.ui.main

import android.util.Log
import androidx.constraintlayout.widget.StateSet.TAG
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radrasyad.myapplication.api.RetrofitClient
import com.radrasyad.myapplication.data.data.model.UserResponse
import com.radrasyad.myapplication.data.data.model.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<Users>>()

    fun setSearchUsers(query: String) {
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUsers.postValue(response.body()?.items)
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                    Log.e(TAG, "onFailure, ${t.message}")

                }
            })
    }

    fun getSearchUsers(): MutableLiveData<ArrayList<Users>> {
        return listUsers
    }
}