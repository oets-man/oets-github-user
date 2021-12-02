package com.example.githubuser

import com.google.gson.annotations.SerializedName

data class UserSearchResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<UserResponseQuery>
)

data class UserResponseQuery(

	@field:SerializedName("login")
	val login: String
)
