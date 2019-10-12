package com.mateus.batista.domain.model

data class Post(
    val userId: Long,
    val id: Long,
    val title: String,
    val body: String
)