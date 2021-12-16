package com.example.githubuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users(
    var avatar:Int,
    var name:String,
    var username:String,
    var location:String,
    var company:String,
    var repository:String,
    var followers:String,
    var following:String
): Parcelable
