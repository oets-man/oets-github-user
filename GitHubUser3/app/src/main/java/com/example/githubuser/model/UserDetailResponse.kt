package com.example.githubuser.model

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(

	@field:SerializedName("followers")
	val followers: Int? = 0,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("following")
	val following: Int? = 0,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("public_repos")
	val publicRepos: Int? = 0,

	@field:SerializedName("login")
	val login: String? = null,
)