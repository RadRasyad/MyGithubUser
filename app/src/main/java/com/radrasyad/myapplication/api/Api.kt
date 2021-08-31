package com.radrasyad.myapplication.api

import com.radrasyad.myapplication.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")

    fun getSearchUsers(
        @Query(value = "q") query:String
        ): Call<UserResponse>
}