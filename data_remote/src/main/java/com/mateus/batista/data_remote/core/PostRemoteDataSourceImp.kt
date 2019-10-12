package com.mateus.batista.data_remote.core

import com.mateus.batista.data.model.CommentModel
import com.mateus.batista.data.model.PostModel
import com.mateus.batista.data.model.UserModel
import com.mateus.batista.data.repository.PostRemoteDataSource
import com.mateus.batista.data_remote.mapper.CommentMapper
import com.mateus.batista.data_remote.mapper.PostMapper
import com.mateus.batista.data_remote.mapper.UserMapper
import com.mateus.batista.data_remote.service.UnbabelService
import org.koin.core.KoinComponent

class PostRemoteDataSourceImp(
    private val service: UnbabelService
) : KoinComponent, PostRemoteDataSource {

    override suspend fun getUsers(): List<UserModel> {
        return UserMapper.toData(service.getUsers())
    }

    override suspend fun getComments(): List<CommentModel> {
        return CommentMapper.toData(service.getComments())
    }

    override suspend fun getPosts(): List<PostModel> {
        return PostMapper.toData(service.getPosts())
    }
}