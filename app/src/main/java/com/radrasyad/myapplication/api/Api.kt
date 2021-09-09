package com.radrasyad.myapplication.api

import com.radrasyad.myapplication.data.model.DetailUserResponse
import com.radrasyad.myapplication.data.model.UserResponse
import com.radrasyad.myapplication.data.model.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")

    fun getSearchUsers(
        @Query(value = "q") query:String
        ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username : String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username : String
    ): Call<ArrayList<Users>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username : String
    ): Call<ArrayList<Users>>
}