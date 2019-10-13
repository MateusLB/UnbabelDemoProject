package com.mateus.batista.domain.repository

import com.mateus.batista.domain.core.Response
import com.mateus.batista.domain.model.Post

interface PostRepository {
    suspend fun getPosts() : Response<List<Post>>

    suspend fun getPostById(id : Long) : Response<Post>
}