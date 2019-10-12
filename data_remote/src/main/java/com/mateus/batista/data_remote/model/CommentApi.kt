package com.mateus.batista.data_remote.model

import com.google.gson.annotations.SerializedName

data class CommentApi(
    @SerializedName("postId")
    val postId: Long? = null,
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("body")
    val body: String? = null
)