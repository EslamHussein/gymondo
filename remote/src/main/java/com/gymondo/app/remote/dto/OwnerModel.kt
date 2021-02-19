package com.gymondo.app.remote.dto

import com.google.gson.annotations.SerializedName

data class OwnerModel(@SerializedName("login") val name: String,
                      val id: Int,
                      @SerializedName("avatar_url") val avatarUrl: String)