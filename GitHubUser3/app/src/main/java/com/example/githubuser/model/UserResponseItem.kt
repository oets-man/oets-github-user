package com.example.githubuser.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponseItem(
	@field:SerializedName("login")
	var login: String? = null,
	@field:SerializedName("id")
	var id: Long? = 0,
	@field:SerializedName("type")
	var type: String? = null,
	@field:SerializedName("avatar_url")
	var avatarUrl: String? = null
) : Parcelable
