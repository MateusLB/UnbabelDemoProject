package com.mateus.batista.data.source

import com.mateus.batista.data.model.CommentModel
import com.mateus.batista.data.model.PostModel
import com.mateus.batista.data.model.UserModel

interface PostCacheDataSource {

    suspend fun insertAllPost(posts: List<PostModel>)

    suspend fun insertAllComments(comments: List<CommentModel>)

    suspend fun insertAllUsers(users: List<UserModel>)

    suspend fun getUserById(id: Long) : UserModel

    suspend fun getCommentsByPostId(postId: Long) : List<CommentModel>

    suspend fun getPosts() : List<PostModel>

    suspend fun getPostById(id: Long) : PostModel
}