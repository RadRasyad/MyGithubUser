package com.radrasyad.myapplication.data.data.model

data class DetailUserResponse(
    val login : String? = null,
    val avatar_url : String? = null,
    val id : Int = 0,
    val followers_url : String? = null,
    val following_url : String? = null,
    val name : String? = null,
    val public_repos : Int? = null,
    val followers : Int? = null,
    val following : Int? = null,
    val company : String? = null,
    val location : String? = null
)
