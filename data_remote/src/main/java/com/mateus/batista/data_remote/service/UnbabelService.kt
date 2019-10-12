package com.mateus.batista.data_remote.service

import com.mateus.batista.data_remote.model.CommentApi
import com.mateus.batista.data_remote.model.PostApi
import com.mateus.batista.data_remote.model.UserApi
import retrofit2.http.GET

interface UnbabelService {

    @GET("users")
    suspend fun getUsers() : List<UserApi>

    @GET("comments")
    suspend fun getComments() : List<CommentApi>

    @GET("posts")
    suspend fun getPosts() : List<PostApi>
}