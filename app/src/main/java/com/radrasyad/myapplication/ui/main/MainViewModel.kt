package com.radrasyad.myapplication.ui.main

import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.StateSet.TAG
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radrasyad.myapplication.api.RetrofitClient
import com.radrasyad.myapplication.data.data.model.UserResponse
import com.radrasyad.myapplication.data.data.model.Users
import com.radrasyad.myapplication.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<Users>>()
    private lateinit var binding: ActivityMainBinding

    fun setSearchUsers(query: String){
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful){
                        listUsers.postValue(response.body()?.items)
                    }else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                    Log.e(TAG, "onFailure, ${t.message}")
                    binding.progressbar.visibility = View.GONE
                }
            })
    }

    fun getSearchUsers(): LiveData<ArrayList<Users>>{
        return listUsers
    }
}