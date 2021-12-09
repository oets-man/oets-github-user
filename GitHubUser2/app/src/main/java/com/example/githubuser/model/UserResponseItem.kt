package com.example.githubuser.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponseItem(
	@field:SerializedName("login")
	val login: String? = null,
	@field:SerializedName("id")
	val id: Long? = 0,
	@field:SerializedName("type")
	val type: String? = null,
	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null
) : Parcelable
