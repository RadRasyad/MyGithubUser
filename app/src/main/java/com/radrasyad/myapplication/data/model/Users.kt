package com.radrasyad.myapplication.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Users(
    val login : String,
    val id : Int,
    val avatar_url : String
) : Parcelable
