package com.radrasyad.myapplication.api

import com.radrasyad.myapplication.BuildConfig
import com.radrasyad.myapplication.data.data.model.DetailUserResponse
import com.radrasyad.myapplication.data.data.model.UserResponse
import com.radrasyad.myapplication.data.data.model.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("search/users")
    @Headers("Authorization: token $secret")

    fun getSearchUsers(
        @Query(value = "q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $secret")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $secret")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<Users>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $secret")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<Users>>

    companion object {
        const val secret = BuildConfig.KEY
    }
}