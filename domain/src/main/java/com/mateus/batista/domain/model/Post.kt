package com.mateus.batista.domain.model

data class Post(
    val id: Long,
    val title: String,
    val body: String,
    val author: String,
    val numberComments: Int
)