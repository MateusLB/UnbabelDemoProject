package com.mateus.batista.data.source

import com.mateus.batista.data.model.CommentModel
import com.mateus.batista.data.model.PostModel
import com.mateus.batista.data.model.UserModel

interface PostRemoteDataSource {
    suspend fun getUsers() : List<UserModel>

    suspend fun getComments() : List<CommentModel>

    suspend fun getPosts() : List<PostModel>
}