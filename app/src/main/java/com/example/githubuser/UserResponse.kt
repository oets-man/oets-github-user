package com.example.githubuser

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("UserResponse")
	val userResponse: List<UserResponseItem>
)

data class UserResponseItem(

	@field:SerializedName("login")
	val login: String
)
