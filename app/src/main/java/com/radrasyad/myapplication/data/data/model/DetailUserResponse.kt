package com.radrasyad.myapplication.data.data.model

data class DetailUserResponse(
    val login : String,
    val avatar_url : String,
    val id : String,
    val followers_url : String,
    val following_url : String,
    val name : String,
    val public_repos : Int,
    val followers : Int,
    val following : Int,
    val company : String,
    val location : String
)
