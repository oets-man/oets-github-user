package com.example.githubuser.model

import com.google.gson.annotations.SerializedName

data class UserSearchResponse(

	@field:SerializedName("items")
	val items: ArrayList<UserResponseItem>
)
