package com.example.githubuser

import com.google.gson.annotations.SerializedName

data class UserSearchResponse(

	@field:SerializedName("items")
	val items: List<UserResponseQuery>
)

data class UserResponseQuery(

	@field:SerializedName("login")
	val login: String
)
